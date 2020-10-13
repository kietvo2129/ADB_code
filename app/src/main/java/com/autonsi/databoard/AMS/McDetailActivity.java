package com.autonsi.databoard.AMS;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlarmData.StatusLayout.MapFloorAlrmActivity;
import com.autonsi.databoard.AlarmData.StatusLayout.MapSensor.MapSensorAlarmActivity;
import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Counting.Scan.ScanQRActivity;
import com.autonsi.databoard.Receving.ReceivingScan.ReceivingScanActivity;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class McDetailActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    EditText edt_qrcode;
    ImageView map,img_qrcode;
    TextView tv_code,Name,MCType,Description,Serial,Purchasedate,ValidDate,Status,tvurl,Document;
    ArrayList<MCDetailMaster> mcDetailMasterArrayList;
    RelativeLayout contain;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mc_detail);
        img_qrcode= findViewById(R.id.img_qrcode);
        edt_qrcode = findViewById(R.id.edt_qrcode);
        contain= findViewById(R.id.contain);

        map = findViewById(R.id.map);
        tv_code = findViewById(R.id.tv_code);
        Name = findViewById(R.id.Name);
        MCType = findViewById(R.id.MCType);
        Description = findViewById(R.id.Description);
        Serial = findViewById(R.id.Serial);
        Purchasedate = findViewById(R.id.Purchasedate);
        ValidDate = findViewById(R.id.ValidDate);
        Status = findViewById(R.id.Status);
        tvurl = findViewById(R.id.tvurl);
        Document = findViewById(R.id.Document);
        contain.setVisibility(View.GONE);

        img_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQRScanner();
            }
        });
        edt_qrcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                closeKeyboard();
                if (edt_qrcode.getText().toString().length() != 0){
                    getData(edt_qrcode.getText().toString().trim());
                }
                return false;
            }
        });

    }
    public void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(McDetailActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                edt_qrcode.setText(result.getContents());
                getData(result.getContents());
            }
        }
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }
    private void getData(String mccode) {
        new getData().execute(Url + "AMSTPMSchedule/API_GetAllMachines?code="+mccode);
    }
    private class getData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mcDetailMasterArrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                String MachineNo,MachineName,MachineTypeName,Description,SerialNumber,PurchaseDate,ValidDate
                        ,Image,MachineStatusName,ReferenceLink,Document;
                if (!jsonObject.getBoolean("result")||jsonObject.getJSONArray("data").length()==0){
                    AlerError.Baoloi("MC code don't exist", McDetailActivity.this);
                    contain.setVisibility(View.GONE);
                    return;
                }
                JSONArray jsonArray = jsonObject.getJSONArray("data");
                if (jsonArray.length()!=0) {
                    JSONObject object = jsonArray.getJSONObject(0);
                    MachineNo = object.getString("MachineNo");
                    MachineName = object.getString("MachineName");
                    MachineTypeName = object.getString("MachineTypeName");
                    Description = object.getString("Description");
                    SerialNumber = object.getString("SerialNumber");
                    PurchaseDate = object.getString("PurchaseDate");
                    ValidDate = object.getString("ValidDate");
                    Image = object.getString("Image");
                    MachineStatusName = object.getString("MachineStatusName");
                    ReferenceLink = object.getString("ReferenceLink");
                    Document = object.getString("Document");
                    mcDetailMasterArrayList.add(new MCDetailMaster(MachineNo,MachineName,MachineTypeName,Description,SerialNumber,PurchaseDate,ValidDate
                            ,Image,MachineStatusName,ReferenceLink,Document));
                    setData();
                } else {
                    AlerError.Baoloi("Location code incorrect.", McDetailActivity.this);
                    contain.setVisibility(View.GONE);
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                contain.setVisibility(View.GONE);
                AlerError.Baoloi("Could not connect to server", McDetailActivity.this);
            }
        }

    }

    private void setData() {
        contain.setVisibility(View.VISIBLE);
        tv_code.setText(mcDetailMasterArrayList.get(0).MachineNo);
        Name.setText(mcDetailMasterArrayList.get(0).MachineName);
        MCType.setText(mcDetailMasterArrayList.get(0).MachineTypeName);
        Description.setText(mcDetailMasterArrayList.get(0).Description);
        Serial.setText(mcDetailMasterArrayList.get(0).SerialNumber);
        Purchasedate.setText(mcDetailMasterArrayList.get(0).PurchaseDate);
        ValidDate.setText(mcDetailMasterArrayList.get(0).ValidDate);
        ValidDate.setText(mcDetailMasterArrayList.get(0).ValidDate);
        Status.setText(mcDetailMasterArrayList.get(0).MachineStatusName);
        tvurl.setText(mcDetailMasterArrayList.get(0).ReferenceLink);
        Document.setText(mcDetailMasterArrayList.get(0).Document);

        Log.e("logImage",Url + "Images/Machine/" + mcDetailMasterArrayList.get(0).getImage());
        Glide.with(McDetailActivity.this)
                .load(Url + "Images/Machine/" + mcDetailMasterArrayList.get(0).getImage())
                .error(R.drawable.errorimage)
                .into(map);
    }
}
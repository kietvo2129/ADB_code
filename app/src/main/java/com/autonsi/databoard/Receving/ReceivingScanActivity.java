package com.autonsi.databoard.Receving;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.autonsi.databoard.AlarmData.IssuesList.AlarmIssuesList;
import com.autonsi.databoard.AlarmData.IssuesList.DoorHistoryAdapter;
import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Counting.Scan.ScanQRActivity;
import com.autonsi.databoard.DigitalData.IssuesList.IssuesActivity;
import com.autonsi.databoard.VolleyMultipartRequest;
import com.bumptech.glide.Glide;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ReceivingScanActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    TextView tvLocation;
    ArrayList<String> arrayLocationcd;
    ArrayAdapter<String> arrayLocationnm;
    ImageView img_qrcode;
    String locationCd;
    EditText edt_qrcode;

    ArrayList<PutAwayScanMaster> awayScanMasterArrayList;

    PutAwayScanAdapter putAwayScanAdapter;

    RecyclerView mRecyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving_scan);
        tvLocation = findViewById(R.id.tvLocation);
        mRecyclerView= findViewById(R.id.recyclerview);
        getlocation();

        awayScanMasterArrayList = new ArrayList<>();
        tvLocation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showLocationName();
            }
        });
        img_qrcode = findViewById(R.id.img_qrcode);
        img_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQRScanner();
            }
        });
        edt_qrcode = findViewById(R.id.edt_qrcode);
        edt_qrcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                closeKeyboard();
                if (edt_qrcode.getText().toString().length() != 0){
                    getDataQRcode(edt_qrcode.getText().toString().trim());
                }
                return false;
            }
        });

    }
    public void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(ReceivingScanActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                edt_qrcode.setText(result.getContents());
                getDataQRcode(result.getContents());
            }
        }
    }

    private void getDataQRcode(String str) {
        String UPLOAD_URL = Url + "WMSReceiving/PutAwayMaterialScan";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {


                            // Json như binh thuong
                            JSONObject obj = new JSONObject(new String(response.data));

                            if (obj.getBoolean("flag")){
                                Toast.makeText(ReceivingScanActivity.this, obj.getString("message"), Toast.LENGTH_SHORT).show();
                                edt_qrcode.setText("");

                                JSONArray jsonArray = obj.getJSONArray("data");
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String mt_lot_cd,mt_nm,bin_name;
                                mt_lot_cd = jsonObject.getString("mt_lot_cd");
                                mt_nm = jsonObject.getString("mt_nm");
                                bin_name = jsonObject.getString("bin_name");
                                awayScanMasterArrayList.add(0,new PutAwayScanMaster(mt_lot_cd,mt_nm,bin_name));

                                buildRecyclerView();
                            }else {
                                AlerError.Baoloi(obj.getString("message"), ReceivingScanActivity.this);
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        AlerError.Baoloi("Could not connect to server", ReceivingScanActivity.this);
                    }
                }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("binCode", locationCd);
                params.put("lotCode", str);
                return params;
            }
        };

        Volley.newRequestQueue(ReceivingScanActivity.this).add(volleyMultipartRequest);
    }


    private void buildRecyclerView() {

        RecyclerView.LayoutManager mLayoutManager;
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ReceivingScanActivity.this);
        putAwayScanAdapter = new PutAwayScanAdapter(awayScanMasterArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(putAwayScanAdapter);

        putAwayScanAdapter.setOnItemClickListener(new PutAwayScanAdapter.OnItemClickListener() {
            @Override
            public void onwarningClick(int position) {

            }
        });

    }



    private void showLocationName() {

        AlertDialog.Builder builderSingle = new AlertDialog.Builder(ReceivingScanActivity.this);
        builderSingle.setTitle("Select One Line:");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayLocationnm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                tvLocation.setText(arrayLocationnm.getItem(i));
                locationCd = arrayLocationcd.get(i);
                dialog.dismiss();
                Toast.makeText(ReceivingScanActivity.this, ""+locationCd, Toast.LENGTH_SHORT).show();
            }
        });
        builderSingle.show();
    }

    private void getlocation() {

        new getlocation().execute(Url + "WMSConfiguration/GetWarehouseLocation?type=BIN");

    }
    private class getlocation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            arrayLocationcd = new ArrayList<>();
            arrayLocationnm = new ArrayAdapter<String>(ReceivingScanActivity.this, android.R.layout.select_dialog_singlechoice);
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length()!=0) {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        String lct_cd = jsonObject.getString("lct_cd");
                        String lct_nm = jsonObject.getString("lct_nm");
                        arrayLocationnm.add(lct_nm);
                        arrayLocationcd.add(lct_cd);
                    }
                    tvLocation.setText(arrayLocationnm.getItem(0));
                    locationCd = arrayLocationcd.get(0);

                } else {
                    AlerError.Baoloi("Location code incorrect.", ReceivingScanActivity.this);
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ReceivingScanActivity.this);
            }
        }

    }
}
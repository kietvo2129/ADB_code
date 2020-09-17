package com.autonsi.databoard.Receving.FindQRCode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlerError.AlerError;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class FindQRCodeActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    ImageView img_qrcode;
    EditText edt_qrcode;
    String status = "";
    ImageView map;
    TextView tv_mtcode,tv_mt_nm,tv_lot_qty,tv_unitprice,tv_lotprice,tv_expdate,WorkDate,lct_nm;

    ArrayList<FindQRCodeMaster> findQRCodeMasterArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_qr_code);
        tv_mtcode= findViewById(R.id.tv_mtcode);
        tv_mt_nm= findViewById(R.id.tv_mt_nm);
        tv_lot_qty= findViewById(R.id.tv_lot_qty);
        tv_unitprice= findViewById(R.id.tv_unitprice);
        tv_lotprice= findViewById(R.id.tv_lotprice);
        tv_expdate= findViewById(R.id.tv_expdate);
        WorkDate= findViewById(R.id.WorkDate);
        lct_nm= findViewById(R.id.lct_nm);

        setnull();
        map= findViewById(R.id.map);
        img_qrcode = findViewById(R.id.img_qrcode);
        map.setImageResource(R.drawable.im_noexist);
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
                if (edt_qrcode.getText().toString().length() != 0) {
                    getDataQRcode(edt_qrcode.getText().toString().trim());
                }
                return false;
            }
        });
    }

    private void setnull() {
        tv_mtcode.setText("");
        tv_mt_nm.setText("");
        tv_lot_qty.setText("");
        tv_unitprice.setText("");
        tv_lotprice.setText("");
        tv_expdate.setText("");
        WorkDate.setText("");
        lct_nm.setText("");
    }
    private void setData() {
        tv_mtcode.setText(findQRCodeMasterArrayList.get(0).getMt_cd());
        tv_mt_nm.setText(findQRCodeMasterArrayList.get(0).getMt_nm());
        tv_lot_qty.setText(findQRCodeMasterArrayList.get(0).getLot_qty());
        tv_unitprice.setText(findQRCodeMasterArrayList.get(0).getUnit_price());
        tv_lotprice.setText(findQRCodeMasterArrayList.get(0).getLot_price());
        tv_expdate.setText(findQRCodeMasterArrayList.get(0).getExpiry_date());
        WorkDate.setText(findQRCodeMasterArrayList.get(0).getWork_dt());
        lct_nm.setText(findQRCodeMasterArrayList.get(0).getLct_nm());
    }

    public void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(FindQRCodeActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                edt_qrcode.setText(result.getContents());
                getDataQRcode(result.getContents());
            }
        }
    }

    private void getDataQRcode(String contents) {
        new getDataQRcode().execute(Url + "APIProduct/GetStatusLotCode?mt_lot_cd=" + contents);

    }

    private class getDataQRcode extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            findQRCodeMasterArrayList = new ArrayList<>();
            try {
                String mt_cd,mt_cd_origin,mt_nm,lot_qty,unit_price,lot_price,expiry_date,mr_no,mrd_no,worker,work_dt,lct_nm;
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("result")) {

                    JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                    status = jsonObject1.getString("status").replace("null","");
                    mt_cd_origin = jsonObject1.getString("mt_cd_origin").replace("null","");
                    mt_nm = jsonObject1.getString("mt_nm").replace("null","");
                    lot_qty = jsonObject1.getString("lot_qty").replace("null","");
                    unit_price = jsonObject1.getString("unit_price").replace("null","");
                    lot_price = jsonObject1.getString("lot_price").replace("null","");
                    expiry_date = jsonObject1.getString("expiry_date").replace("null","");
                    mr_no = jsonObject1.getString("mr_no").replace("null","");
                    mrd_no = jsonObject1.getString("mrd_no").replace("null","");
                    worker = jsonObject1.getString("worker").replace("null","");
                    work_dt = jsonObject1.getString("work_dt").replace("null","");
                    lct_nm = jsonObject1.getString("lct_nm").replace("null","");
                    mt_cd = jsonObject1.getString("mt_cd").replace("null","");
                    findQRCodeMasterArrayList.add(new FindQRCodeMaster(mt_cd,mt_cd_origin,mt_nm,lot_qty,unit_price,lot_price,expiry_date,mr_no,mrd_no,worker,work_dt,lct_nm,status));
                    setData();
                } else {
                    setnull();
                    status = "";
                    AlerError.Baoloi(jsonObject.getString("message"), FindQRCodeActivity.this);
                }
                setLocation();setLocation();

            } catch (JSONException e) {
                setnull();
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", FindQRCodeActivity.this);
            }
        }

    }


    private void setLocation() {

        switch (status){
            case "LotCode Generated":
                map.setImageResource(R.drawable.im_receiving);
                break;
            case "Put Away":
                map.setImageResource(R.drawable.im_putaway);
                break;
            case "Shipping Order":
                map.setImageResource(R.drawable.im_shippingoder);
                break;
            case "Picking":
                map.setImageResource(R.drawable.im_picking);
                break;
            case "Confirm":
                map.setImageResource(R.drawable.im_shipping);
                break;
            default :
                map.setImageResource(R.drawable.im_noexist);
                break;

        }

    }

}
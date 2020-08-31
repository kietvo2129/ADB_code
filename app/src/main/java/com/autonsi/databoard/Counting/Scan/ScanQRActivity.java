package com.autonsi.databoard.Counting.Scan;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Counting.Count.CountActivity;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.google.zxing.qrcode.QRCodeWriter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ScanQRActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    ImageView img_qrcode,imabarcode;
    EditText edt_qrcode;
    TextView tv_line_nm,tv_prd_nm,tv_lct_nm,tv_datetime,tv_barcode;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_qr);
        img_qrcode = findViewById(R.id.img_qrcode);
        edt_qrcode = findViewById(R.id.edt_qrcode);
        tv_line_nm = findViewById(R.id.tv_line_nm);
        tv_prd_nm = findViewById(R.id.tv_prd_nm);
        tv_lct_nm = findViewById(R.id.tv_lct_nm);
        tv_datetime = findViewById(R.id.tv_datetime);
        imabarcode = findViewById(R.id.imabarcode);
        tv_barcode = findViewById(R.id.tv_barcode);
        rl = findViewById(R.id.rl);
        rl.setVisibility(View.GONE);
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
                    getDataQRcode(edt_qrcode.getText().toString().trim());
                }
                return false;
            }
        });
    }
    public void showKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
    }

    public void closeKeyboard(){
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
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
                Toast.makeText(ScanQRActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                edt_qrcode.setText(result.getContents());
                getDataQRcode(result.getContents());
            }
        }
    }

    private void getDataQRcode(String str) {
        new getDataQR().execute(Url + "plan/get_info_productlot_info?prd_lot=" + str);
    }
    private class getDataQR extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("result")) {
                    rl.setVisibility(View.VISIBLE);

                    JSONObject jsonObject1 = jsonObject.getJSONObject("Data");
                    tv_barcode.setText(jsonObject1.getString("prd_lot").replace("null",""));
                    tv_line_nm.setText(jsonObject1.getString("line_nm").replace("null",""));
                    tv_lct_nm.setText(jsonObject1.getString("lct_nm").replace("null",""));
                    tv_prd_nm.setText(jsonObject1.getString("prd_nm").replace("null",""));
                    tv_datetime.setText(jsonObject1.getString("datetime").replace("null",""));
                    imabarcode.setImageBitmap(ima_barcode(jsonObject1.getString("prd_lot").replace("null","")));
                } else {
                    rl.setVisibility(View.GONE);
                    AlerError.Baoloi(jsonObject.getString("message"), ScanQRActivity.this);
                    return;
                }

            } catch (JSONException e) {
                rl.setVisibility(View.GONE);
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ScanQRActivity.this);
            }
        }

    }
    public static Bitmap ima_barcode(String val) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(val, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }
}
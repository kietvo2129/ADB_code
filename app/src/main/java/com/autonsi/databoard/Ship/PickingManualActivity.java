package com.autonsi.databoard.Ship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Receving.ReceivingActivity;
import com.autonsi.databoard.Receving.ReceivingAdapter;
import com.autonsi.databoard.Receving.ReceivingMaster;
import com.autonsi.databoard.Url;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class PickingManualActivity extends AppCompatActivity {

    private EditText edt_mrno, edt_mtno, edt_lotcode;
    private RecyclerView rv_pickmanual;
    private Button btnSearchPick, btnPickManual;
    private TextView edt_startdate, edt_enđate;
    private ArrayList<PickManualItem> materPick;
    private PickManualAdapter pickManualAdapter;
    private CheckBox check_all_picking;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picking_manual);
        setTitle("Picking Manual");

        edt_mrno = findViewById(R.id.edt_MRNO);
        edt_mtno = findViewById(R.id.edt_MTNO);
        edt_lotcode = findViewById(R.id.edt_LOTCODE);
        rv_pickmanual = findViewById(R.id.rv_pickmanual);
        btnPickManual = findViewById(R.id.btn_pickmanual);
        btnSearchPick = findViewById(R.id.btn_spick);

        edt_startdate = findViewById(R.id.edt_startdate);
        edt_enđate = findViewById(R.id.edt_enddate);
        check_all_picking = findViewById(R.id.check_all_picking);

        SearchPick();

        btnSearchPick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SearchPick();
            }
        });

        edt_startdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate(edt_startdate);
            }
        });

        edt_enđate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SelectDate(edt_enđate);
            }
        });
    }

    private void SearchPick() {
        String mr = edt_mrno.getText().toString().trim();
        String mt = edt_mtno.getText().toString().trim();
        String lo = edt_lotcode.getText().toString().trim();
        String sd = edt_startdate.getText().toString().trim();
        String ed = edt_enđate.getText().toString().trim();

        String urlGet = Url.webUrl + "WMSShipping/getpicM?mr_no=" + mr + "&mt_cd=" + mt + "&mt_barcode=" + lo + "&start=" + sd + "&end=" + ed;
        Log.d("Search_Pick", urlGet);
        new SearchPickFUN().execute(urlGet);
    }

    class SearchPickFUN extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            materPick = new ArrayList<>();
            try {

                JSONArray jsonArray = new JSONArray(s);
                Log.d("data",s);
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", PickingManualActivity.this);
                    buildRecyclerView();
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        //"id":568,
                        //"mr_no":"MR8",
                        //"mrd_no":"MRD9",
                        //"ml_no":"CAR4192_200915150555_001_200915150707_536",
                        //"mt_cd":"CAR4192_200915150555_001",
                        //"mt_type":"Type 1",
                        //"lot_qty":45,
                        //"mt_sts_cd":"Shipping Order",
                        //"mt_sts_cd_cd":"013",
                        //"mt_nm":"Material 4",
                        //"worker":"root",
                        //"lct_cd":"BIN-2",
                        //"work_dt":null

                        String id = jsonObject.getString("id").replace("null", "");
                        String mr_no = jsonObject.getString("mr_no").replace("null", "");
                        String mrd_no = jsonObject.getString("mrd_no").replace("null", "");
                        String ml_no = jsonObject.getString("ml_no").replace("null", "");
                        String mt_cd = jsonObject.getString("mt_cd").replace("null", "");
                        String mt_type = jsonObject.getString("mt_type").replace("null", "");
                        String lot_qty = jsonObject.getString("lot_qty").replace("null", "");
                        String mt_sts_cd = jsonObject.getString("mt_sts_cd").replace("null", "");
                        String mt_sts_cd_cd = jsonObject.getString("mt_sts_cd_cd").replace("null", "");
                        String mt_nm = jsonObject.getString("mt_nm").replace("null", "");
                        String worker = jsonObject.getString("worker").replace("null", "");
                        String lct_cd = jsonObject.getString("lct_cd").replace("null", "");
                        String work_dt = jsonObject.getString("work_dt").replace("null", "");

                        boolean check = false;
                        boolean pickStatus = false;
                        materPick.add(new PickManualItem(check, pickStatus, id, mr_no, mrd_no, ml_no, mt_cd, mt_type, lot_qty, mt_sts_cd, mt_sts_cd_cd, mt_nm, worker, lct_cd, work_dt));

                    }
                    buildRecyclerView();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", PickingManualActivity.this);
            }
        }
    }

    private void buildRecyclerView() {
        pickManualAdapter = new PickManualAdapter(materPick);

        rv_pickmanual.setLayoutManager(new LinearLayoutManager(this));
        rv_pickmanual.setHasFixedSize(true);
        rv_pickmanual.setAdapter(pickManualAdapter);

        pickManualAdapter.setOnItemClickListener(new PickManualAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemCheck(int position) {
                materPick.get(position).setCheck(true);
            }
        });

    }

    private void SelectDate(TextView tv) {
        final Calendar calendar = Calendar.getInstance();
        int ngay = calendar.get(Calendar.DATE);
        int thang = calendar.get(Calendar.MONTH);
        int nam = calendar.get(Calendar.YEAR);

        DatePickerDialog picker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                calendar.set(i, i1, i2);
//                    StrDate = ""+i+i1+i2;
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                    tv.setText(simpleDateFormat.format(calendar.getTime()));
            }
        }, nam, thang, ngay);

        picker.setButton(DialogInterface.BUTTON_NEUTRAL, "none", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                    tv.setText("");
            }
        });
        picker.show();
    }
}


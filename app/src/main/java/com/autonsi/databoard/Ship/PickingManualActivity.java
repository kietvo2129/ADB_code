package com.autonsi.databoard.Ship;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Url;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PickingManualActivity extends AppCompatActivity {

    private EditText edt_mrno, edt_mtno, edt_lotcode;
    private RecyclerView rv_pickmanual;
    private Button btnSearchPick, btnPickManual;
    private TextView edt_startdate, edt_enđate;
    private ArrayList<PickManualItem> materPick;
    private PickManualAdapter pickManualAdapter;
    private CheckBox check_all_picking;
    private List<ALLUSER> allusers;
    private Spinner spinner_all;
    private int SpinPosition = 0;

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
        spinner_all = findViewById(R.id.spinner_all);

        new getallusers().execute(Url.webUrl + "WMSShipping/getallusers");
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

        check_all_picking.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (materPick.size() > 0) {
                    for (int i = 0; i < materPick.size(); i++) {
                        materPick.get(i).setCheck(isChecked);
                    }
                    pickManualAdapter.notifyDataSetChanged();
                }
            }
        });

        btnPickManual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ab = "";
                for (int i = 0; i < materPick.size(); i++) {
                    if (materPick.get(i).isCheck()) {
                        ab = ab + materPick.get(i).getMl_no() + "\n";
                    }
                }
                Toast.makeText(PickingManualActivity.this, ab + "//"+ allusers.get(SpinPosition).getName(), Toast.LENGTH_SHORT).show();
            }
        });

        spinner_all.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                SpinPosition = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void SearchPick() {
        check_all_picking.setChecked(false);
        String mr = edt_mrno.getText().toString().trim();
        String mt = edt_mtno.getText().toString().trim();
        String lo = edt_lotcode.getText().toString().trim();
        String sd = edt_startdate.getText().toString().trim();
        String ed = edt_enđate.getText().toString().trim();

        String urlGet = Url.webUrl + "WMSShipping/getpicM?mr_no=" + mr + "&mt_cd=" + mt + "&mt_barcode=" + lo + "&start=" + sd + "&end=" + ed;
        Log.d("Search_Pick", urlGet);
        new SearchPickFUN().execute(urlGet);
    }

    class getallusers extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            allusers = new ArrayList<>();
            allusers.add(new ALLUSER("","","None"));
            try {

                JSONArray jsonArray = new JSONArray(s);
                Log.d("data", s);
                if (jsonArray.length() == 0) {
                    //AlerError.Baoloi("not found data", PickingManualActivity.this);
                    //buildRecyclerView();

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

                        String id = jsonObject.getString("userid").replace("null", "");
                        String code = jsonObject.getString("upw").replace("null", "");
                        String name = jsonObject.getString("uname").replace("null", "");

                        allusers.add(new ALLUSER(id, code, name));

                    }
                }

                ArrayAdapter adapter = new ArrayAdapter(PickingManualActivity.this, android.R.layout.simple_spinner_item, allusers);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spinner_all.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", PickingManualActivity.this);
            }
        }
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
                Log.d("data", s);
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
            public void onItemCheck(int position, boolean ischeck) {
                materPick.get(position).setCheck(ischeck);
            }

            @Override
            public void onItemClick(int position) {

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

    private class ALLUSER {


        String code;
        String name;
        String id;

        public ALLUSER(String id, String code, String name) {
            this.code = code;
            this.name = name;
            this.id = id;
        }

        public String getCode() {
            return code;
        }

        public String getName() {
            return name;
        }

        public String getId() {
            return id;
        }

        @Override
        public String toString() {
            return name;
        }
    }
}



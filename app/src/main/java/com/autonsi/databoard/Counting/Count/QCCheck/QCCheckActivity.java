package com.autonsi.databoard.Counting.Count.QCCheck;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.autonsi.databoard.ActivitiesLogin.SplashActivity;
import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Counting.Count.CountActivity;
import com.autonsi.databoard.Receving.ReceivingOder.ReceivingActivity;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class QCCheckActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    TextView tv_line,tv_Actual,tv_product_nm;
    String qc_code="";

    PieChart pieChart;
    ArrayList<QCcheckMaster> qCcheckMasterArrayList;
    QCcheckAdapter qCcheckAdapter;

    RecyclerView recyclerView;
    int numActual = 0;

    String line_no_d ="",line_no="";
    private int sumTongAll = 0;
    RelativeLayout rl;
    ArrayList<QCCheckDetailChartMaster>qcCheckDetailChartMasterArrayList;
    ArrayList<Entry> biegroupdef_qty;
    ArrayList<String> labelscheck_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qc_check);
        setTitle("QC Check");
        recyclerView = findViewById(R.id.rycview);
        tv_line = findViewById(R.id.tv_line);
        tv_Actual = findViewById(R.id.tv_Actual);
        tv_product_nm = findViewById(R.id.tv_product_nm);
        Intent intent = getIntent();
        tv_line.setText(intent.getStringExtra("line"));
        numActual = Integer.parseInt(intent.getStringExtra("tv_Actual")) - Integer.parseInt(intent.getStringExtra("tv_defective"));
        tv_Actual.setText(numActual+"");
        tv_product_nm.setText(intent.getStringExtra("product_nm"));
        qc_code = intent.getStringExtra("tv_qc");
        line_no_d = CountActivity.line_no_d;
        line_no= CountActivity.line_noend;
        rl = findViewById(R.id.rl);

        rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                popupAction();

            }
        });


        loadpageQccheck();
    }

    private void popupAction() {
        Dialog filterDialog;
        filterDialog = new Dialog(QCCheckActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        filterDialog.setContentView(R.layout.popup_detail_qc_check);
        filterDialog.setCancelable(false);
        filterDialog.getWindow().setLayout(getWidth(QCCheckActivity.this), ((getHight(QCCheckActivity.this) / 100) * 80));
        filterDialog.getWindow().setGravity(Gravity.BOTTOM);
        filterDialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.cancel();
            }
        });
        pieChart = filterDialog.findViewById(R.id.piechart);

        getDataQCcheckDetail();


        filterDialog.show();
    }

    private void getDataQCcheckDetail() {
        new getDataQCcheckDetail().execute(Url+"plan/GetChartPQC?line_no_d="+line_no_d);
        Log.e("getDataQCcheckDetail",Url+"plan/GetChartPQC?line_no_d="+line_no_d);
    }

    private class getDataQCcheckDetail extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            qcCheckDetailChartMasterArrayList = new ArrayList<>();
            biegroupdef_qty = new ArrayList<>();
            labelscheck_value = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("result")){
                    return;
                }
                JSONArray data = jsonObject.getJSONArray("data");
                if (data.length()==0){
                    return;
                }
                JSONObject jsonObject1 = data.getJSONObject(0);
                JSONArray Detail = jsonObject1.getJSONArray("Detail");
                if (Detail.length()==0){
                    return;
                }
                for (int i=0;i<Detail.length();i++){

                    JSONObject jsonObject2 = Detail.getJSONObject(i);
                    String check_value = jsonObject2.getString("check_value");
                    String def_qty = jsonObject2.getString("def_qty");
                    int numdef_qty = Integer.parseInt(def_qty);
                    qcCheckDetailChartMasterArrayList.add(new QCCheckDetailChartMaster(check_value,numdef_qty));
                    biegroupdef_qty.add(new Entry(numdef_qty,i));
                    labelscheck_value.add(check_value);
                }
                setChartQCdetail();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", QCCheckActivity.this);
            }

        }

    }


    private void setChartQCdetail() {
        PieDataSet pieDataSet = new PieDataSet(biegroupdef_qty,"");
        PieData data = new PieData(labelscheck_value,pieDataSet);
        pieDataSet.setValueTextColor(Color.BLUE);
        pieDataSet.setColors(ColorTemplate.COLORFUL_COLORS);
        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.invalidate();

    }

    private void loadpageQccheck() {
        new getQc_check().execute(Url+"plan/View_QC?qc_cd="+qc_code);
    }

    private class getQc_check extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            qCcheckMasterArrayList = new ArrayList<>();

            try {
                JSONObject object = new JSONObject(s);
                if (object.getString("result").equals("false") || object.getString("qc_itemcheck_mt").equals("[]")) {
                    Toast.makeText(QCCheckActivity.this, "No data", Toast.LENGTH_SHORT).show();
                    return;
                }
                JSONArray jsonArray = object.getJSONArray("qc_itemcheck_mt");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object1 = jsonArray.getJSONObject(i);

                    String qc_check_subject_id = object1.getString("qc_check_subject_id");
                    String qc_check_subject_code = object1.getString("qc_check_subject_code");
                    String qc_check_subject_name = i+1+". " + object1.getString("qc_check_subject_name");
                    String qc_check_subject_master_code = object1.getString("qc_check_subject_master_code");
                    String qc_check_subject_master_type = object1.getString("qc_check_subject_master_type");




                    JSONArray jsonArray_1 = object1.getJSONArray("view_qc_check_detail");
                    for (int j = 0; j < jsonArray_1.length(); j++) {
                        JSONObject object_2 = jsonArray_1.getJSONObject(j);

                        String qc_check_detail_id = object_2.getString("qc_check_detail_id");
                        String qc_check_detail_code = object_2.getString("qc_check_detail_code");
                        String qc_check_detail_name = object_2.getString("qc_check_detail_name");

                        int tong = 0;



                        qCcheckMasterArrayList.add(new QCcheckMaster(qc_check_subject_id,
                                qc_check_subject_code,
                                qc_check_subject_name,
                                qc_check_subject_master_code,
                                qc_check_subject_master_type,
                                qc_check_detail_id,
                                qc_check_detail_code,
                                qc_check_detail_name,tong));
                    }
                }

                buildQC();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", QCCheckActivity.this);
            }

        }

    }

    private void buildQC() {
        RecyclerView.LayoutManager mLayoutManager;
        mLayoutManager = new LinearLayoutManager(QCCheckActivity.this);
        qCcheckAdapter = new QCcheckAdapter(qCcheckMasterArrayList);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(qCcheckAdapter);

        qCcheckAdapter.setOnItemClickListener(new QCcheckAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

            }

            @Override
            public void onItemEditText(int position) {
                inputnum(position);
            }

            @Override
            public void onItemButtonUp(int position,TextView edittext) {
                edittext.setError(null);

                int sumTongArr = 0;
                for (int i = 0;i<qCcheckMasterArrayList.size();i++){
                    sumTongArr += qCcheckMasterArrayList.get(i).tong;
                }
                if (sumTongArr>=numActual){
                    edittext.setError("maximum");
                }else {
                    int tong = qCcheckMasterArrayList.get(position).tong;
                    tong +=1;
                    qCcheckMasterArrayList.get(position).setTong(tong);
                    qCcheckAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onItemButtonDown(int position,TextView edittext) {
                edittext.setError(null);
                int tong = qCcheckMasterArrayList.get(position).tong;
                tong -=1;
                if (tong<0){
                    edittext.setError("minimum");
                }else {
                    qCcheckMasterArrayList.get(position).setTong(tong);
                    qCcheckAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void inputnum(int pos) {
        AlertDialog.Builder builder = new AlertDialog.Builder(QCCheckActivity.this);
        View viewInflated = LayoutInflater.from(QCCheckActivity.this).inflate(R.layout.number_input_layout, null);
        builder.setTitle("Input number");
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);
        final int[] numinput = {0};
        int sumTongArr = 0;
        for (int i = 0;i<qCcheckMasterArrayList.size();i++){
            if (pos!=i) {
                sumTongArr += qCcheckMasterArrayList.get(i).tong;
            }
        }
        int finalSumTongArr = sumTongArr;
        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int in, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                int maximuminput = numActual - finalSumTongArr;
                if (input.getText().toString().length() == 0) {
                    numinput[0] = 0;
                } else {
                    numinput[0] = Integer.parseInt(input.getText().toString());
                    if (numinput[0] >maximuminput) {
                        input.setText(maximuminput+"");
                    }
                }
            }
        });


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                qCcheckMasterArrayList.get(pos).setTong(numinput[0]);
                qCcheckAdapter.notifyDataSetChanged();

            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menusave) {
            savecheckQC();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
            getMenuInflater().inflate(R.menu.menu_save_check_qc, menu);
        return true;
    }

    private void savecheckQC() {

        String check_qty_error="";
        String icdno="";


        for (int i=0;i<qCcheckMasterArrayList.size();i++){
            check_qty_error+=qCcheckMasterArrayList.get(i).tong+",";
            icdno+=qCcheckMasterArrayList.get(i).qc_check_detail_id+",";

            sumTongAll += qCcheckMasterArrayList.get(i).tong;
        }

        if (sumTongAll!=0) {
            new savecheckQC().execute(Url + "plan/Insert_Check_QC?line_no_d=" +
                    line_no_d +
                    "&master_code=" +
                    qc_code +
                    "&check_qty=" +
                    numActual +
                    "&check_qty_error=" +
                    check_qty_error.substring(0, check_qty_error.length() - 1) +
                    "&icdno=" +
                    icdno.substring(0, icdno.length() - 1));
        }else {
            AlerError.Baoloi("Please enter the number of defective items",QCCheckActivity.this);
        }
    }

    private class savecheckQC extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            qCcheckMasterArrayList = new ArrayList<>();

            try {
                JSONObject object = new JSONObject(s);
                if (object.getBoolean("result")){
                    Toast.makeText(QCCheckActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    loadpageQccheck();
                    numActual = numActual-sumTongAll;
                    tv_Actual.setText(numActual+"");
                    sumTongAll = 0;

                }else {
                    AlerError.Baoloi("QC check error, please check again",QCCheckActivity.this);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", QCCheckActivity.this);
            }

        }

    }
    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static int getHight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }

}
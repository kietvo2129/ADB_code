package com.autonsi.databoard.Counting.DashBoard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.autonsi.databoard.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CountingStatusActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;

    TextView tvtotal,tvEfficiency,tvDefective;
    List<DashboardCountingMaster> dashboardCountingMasterList;
    DashboardCountingAdapter dashboardCountingAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_status);
        BarChart barChart = (BarChart) findViewById (R.id.barchart);
        setTitle("Data Board Count");
        tvtotal = findViewById(R.id.tvtotal);
        tvEfficiency = findViewById(R.id.tvEfficiency);
        tvDefective = findViewById(R.id.tvDefective);
        mRecyclerView = findViewById(R.id.home_sensor_info);

        readDatabox();
        readLineinfor();
    }
    private void readLineinfor() {
        new Lineinfor().execute(Url + "Counting/GetCountingListToday");
        Log.d("Lineinfor", Url + "Counting/GetCountingListToday");
    }
    private class Lineinfor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dashboardCountingMasterList = new ArrayList<>();
            try {

                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length()==0){
                    return;
                }
                String id,line_no,line_nm,work_ymd,target_qty,work_time,qty_per_hour,update_time
                        ,current_act_qty,current_def_qty,latest_act_qty,line_no_d,latest_def_qty
                        ,remark,lunch_start_time,lunch_end_time,dinner_start_time,dinner_end_time,
                        Alarm_Range1,Alarm_Range2;

                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id = jsonObject.getString("id").replace("null","");
                    line_no = jsonObject.getString("line_no").replace("null","");
                    line_nm = jsonObject.getString("line_nm").replace("null","");
                    work_ymd = jsonObject.getString("work_ymd").replace("null","");
                    target_qty = jsonObject.getString("target_qty").replace("null","");
                    work_time = jsonObject.getString("work_time").replace("null","");
                    qty_per_hour = jsonObject.getString("qty_per_hour").replace("null","");
                    update_time = jsonObject.getString("update_time").replace("null","");
                    current_act_qty = jsonObject.getString("current_act_qty").replace("null","");
                    current_def_qty = jsonObject.getString("current_def_qty").replace("null","");
                    latest_act_qty = jsonObject.getString("latest_act_qty").replace("null","");
                    line_no_d = jsonObject.getString("line_no_d").replace("null","");
                    latest_def_qty = jsonObject.getString("latest_def_qty").replace("null","");
                    remark = jsonObject.getString("remark").replace("null","");
                    lunch_start_time = jsonObject.getString("lunch_start_time").replace("null","");
                    lunch_end_time = jsonObject.getString("lunch_end_time").replace("null","");
                    dinner_start_time = jsonObject.getString("dinner_start_time").replace("null","");
                    dinner_end_time = jsonObject.getString("dinner_end_time").replace("null","");
                    Alarm_Range1 = jsonObject.getString("Alarm_Range1").replace("null","");
                    Alarm_Range2 = jsonObject.getString("Alarm_Range2").replace("null","");

                    dashboardCountingMasterList.add(new DashboardCountingMaster(id,line_no,line_nm,work_ymd,target_qty,work_time,qty_per_hour,update_time
                            ,current_act_qty,current_def_qty,latest_act_qty,line_no_d,latest_def_qty
                            ,remark,lunch_start_time,lunch_end_time,dinner_start_time,dinner_end_time,
                            Alarm_Range1,Alarm_Range2));
                }
                buildRecyc();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountingStatusActivity.this);
            }


        }

    }
    private void buildRecyc() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(CountingStatusActivity.this);
        //mLayoutManager.setAutoMeasureEnabled(false);
        dashboardCountingAdapter = new DashboardCountingAdapter(dashboardCountingMasterList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //  Context context = mRecyclerView.getContext();
        mRecyclerView.setAdapter(dashboardCountingAdapter);

    }


    private void readDatabox() {
        new readDatabox().execute(Url + "Counting/GetCurrentLineDataContinuously");
        Log.d("readDatabox", Url + "Counting/GetCurrentLineDataContinuously");
    }

    private class readDatabox extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length()==0){
                    AlerError.Baoloi("Could not connect to server", CountingStatusActivity.this);
                    return;
                }
                JSONObject jsonObject= jsonArray.getJSONObject(0);
                DecimalFormat formatter = new DecimalFormat("#,###,###");

                tvtotal.setText(formatter.format(Integer.parseInt(jsonObject.getString("total_act_qty_today").replace("null","0"))));
                tvDefective.setText(formatter.format(Integer.parseInt(jsonObject.getString("total_def_qty_today").replace("null","0"))));

                double effi = Double.parseDouble(jsonObject.getString("efficiency").replace("null","0"));
                tvEfficiency.setText(String.format("%.0f",effi) +"%");

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountingStatusActivity.this);
            }
        }

    }

}
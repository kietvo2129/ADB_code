package com.quickblox.sample.videochat.java.AlarmData.Dashboard;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class AlarmDashboardActivity extends AppCompatActivity {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    TextView tvtotal, tvdoor, tvmotion, tvsmoke;
    List<AlarmDashboardMaster> alarmDashboardMasterList;
    List<String> Listdata_door = new ArrayList<String>();
    List<String> Listdata_Motion = new ArrayList<String>();
    List<String> Listdata_Smoke = new ArrayList<String>();
    List<String> listTime = new ArrayList<String>();
    LineChart mlinechart;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    AlarmDashboardAdapter alarmDashboardAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Dash Board Alarm");
        setContentView(R.layout.activity_alarm_dashboard);
        tvtotal = findViewById(R.id.tvtotal);
        tvdoor = findViewById(R.id.tvdoor);
        tvmotion = findViewById(R.id.tvmotion);
        tvsmoke = findViewById(R.id.tvsmoke);
        mlinechart = findViewById(R.id.linechart);
        mRecyclerView = findViewById(R.id.home_sensor_info);
        readDatabox();
        readdataChart();
        readSensorinfor();

    }
    private void readSensorinfor() {
        new readSensorinfor().execute(Url + "MotionAlarm/GetDataboard_CB?sts=today&rows=1000&page=1&sidx=&sord=asc");
        Log.d("readSensorinfor", Url + "MotionAlarm/GetDataboard_CB?sts=today&rows=1000&page=1&sidx=&sord=asc");
    }

    private class readSensorinfor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            alarmDashboardMasterList = new ArrayList<>();
            try {

                JSONObject jsonObjectA = new JSONObject(s);

                JSONArray jsonArray = jsonObjectA.getJSONArray("rows");
                if (jsonArray.length()==0){
                    return;
                }

                String ss_no,ss_nm,sts_nd,building_code,floor_code,Time;
                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    ss_no = jsonObject.getString("ss_no").replace("null","");
                    ss_nm = jsonObject.getString("ss_nm").replace("null","");
                    sts_nd = jsonObject.getString("sts_nd").replace("null","");
                    building_code = jsonObject.getString("building_code").replace("null","");
                    floor_code = jsonObject.getString("floor_code").replace("null","");
                    Time = jsonObject.getString("Time").replace("null","");
                    alarmDashboardMasterList.add(new AlarmDashboardMaster(ss_no,ss_nm,sts_nd,building_code,floor_code,Time));

                }
                buildRecyc();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", AlarmDashboardActivity.this);
            }


        }

    }
    private void buildRecyc() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(AlarmDashboardActivity.this);
        //mLayoutManager.setAutoMeasureEnabled(false);
        alarmDashboardAdapter = new AlarmDashboardAdapter(alarmDashboardMasterList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //  Context context = mRecyclerView.getContext();
        mRecyclerView.setAdapter(alarmDashboardAdapter);

    }

    private void readdataChart() {
        Listdata_door = new ArrayList<String>();
        Listdata_Motion = new ArrayList<String>();
        Listdata_Smoke = new ArrayList<String>();
        listTime = new ArrayList<String>();
        new readchart().execute(Url + "MotionAlarm/chart_data?sts=tieng_cuoi");
        Log.d("readchart", Url + "MotionAlarm/chart_data?sts=tieng_cuoi");
    }

    private class readchart extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("result").equals("false")) {
                    AlerError.Baoloi("No data!", AlarmDashboardActivity.this);
                    return;
                }
                JSONObject data_door = jsonObject.getJSONArray("data_door").getJSONObject(0);
                Listdata_door.add(data_door.getString("th1"));
                Listdata_door.add(data_door.getString("th2"));
                Listdata_door.add(data_door.getString("th3"));
                Listdata_door.add(data_door.getString("th4"));
                Listdata_door.add(data_door.getString("th5"));
                Listdata_door.add(data_door.getString("th6"));
                Listdata_door.add(data_door.getString("th7"));
                JSONObject data_Motion = jsonObject.getJSONArray("data_Motion").getJSONObject(0);
                Listdata_Motion.add(data_Motion.getString("th1"));
                Listdata_Motion.add(data_Motion.getString("th2"));
                Listdata_Motion.add(data_Motion.getString("th3"));
                Listdata_Motion.add(data_Motion.getString("th4"));
                Listdata_Motion.add(data_Motion.getString("th5"));
                Listdata_Motion.add(data_Motion.getString("th6"));
                Listdata_Motion.add(data_Motion.getString("th7"));
                JSONObject data_Smoke = jsonObject.getJSONArray("data_Smoke").getJSONObject(0);
                Listdata_Smoke.add(data_Smoke.getString("th1"));
                Listdata_Smoke.add(data_Smoke.getString("th2"));
                Listdata_Smoke.add(data_Smoke.getString("th3"));
                Listdata_Smoke.add(data_Smoke.getString("th4"));
                Listdata_Smoke.add(data_Smoke.getString("th5"));
                Listdata_Smoke.add(data_Smoke.getString("th6"));
                Listdata_Smoke.add(data_Smoke.getString("th7"));
                JSONArray array = jsonObject.getJSONArray("array");
                for (int i = 0; i < array.length(); i++) {
                    listTime.add(array.getString(i));
                }

                setChart();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", AlarmDashboardActivity.this);
            }

        }

    }

    private void setChart() {
        LineDataSet lineDataSet2 = new LineDataSet(dataDoor(), "Door");
        LineDataSet lineDataSet1 = new LineDataSet(dataMotion(), "Motion");
        LineDataSet lineDataSet3 = new LineDataSet(dataSmoke(), "Smoke");
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
//        XAxis xAxis = mlinechart.getXAxis();
//        xAxis.setValueFormatter(new MyAxisValue());
        lineDataSet2.setColor(Color.parseColor("#28A745"));
        lineDataSet1.setColor(Color.parseColor("#3E95CD"));
        lineDataSet3.setColor(Color.parseColor("#BEA6FF"));
        lineDataSet2.setCircleColorHole(Color.parseColor("#28A745"));
        lineDataSet1.setCircleColorHole(Color.parseColor("#3E95CD"));
        lineDataSet3.setCircleColorHole(Color.parseColor("#BEA6FF"));
        lineDataSet2.setCircleColor(Color.parseColor("#28A745"));
        lineDataSet1.setCircleColor(Color.parseColor("#3E95CD"));
        lineDataSet3.setCircleColor(Color.parseColor("#BEA6FF"));
        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet3);
        mlinechart.setDescription("");
        LineData data = new LineData(listTime,dataSets);
        mlinechart.setData(data);
        mlinechart.invalidate();


    }

    private ArrayList<Entry> dataDoor() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < Listdata_door.size(); i++) {
            int value = Integer.parseInt(Listdata_door.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }

    private ArrayList<Entry> dataMotion() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < Listdata_Motion.size(); i++) {
            int value = Integer.parseInt(Listdata_Motion.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }

    private ArrayList<Entry> dataSmoke() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < Listdata_Smoke.size(); i++) {
            int value = Integer.parseInt(Listdata_Smoke.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }
//
//
//    private class MyAxisValue implements IAxisValueFormatter {
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//
//            Log.e("QQQQ", value + "");
//            if (value == 0f) {
//                return listTime.get(0);
//            } else if (value == 1f) {
//                return listTime.get(1);
//            } else if (value == 2f) {
//                return listTime.get(2);
//            } else if (value == 3f) {
//                return listTime.get(3);
//            } else if (value == 4f) {
//                return listTime.get(4);
//            } else if (value == 5f) {
//                return listTime.get(5);
//            }else if (value == 6f) {
//                return listTime.get(6);
//            } else {
//                return "";
//            }
//        }
//    }


    private void readDatabox() {
        new readAverage().execute(Url + "MotionAlarm/api_view_Alarm_Dashboard");
        Log.d("readAverage", Url + "MotionAlarm/api_view_Alarm_Dashboard");
    }

    private class readAverage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject jsonObject = new JSONObject(s);
//                if (jsonObject.getString("result").trim().equals("false")){
//                    AlerError.Baoloi("No Data", getActivity());
//                    return;
//                }
                tvtotal.setText(jsonObject.getString("total_today"));
                tvdoor.setText(jsonObject.getString("door_today"));
                tvmotion.setText(jsonObject.getString("Motion_today"));
                tvsmoke.setText(jsonObject.getString("Smoke_today"));

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", AlarmDashboardActivity.this);
            }


        }

    }
}
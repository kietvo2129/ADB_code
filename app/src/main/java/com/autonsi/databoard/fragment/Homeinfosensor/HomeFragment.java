package com.autonsi.databoard.fragment.Homeinfosensor;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;

import android.util.Log;
import android.view.LayoutInflater;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
//import com.github.mikephil.charting.formatter.IAxisValueFormatter;
//import com.github.mikephil.charting.interfaces.datasets.ILineDataSet;
import com.autonsi.databoard.AlerError.AlerError;

import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class HomeFragment extends Fragment {
    String Url = com.autonsi.databoard.Url.webUrl;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    List<String> ListAveragePressure = new ArrayList<String>();
    List<String> ListAveragePower = new ArrayList<String>();
    List<String> AverageTemperature = new ArrayList<String>();
    List<String> AverageHumidity = new ArrayList<String>();
    List<String> listTime = new ArrayList<String>();
    TextView tvisu, tvtemp, tvpress,tvhumi,tvPower;

    HomesensorinforAdapter homesensorinforAdapter;

    List<HomesensorinforMaster> homesensorinforMasterList;

    LineChart mlinechart;
    ImageView image;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        tvisu = view.findViewById(R.id.tvisu);
        tvpress = view.findViewById(R.id.tvpress);
        tvtemp = view.findViewById(R.id.tvtemp);
        tvhumi = view.findViewById(R.id.tvhumi);
        tvPower = view.findViewById(R.id.tvPower);
        mRecyclerView = view.findViewById(R.id.home_sensor_info);

        mlinechart = view.findViewById(R.id.linechart);
        image = view.findViewById(R.id.image);
        //readdataChart();
        readSensorinfor();
        readDatah1();
        return view;
    }

    private void readSensorinfor() {
        new readSensorinfor().execute(Url + "Monitor/GetAllSensorIssueDataDaily");
        Log.d("readSensorinfor", Url + "Monitor/GetAllSensorIssueDataDaily");
    }

    private class readSensorinfor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            homesensorinforMasterList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length()==0){
                    return;
                }

                String id,ss_no,current_temp,current_press,current_humi,current_pow,temp_issue
                        ,humi_issue,pow_issue,time_update,time_finish,info_warning,info_finish
                        ,sts_send,min_temp,max_temp,min_press,max_press,min_humi,max_humi,min_pow,max_pow,
                        company_code,branch_code,country_code,city_code,building_code,floor_code,room_code
                        ,ss_nm,sts_nd,company_name,branch_name,building_name,floor_name,room_name,temp_issue_nm
                        ,humi_issue_nm,press_issue_nm,pow_issue_nm,sts_send_nm;
                for (int i = 0; i<jsonArray.length();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id = jsonObject.getString("id").replace("null","");
                    ss_no = jsonObject.getString("ss_no").replace("null","");
                    current_temp = jsonObject.getString("current_temp").replace("null","");
                    current_press = jsonObject.getString("current_press").replace("null","");
                    current_humi = jsonObject.getString("current_humi").replace("null","");
                    current_pow = jsonObject.getString("current_pow").replace("null","");
                    temp_issue = jsonObject.getString("temp_issue").replace("null","");
                    humi_issue = jsonObject.getString("humi_issue").replace("null","");
                    pow_issue = jsonObject.getString("pow_issue").replace("null","");
                    time_update = jsonObject.getString("time_update").replace("null","");
                    time_finish = jsonObject.getString("time_finish").replace("null","");
                    info_warning = jsonObject.getString("info_warning").replace("null","");
                    info_finish = jsonObject.getString("info_finish").replace("null","");
                    sts_send = jsonObject.getString("sts_send").replace("null","");
                    min_temp = jsonObject.getString("min_temp").replace("null","");
                    max_temp = jsonObject.getString("max_temp").replace("null","");
                    min_press = jsonObject.getString("min_press").replace("null","");
                    max_press = jsonObject.getString("max_press").replace("null","");
                    min_humi = jsonObject.getString("min_humi").replace("null","");
                    max_humi = jsonObject.getString("max_humi").replace("null","");
                    min_pow = jsonObject.getString("min_pow").replace("null","");
                    max_pow = jsonObject.getString("max_pow").replace("null","");
                    company_code = jsonObject.getString("company_code").replace("null","");
                    branch_code = jsonObject.getString("branch_code").replace("null","");
                    country_code = jsonObject.getString("country_code").replace("null","");
                    city_code = jsonObject.getString("city_code").replace("null","");
                    building_code = jsonObject.getString("building_code").replace("null","");
                    floor_code = jsonObject.getString("floor_code").replace("null","");
                    room_code = jsonObject.getString("room_code").replace("null","");
                    ss_nm = jsonObject.getString("ss_nm").replace("null","");
                    sts_nd = jsonObject.getString("sts_nd").replace("null","");
                    company_name = jsonObject.getString("company_name").replace("null","");
                    branch_name = jsonObject.getString("branch_name").replace("null","");
                    building_name = jsonObject.getString("building_name").replace("null","");
                    floor_name = jsonObject.getString("floor_name").replace("null","");
                    room_name = jsonObject.getString("room_name").replace("null","");
                    temp_issue_nm = jsonObject.getString("temp_issue_nm").replace("null","");
                    humi_issue_nm = jsonObject.getString("humi_issue_nm").replace("null","");
                    press_issue_nm = jsonObject.getString("press_issue_nm").replace("null","");
                    pow_issue_nm = jsonObject.getString("pow_issue_nm").replace("null","");
                    sts_send_nm = jsonObject.getString("sts_send_nm").replace("null","");
                    homesensorinforMasterList.add(new HomesensorinforMaster(id,ss_no,current_temp,current_press,current_humi,current_pow,temp_issue
                            ,humi_issue,pow_issue,time_update,time_finish,info_warning,info_finish
                            ,sts_send,min_temp,max_temp,min_press,max_press,min_humi,max_humi,min_pow,max_pow,
                            company_code,branch_code,country_code,city_code,building_code,floor_code,room_code
                            ,ss_nm,sts_nd,company_name,branch_name,building_name,floor_name,room_name,temp_issue_nm
                            ,humi_issue_nm,press_issue_nm,pow_issue_nm,sts_send_nm));

                }
                buildRecyc();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getActivity());
            }


        }

    }

    private void buildRecyc() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(getActivity());
        //mLayoutManager.setAutoMeasureEnabled(false);
        homesensorinforAdapter = new HomesensorinforAdapter(homesensorinforMasterList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //  Context context = mRecyclerView.getContext();
        mRecyclerView.setAdapter(homesensorinforAdapter);

    }

    private void readdataChart() {
        ListAveragePressure = new ArrayList<String>();
        ListAveragePower = new ArrayList<String>();
        AverageTemperature = new ArrayList<String>();
        AverageHumidity = new ArrayList<String>();
        listTime = new ArrayList<String>();
        new GetAveragePower().execute(Url + "DashBoard/GetAveragePowerOneHourBefore");
        Log.d("GetAveragePower", Url + "DashBoard/GetAveragePowerOneHourBefore");

    }

    private void readDatah1() {
        new readAverage().execute(Url + "DashBoard/GetCurrentTemperature");
        Log.d("readAverage", Url + "DashBoard/GetCurrentTemperature");
//        new readissues().execute(Url + "DashBoard/GetTotalIssuesPerDay");
//        Log.d("readissues", Url + "DashBoard/GetTotalIssuesPerDay");
    }

    private class readAverage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
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
                tvtemp.setText(jsonObject.getString("tempCount"));
                tvpress.setText(jsonObject.getString("pressCount"));
                tvhumi.setText(jsonObject.getString("humiCount"));
                tvPower.setText(jsonObject.getString("powCount"));
                tvisu.setText(jsonObject.getString("totalIssues"));

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getActivity());
            }


        }

    }

//    private class readissues extends AsyncTask<String, Void, String> {
//        @Override
//        protected String doInBackground(String... strings) {
//            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            tvisu.setText(s.trim());
//        }
//
//    }


    private class GetAveragePressure extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", getActivity());
                    return;
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String t10, t20, t30, t40, t50, t60;
                t10 = jsonObject.getString("t10");
                t20 = jsonObject.getString("t20");
                t30 = jsonObject.getString("t30");
                t40 = jsonObject.getString("t40");
                t50 = jsonObject.getString("t50");
                t60 = jsonObject.getString("t60");
                ListAveragePressure.add(t60);
                ListAveragePressure.add(t50);
                ListAveragePressure.add(t40);
                ListAveragePressure.add(t30);
                ListAveragePressure.add(t20);
                ListAveragePressure.add(t10);
                new GetAverageTemperature().execute(Url + "DashBoard/GetAverageTemperatureOneHourBefore");
                Log.d("GetAverageTemperature", Url + "DashBoard/GetAverageTemperatureOneHourBefore");


            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getActivity());
            }

        }

    }

    private void setChart() {
        LineDataSet lineDataSet2 = new LineDataSet(dataPower(), "Power");
        LineDataSet lineDataSet1 = new LineDataSet(dataPressure(), "Pressure");
        LineDataSet lineDataSet3 = new LineDataSet(dataTemperature(), "Temperature");
        LineDataSet lineDataSet4 = new LineDataSet(dataHumidity(), "Humidity");
        ArrayList<LineDataSet> dataSets = new ArrayList<>();
//        XAxis xAxis = mlinechart.getXAxis();
//        xAxis.setValueFormatter(new MyAxisValue());
        lineDataSet2.setColor(Color.parseColor("#BEA6FF"));
        lineDataSet1.setColor(Color.parseColor("#BDF505"));
        lineDataSet3.setColor(Color.parseColor("#F50505"));
        lineDataSet4.setColor(Color.parseColor("#3E95CD"));
        lineDataSet2.setCircleColorHole(Color.parseColor("#BEA6FF"));
        lineDataSet1.setCircleColorHole(Color.parseColor("#28A745"));
        lineDataSet3.setCircleColorHole(Color.parseColor("#F50505"));
        lineDataSet4.setCircleColorHole(Color.parseColor("#3E95CD"));
        lineDataSet2.setCircleColor(Color.parseColor("#BEA6FF"));
        lineDataSet1.setCircleColor(Color.parseColor("#28A745"));
        lineDataSet3.setCircleColor(Color.parseColor("#F50505"));
        lineDataSet4.setCircleColor(Color.parseColor("#3E95CD"));

        dataSets.add(lineDataSet2);
        dataSets.add(lineDataSet1);
        dataSets.add(lineDataSet3);
        dataSets.add(lineDataSet4);
        mlinechart.setDescription("");

        LineData data = new LineData(listTime,dataSets);
        mlinechart.setData(data);
        mlinechart.invalidate();


    }

    private ArrayList<Entry> dataHumidity() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < AverageHumidity.size(); i++) {
            float value = Float.parseFloat(AverageHumidity.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }

    private ArrayList<Entry> dataTemperature() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < AverageTemperature.size(); i++) {
            float value = Float.parseFloat(AverageTemperature.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }

    private ArrayList<Entry> dataPressure() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < ListAveragePressure.size(); i++) {
            float value = Float.parseFloat(ListAveragePressure.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }

    private ArrayList<Entry> dataPower() {

        ArrayList<Entry> datavalue = new ArrayList<Entry>();

        for (int i = 0; i < ListAveragePower.size(); i++) {
            float value = Float.parseFloat(ListAveragePower.get(i));
            datavalue.add(new Entry(value, i));
        }

        return datavalue;
    }

    private class GetAveragePower extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", getActivity());
                    return;
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String t10, t20, t30, t40, t50, t60;
                t10 = jsonObject.getString("t10");
                t20 = jsonObject.getString("t20");
                t30 = jsonObject.getString("t30");
                t40 = jsonObject.getString("t40");
                t50 = jsonObject.getString("t50");
                t60 = jsonObject.getString("t60");
                ListAveragePower.add(t60);
                ListAveragePower.add(t50);
                ListAveragePower.add(t40);
                ListAveragePower.add(t30);
                ListAveragePower.add(t20);
                ListAveragePower.add(t10);


                new GetAveragePressure().execute(Url + "DashBoard/GetAveragePressureOneHourBefore");
                Log.d("GetAveragePressure", Url + "DashBoard/GetAveragePressureOneHourBefore");

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getActivity());
            }

        }

    }

    private class GetAverageTemperature extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", getActivity());
                    return;
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String t10, t20, t30, t40, t50, t60, t1, t2, t3, t4, t5, t6;
                t10 = jsonObject.getString("t10");
                t20 = jsonObject.getString("t20");
                t30 = jsonObject.getString("t30");
                t40 = jsonObject.getString("t40");
                t50 = jsonObject.getString("t50");
                t60 = jsonObject.getString("t60");
                AverageTemperature.add(t60);
                AverageTemperature.add(t50);
                AverageTemperature.add(t40);
                AverageTemperature.add(t30);
                AverageTemperature.add(t20);
                AverageTemperature.add(t10);
                t1 = jsonObject.getString("t1");
                t2 = jsonObject.getString("t2");
                t3 = jsonObject.getString("t3");
                t4 = jsonObject.getString("t4");
                t5 = jsonObject.getString("t5");
                t6 = jsonObject.getString("t6");

                listTime.add(t6);
                listTime.add(t5);
                listTime.add(t4);
                listTime.add(t3);
                listTime.add(t2);
                listTime.add(t1);
                new GetAverageHumidity().execute(Url + "DashBoard/GetAverageHumidityOneHourBefore");
                Log.d("GetAverageHumidity", Url + "DashBoard/GetAverageHumidityOneHourBefore");
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getActivity());
            }

        }

    }

    private class GetAverageHumidity extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", getActivity());
                    return;
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                String t10, t20, t30, t40, t50, t60, t1, t2, t3, t4, t5, t6;
                t10 = jsonObject.getString("t10");
                t20 = jsonObject.getString("t20");
                t30 = jsonObject.getString("t30");
                t40 = jsonObject.getString("t40");
                t50 = jsonObject.getString("t50");
                t60 = jsonObject.getString("t60");
                AverageHumidity.add(t60);
                AverageHumidity.add(t50);
                AverageHumidity.add(t40);
                AverageHumidity.add(t30);
                AverageHumidity.add(t20);
                AverageHumidity.add(t10);

                setChart();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getActivity());
            }


        }

    }

//    private class MyAxisValue implements IAxisValueFormatter {
//
//        @Override
//        public String getFormattedValue(float value, AxisBase axis) {
//
//            Log.e("QQQQ", value + "");
//            if (value == 0f) {
//                return listTime.get(5);
//            } else if (value == 1f) {
//                return listTime.get(4);
//            } else if (value == 2f) {
//                return listTime.get(3);
//            } else if (value == 3f) {
//                return listTime.get(2);
//            } else if (value == 4f) {
//                return listTime.get(1);
//            } else if (value == 5f) {
//                return listTime.get(0);
//            } else {
//                return "";
//            }
//        }
//    }
}

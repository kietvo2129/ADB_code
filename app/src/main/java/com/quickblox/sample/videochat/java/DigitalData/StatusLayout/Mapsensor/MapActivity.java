package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.bumptech.glide.Glide;

import com.pedro.vlc.VlcVideoLibrary;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.CCTV.CCTVList.CCTVActivity;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapBuild.MapBuildActivity;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapFloor.MapFloorActivity;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MapActivity extends AppCompatActivity{
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;

    ImageView imageView;
    RelativeLayout rl;
    TextView location;

    ArrayList<MapMater> mapMaterArrayList;
    ArrayList<MapInfoSensor> mapInfoSensorArrayList;

    ArrayList<sensor_map_Master> sensor_map_masterArrayList;
    sensor_mapAdapter sensor_mapAdapters;
    int finalHeight, finalWidth;
    private String[] options = new String[]{":fullscreen"};
    private RecyclerView.LayoutManager mLayoutManager;

    Button buttontoggle;
    int bientam = -1;
    RecyclerView home_sensor_info;
    private String nmCCTV="";
    private VlcVideoLibrary vlcVideoLibrary;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_map);
        setTitle("Sensor");
        rl = (RelativeLayout) findViewById(R.id.rl);
        imageView = findViewById(R.id.imageview);
        location = findViewById(R.id.location);
        home_sensor_info = findViewById(R.id.home_sensor_info);
        location.setText(MapBuildActivity.building_name + " - " + MapFloorActivity.floor_name);

//load Image
        Glide.with(MapActivity.this)
                .load(Url + "Images/Monitor/" + MapFloorActivity.floor_image)
                .error(R.drawable.errorimage)
                .into(imageView);
        Log.d("image", Url + "Images/Monitor/" + MapFloorActivity.floor_image);


        new readSensor().execute(Url + "Monitor/get_info_sensor?" + "build=" + MapBuildActivity.building_code + "&floor=" + MapFloorActivity.floor_code);
        Log.d("readSensor", Url + "Monitor/get_info_sensor?" + "build=" + MapBuildActivity.building_code + "&floor=" + MapFloorActivity.floor_code);

        new readSensor_map().execute(Url + "Monitor/GetMonitor?id=" + MapFloorActivity.floor_id);
        Log.d("readSensor_map", Url + "Monitor/GetMonitor?id=" + MapFloorActivity.floor_id);


    }


    private class readSensor_map extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sensor_map_masterArrayList = new ArrayList<>();
            String SensorID, SensorName, Temperature, Humidity, Power, Pressure;

            try {

                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", MapActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);
                    SensorID = objectRow.getString("ss_no").replace("null", "");
                    SensorName = objectRow.getString("ss_nm").replace("null", "");
                    Temperature = objectRow.getString("current_temp").replace("-999", "");
                    Humidity = objectRow.getString("current_humi").replace("-999", "");
                    Power = objectRow.getString("current_pow").replace("-999", "");
                    Pressure = objectRow.getString("current_press").replace("-999", "");
                    sensor_map_masterArrayList.add(new sensor_map_Master(SensorID, SensorName, Temperature, Humidity, Power, Pressure));
                }

                buildrecyc();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", MapActivity.this);
            }
        }

    }

    private void buildrecyc() {

        home_sensor_info.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(MapActivity.this);
        sensor_mapAdapters = new sensor_mapAdapter(sensor_map_masterArrayList);
        home_sensor_info.setLayoutManager(mLayoutManager);
        home_sensor_info.setAdapter(sensor_mapAdapters);

    }


    private class readSensor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mapMaterArrayList = new ArrayList<>();
            String ssid, ss_no, ss_nm, top_position, left_posistion,type;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", MapActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ssid = objectRow.getString("ssid").replace("null", "");
                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    type = objectRow.getString("type").replace("null", "");
                    top_position = objectRow.getString("top_position").replace("null", "");
                    left_posistion = objectRow.getString("left_posistion").replace("null", "");


                    mapMaterArrayList.add(new MapMater(ssid, ss_no, ss_nm, top_position, left_posistion,type));
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finalHeight = imageView.getMeasuredHeight();
                        finalWidth = imageView.getMeasuredWidth();
                        SetSensor();
                    }
                }, 1500);

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", MapActivity.this);
            }
        }

    }

    private void SetSensor() {

        if (mapMaterArrayList == null) {
            return;
        }

        final ImageView[] tv = new ImageView[mapMaterArrayList.size() + 1];
        final TextView[] tvs = new TextView[mapMaterArrayList.size() + 1];
        for (int i = 0; i < mapMaterArrayList.size(); i++) {
            tv[i + 1] = new ImageView(this);
            tvs[i + 1] = new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

            String leftx = mapMaterArrayList.get(i).getLeft_posistion().replace("%", "");
            String topx = mapMaterArrayList.get(i).getTop_position().replace("%", "");

            double dbleft = Double.parseDouble(leftx);
            double dbtop = Double.parseDouble(topx);
            if (dbleft > 92) {
                dbleft = 92;
            }

            if (dbtop > 92) {
                dbtop = 92;
            }
            int left = (int) (finalWidth * dbleft / 100);
            int top = (int) (finalHeight * dbtop / 100);
            int left2 = (int) (finalWidth * dbleft / 100)- 30;
            int top2 = (int) (finalHeight * dbtop / 100)+ 30;

            params.leftMargin = left;
            params.topMargin = top;
            params2.leftMargin = left2;
            params2.topMargin = top2;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mapMaterArrayList.get(i).getType().equals("SS")) {
                    tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_view));
                    tvs[i + 1].setText(mapMaterArrayList.get(i).getSs_nm());
                    tvs[i + 1].setTextColor(Color.parseColor("#000000"));
                }else if (mapMaterArrayList.get(i).getType().equals("CMR")) {
                    tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_cmr));
                    tvs[i + 1].setText(mapMaterArrayList.get(i).getSs_nm());
                    tvs[i + 1].setTextColor(Color.parseColor("#000000"));
                }
            }
            tv[i + 1].setLayoutParams(params);
            tvs[i + 1].setLayoutParams(params2);
            int finalI = i;
            tv[i + 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bientam = finalI;
                    if (mapMaterArrayList.get(finalI).getType().equals("SS")) {
                        new readInfosensor().execute(Url + "/Monitor/ShowDataSS?ss_no=" + mapMaterArrayList.get(finalI).ss_no);
                        Log.d("readInfosensor", Url + "/Monitor/ShowDataSS?ss_no=" + mapMaterArrayList.get(finalI).ss_no);
                    }else if (mapMaterArrayList.get(finalI).getType().equals("CMR")) {

                        Intent intent = new Intent(MapActivity.this, CCTVActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("nm_camera", mapMaterArrayList.get(finalI).getSs_no());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }


                }
            });
            rl.addView(tv[i + 1]);
            rl.addView(tvs[i + 1]);
        }

    }


    private class readInfosensor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mapInfoSensorArrayList = new ArrayList<>();
            String ssid, ss_no, ss_nm, current_temp, current_press, current_pow, current_humi, time_update,
                    min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", MapActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ssid = objectRow.getString("ssid").replace("null", "");
                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    current_temp = objectRow.getString("current_temp").replace("null", "0").replace("-999", "");
                    current_press = objectRow.getString("current_press").replace("null", "0").replace("-999", "");
                    current_pow = objectRow.getString("current_pow").replace("null", "0").replace("-999", "");
                    current_humi = objectRow.getString("current_humi").replace("null", "0").replace("-999", "");
                    time_update = objectRow.getString("time_update").replace("null", "");
                    min_temp = objectRow.getString("min_temp").replace("null", "0").replace("-999", "");
                    max_temp = objectRow.getString("max_temp").replace("null", "0").replace("-999", "");
                    min_press = objectRow.getString("min_press").replace("null", "0").replace("-999", "");
                    max_press = objectRow.getString("max_press").replace("null", "0").replace("-999", "");
                    min_humi = objectRow.getString("min_humi").replace("null", "0").replace("-999", "");
                    max_humi = objectRow.getString("max_humi").replace("null", "0").replace("-999", "");
                    min_pow = objectRow.getString("min_pow").replace("null", "0").replace("-999", "");
                    max_pow = objectRow.getString("max_pow").replace("null", "0").replace("-999", "");

                    mapInfoSensorArrayList.add(new MapInfoSensor(ssid, ss_no, ss_nm, current_temp, current_press, current_pow, current_humi, time_update,
                            min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow));
                }

                displaysensor();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", MapActivity.this);
            }
        }

    }

    private void displaysensor() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_history);//popup_sensor);
        new getOn().execute(Url + "Monitor/Searchnhietdo?ss_no=" + mapMaterArrayList.get(bientam).ss_no);
        Log.d("getOn", Url + "Monitor/Searchnhietdo?ss_no=" + mapMaterArrayList.get(bientam).ss_no);

        TextView tvid, tvname, tvtemp, tvhum, tvlocation, tvtime,tvpow,tvpress;
        LinearLayout lnear;
        ImageView icon_temp, icon_hum,icon_press,icon_pow;
        RelativeLayout rlpow,rlpress,rltemp,rlhum;


        tvid = dialog.findViewById(R.id.tvid);
        tvname = dialog.findViewById(R.id.tvname);
        tvtemp = dialog.findViewById(R.id.tvtemp);
        tvhum = dialog.findViewById(R.id.tvhum);
        tvpress = dialog.findViewById(R.id.tvpress);
        tvpow = dialog.findViewById(R.id.tvpow);
        tvlocation = dialog.findViewById(R.id.tvlocation);
        tvlocation.setVisibility(View.GONE);
        tvtime = dialog.findViewById(R.id.tvtime);
        icon_temp = dialog.findViewById(R.id.icon_temp);
        icon_hum = dialog.findViewById(R.id.icon_hum);
        icon_press = dialog.findViewById(R.id.icon_press);
        icon_pow = dialog.findViewById(R.id.icon_pow);
        lnear = dialog.findViewById(R.id.lnear);
        rlpow = dialog.findViewById(R.id.rlpow);
        rlpress = dialog.findViewById(R.id.rlpress);
        rltemp = dialog.findViewById(R.id.rltemp);
        rlhum = dialog.findViewById(R.id.rlhum);
        buttontoggle = dialog.findViewById(R.id.buttontoggle);

        tvid.setText(mapInfoSensorArrayList.get(0).getSs_no());
        tvname.setText(mapInfoSensorArrayList.get(0).getSs_nm());

        if (mapInfoSensorArrayList.get(0).getSs_no().equals("SS-18")){
            buttontoggle.setVisibility(View.GONE);
        }

        buttontoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buttontoggle.getText().equals("ON")){
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=002");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=002");
                }else {
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=001");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=001");
                }
            }
        });


        //tvlocation.setText(mapInfoSensorArrayList.get(0).getBuilding_name() + " - "+ mapInfoSensorArrayList.get(0).getFloor_name());
        tvtime.setText(mapInfoSensorArrayList.get(0).getTime_update());

        if (!mapInfoSensorArrayList.get(0).getCurrent_temp().equals("")){
            rltemp.setVisibility(View.VISIBLE);
            tvtemp.setText(mapInfoSensorArrayList.get(0).getCurrent_temp() + "°C");
           // if (mapInfoSensorArrayList.get(0).getTemp_issue().equals("003")) {
                icon_temp.setBackgroundResource(R.drawable.ic_temgreen);
//            } else {
//                icon_temp.setBackgroundResource(R.drawable.ic_temred);
//            }
        }else {
            rltemp.setVisibility(View.GONE);
        }

        if (!mapInfoSensorArrayList.get(0).getCurrent_humi().equals("")){
            rlhum.setVisibility(View.VISIBLE);
            tvhum.setText(mapInfoSensorArrayList.get(0).getCurrent_humi() + "%");
           // if (mapInfoSensorArrayList.get(0).getHumi_issue().equals("006")) {
                icon_hum.setBackgroundResource(R.drawable.ic_humgreen);
           // } else {
            //    icon_hum.setBackgroundResource(R.drawable.ic_humred);
            //}
        }else {
            rlhum.setVisibility(View.GONE);
        }

        if (!mapInfoSensorArrayList.get(0).getCurrent_press().equals("")){
            rlpress.setVisibility(View.VISIBLE);
            tvpress.setText(mapInfoSensorArrayList.get(0).getCurrent_press() + "Pa");
           // if (mapInfoSensorArrayList.get(0).getPress_issue().equals("009")) {
                icon_press.setBackgroundResource(R.drawable.ic_pressgreen);
           // } else {
           //     icon_press.setBackgroundResource(R.drawable.ic_pressred);
           // }
        }else {
            rlpress.setVisibility(View.GONE);
        }

        if (!mapInfoSensorArrayList.get(0).getCurrent_pow().equals("")){
            rlpow.setVisibility(View.VISIBLE);
            tvpow.setText(mapInfoSensorArrayList.get(0).getCurrent_pow() + "W");
           // if (mapInfoSensorArrayList.get(0).getPow_issue().equals("012")) {
                icon_pow.setBackgroundResource(R.drawable.ic_powgreen);
           // } else {
           //     icon_pow.setBackgroundResource(R.drawable.ic_powred);
           // }
        }else {
            rlpow.setVisibility(View.GONE);
        }

        dialog.show();
    }
    private class getOn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("result").replace("[\"", "").replace("\"]", "").equals("001")) {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                } else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private class setOn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("result")) {
                    if (buttontoggle.getText().equals("ON")){
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    }else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    }
                }else {
                    if (buttontoggle.getText().equals("ON")){
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    }else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                if (buttontoggle.getText().equals("ON")){
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                }else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            }
        }

    }


}

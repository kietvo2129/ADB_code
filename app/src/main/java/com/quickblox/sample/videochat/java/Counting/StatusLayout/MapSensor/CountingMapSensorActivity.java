package com.quickblox.sample.videochat.java.Counting.StatusLayout.MapSensor;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapSensor.MapsensorAlarmAdapter;
import com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapSensor.MapsensorAlarmMaster;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.CCTV.CCTVList.CCTVActivity;
import com.quickblox.sample.videochat.java.Counting.StatusLayout.CountingMapBuildActivity;
import com.quickblox.sample.videochat.java.Counting.StatusLayout.CountingMapFloorActivity;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapInfoSensor;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapMater;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CountingMapSensorActivity extends AppCompatActivity {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;

    ImageView imageView;
    RelativeLayout rl;
    TextView location;

    ArrayList<MapMater> mapMaterArrayList;
    ArrayList<MapInfoSensor> mapInfoSensorArrayList;

    ArrayList<MapsensorAlarmMaster> MapsensorAlarmMasterArrayList;
    MapsensorAlarmAdapter MapsensorAlarmAdapters;
    int finalHeight, finalWidth;
    RecyclerView home_sensor_info;
    private RecyclerView.LayoutManager mLayoutManager;
    
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_map_sensor);
        setTitle("Sensor Alarm");
        rl = (RelativeLayout) findViewById(R.id.rl);
        imageView = findViewById(R.id.imageview);
        location = findViewById(R.id.location);
        home_sensor_info = findViewById(R.id.home_sensor_info);
        location.setText(CountingMapBuildActivity.building_name + " - " + CountingMapFloorActivity.floor_name);


//load Image
        Glide.with(CountingMapSensorActivity.this)
                .load(Url + "Images/Monitor/" + CountingMapFloorActivity.floor_image)
                .error(R.drawable.errorimage)
                .into(imageView);
        Log.d("image", Url + "Images/Monitor/" + CountingMapFloorActivity.floor_image);

        

        new readSensor().execute(Url + "CCTVMonitor/get_info_camera?" + "build=" + CountingMapBuildActivity.building_code + "&floor=" + CountingMapFloorActivity.floor_code);
        Log.d("readSensor", Url + "CCTVMonitor/get_info_camera?" + "build=" + CountingMapBuildActivity.building_code + "&floor=" + CountingMapFloorActivity.floor_code);

        

        new readSensor_map().execute(Url + "MotionAlarm/Getmqtt?id=" + CountingMapFloorActivity.floor_id);
        Log.d("readSensor_map", Url + "MotionAlarm/Getmqtt?id=" + CountingMapFloorActivity.floor_id);

    }




    private class readSensor_map extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            MapsensorAlarmMasterArrayList = new ArrayList<>();
            String SensorID, SensorName, lct_nm, time_up;

            try {

                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray.length() == 0) {
                    //AlerError.Baoloi("No data!", MapSensorAlarmActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);
                    SensorID = objectRow.getString("ss_no").replace("null", "");
                    SensorName = objectRow.getString("ss_nm").replace("null", "");
                    lct_nm = objectRow.getString("lct_nm").replace("null", "");
                    time_up = objectRow.getString("time_up").replace("null", "");
                    MapsensorAlarmMasterArrayList.add(new MapsensorAlarmMaster(SensorID, SensorName, lct_nm, time_up));
                }

                buildrecyc();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountingMapSensorActivity.this);
            }
        }

    }

    private void buildrecyc() {

        home_sensor_info.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(CountingMapSensorActivity.this);
        MapsensorAlarmAdapters = new MapsensorAlarmAdapter(MapsensorAlarmMasterArrayList);
        home_sensor_info.setLayoutManager(mLayoutManager);
        home_sensor_info.setAdapter(MapsensorAlarmAdapters);

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
                    AlerError.Baoloi("No data!", CountingMapSensorActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ssid = objectRow.getString("id").replace("null", "");
                    ss_no = objectRow.getString("camera_no").replace("null", "");
                    ss_nm = objectRow.getString("camera_nm").replace("null", "");
                    top_position = objectRow.getString("top_position").replace("null", "");
                    left_posistion = objectRow.getString("left_posistion").replace("null", "");
                    type = "";

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
                AlerError.Baoloi("Could not connect to server", CountingMapSensorActivity.this);
            }
        }

    }

    private void SetSensor() {

        if (mapMaterArrayList == null) {
            return;
        }

        final ImageView[] tv = new ImageView[mapMaterArrayList.size() + 1];

        for (int i = 0; i < mapMaterArrayList.size(); i++) {
            tv[i + 1] = new ImageView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
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


            params.leftMargin = left;
            params.topMargin = top;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_view));
            }
            tv[i + 1].setLayoutParams(params);

            int finalI = i;
            tv[i + 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    Intent intent = new Intent(CountingMapSensorActivity.this, CCTVActivity.class);
                    Bundle bundle = new Bundle();
                    bundle.putString("nm_camera", mapMaterArrayList.get(finalI).getSs_no());
                    intent.putExtras(bundle);
                    startActivity(intent);

                }
            });
            rl.addView(tv[i + 1]);
        }

    } 
}
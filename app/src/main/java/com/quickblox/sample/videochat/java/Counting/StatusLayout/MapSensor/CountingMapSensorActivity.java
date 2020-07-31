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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapSensor.MapsensorAlarmAdapter;
import com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapSensor.MapsensorAlarmMaster;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.CCTV.CCTVList.CCTVActivity;
import com.quickblox.sample.videochat.java.Counting.StatusLayout.CountingMapBuildActivity;
import com.quickblox.sample.videochat.java.Counting.StatusLayout.CountingMapFloorActivity;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapActivity;
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

    ArrayList<CountingMapSensorMaster> countingMapSensorMasterArrayList;
    CountingMapSensorAdapter countingMapSensorAdapter;
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

        

        new readSensor().execute(Url + "Counting/get_info_line?" + "build=" + CountingMapBuildActivity.building_code + "&floor=" + CountingMapFloorActivity.floor_code);
        Log.d("readSensor", Url + "Counting/get_info_line?" + "build=" + CountingMapBuildActivity.building_code + "&floor=" + CountingMapFloorActivity.floor_code);


        new readSensor_map().execute(Url + "Counting/GetLineInfo?id=" + CountingMapFloorActivity.floor_id);
        Log.d("readSensor_map", Url + "Counting/GetLineInfo?id=" + CountingMapFloorActivity.floor_id);

    }




    private class readSensor_map extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            countingMapSensorMasterArrayList = new ArrayList<>();
            String id,line_no,line_nm,line_manager,Alarm_Range1,Alarm_Range2,building_nm,floor_nm
                    ,remark,reg_id,reg_dt,chg_id,chg_dt,target_qty,actual_qty,defect_qty;

            try {

                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray.length() == 0) {
                    //AlerError.Baoloi("No data!", MapSensorAlarmActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);
                    id = objectRow.getString("id").replace("null", "");
                    line_no = objectRow.getString("line_no").replace("null", "");
                    line_nm = objectRow.getString("line_nm").replace("null", "");
                    line_manager = objectRow.getString("line_manager").replace("null", "");
                    Alarm_Range1 = objectRow.getString("Alarm_Range1").replace("null", "");
                    Alarm_Range2 = objectRow.getString("Alarm_Range2").replace("null", "");
                    building_nm = objectRow.getString("building_nm").replace("null", "");
                    floor_nm = objectRow.getString("floor_nm").replace("null", "");
                    remark = objectRow.getString("remark").replace("null", "");
                    reg_id = objectRow.getString("reg_id").replace("null", "");
                    reg_dt = objectRow.getString("reg_dt").replace("null", "");
                    chg_id = objectRow.getString("chg_id").replace("null", "");
                    chg_dt = objectRow.getString("chg_dt").replace("null", "");
                    target_qty = objectRow.getString("target_qty").replace("null", "0");
                    actual_qty = objectRow.getString("actual_qty").replace("null", "0");
                    defect_qty = objectRow.getString("defect_qty").replace("null", "0");
                    countingMapSensorMasterArrayList.add(new CountingMapSensorMaster(id,line_no,line_nm,line_manager,Alarm_Range1,Alarm_Range2,building_nm,floor_nm
                            ,remark,reg_id,reg_dt,chg_id,chg_dt,target_qty,actual_qty,defect_qty));
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
        countingMapSensorAdapter = new CountingMapSensorAdapter(countingMapSensorMasterArrayList);
        home_sensor_info.setLayoutManager(mLayoutManager);
        home_sensor_info.setAdapter(countingMapSensorAdapter);

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


                    ssid = objectRow.getString("ssid").replace("null", "");
                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    top_position = objectRow.getString("top_position").replace("null", "");
                    left_posistion = objectRow.getString("left_posistion").replace("null", "");
                    type = objectRow.getString("type").replace("null", "");

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
                if (mapMaterArrayList.get(i).getType().equals("Line")) {
                    tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_view));
                }else if (mapMaterArrayList.get(i).getType().equals("CMR")) {
                    tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_cmr));
                }
            }
            tv[i + 1].setLayoutParams(params);

            int finalI = i;
            tv[i + 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //bientam = finalI;
                    if (mapMaterArrayList.get(finalI).getType().equals("Line")) {
//                        new readInfosensor().execute(Url + "/Monitor/ShowDataSS?ss_no=" + mapMaterArrayList.get(finalI).ss_no);
//                        Log.d("readInfosensor", Url + "/Monitor/ShowDataSS?ss_no=" + mapMaterArrayList.get(finalI).ss_no);
                    }else if (mapMaterArrayList.get(finalI).getType().equals("CMR")) {

                        Intent intent = new Intent(CountingMapSensorActivity.this, CCTVActivity.class);

                        Bundle bundle = new Bundle();
                        bundle.putString("nm_camera", mapMaterArrayList.get(finalI).getSs_no());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }


                }
            });
            rl.addView(tv[i + 1]);
        }

    }
}
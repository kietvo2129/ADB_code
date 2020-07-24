package com.quickblox.sample.videochat.java.AlarmData.StatusLayout;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapSensor.MapSensorAlarmActivity;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapFloor.MapFloorAdapter;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapFloor.MapFloorMatter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class MapFloorAlrmActivity extends AppCompatActivity {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    ArrayList<MapFloorMatter> mapFloorMatterArrayList;
    RecyclerView recyclerView;
    MapFloorAdapter mapFloorAdapter;
    public static String floor_code,floor_image,floor_name,floor_id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map_floor);
        setTitle("Map Alarm Floor");
        TextView building_name = findViewById(R.id.building_name);
        TextView city_code = findViewById(R.id.city_code);
        building_name.setText(MapBuildAlarmActivity.building_name);
        city_code.setText(MapBuildAlarmActivity.city_code);
        recyclerView = findViewById(R.id.song_recycler_view);

        new readMapfloor().execute(Url + "Monitor/get_info_sensor?"+ "build=" + MapBuildAlarmActivity.building_code);
        Log.d("readMapbuild", Url + "Monitor/get_info_sensor?"+ "build=" + MapBuildAlarmActivity.building_code);

    }


    private class readMapfloor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mapFloorMatterArrayList = new ArrayList<>();
            String no,floor_id,floor_name,floor_code,floor_image,building_code;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", MapFloorAlrmActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    floor_id = objectRow.getString("floor_id").replace("null", "");
                    floor_name = objectRow.getString("floor_name").replace("null", "");
                    floor_code = objectRow.getString("floor_code").replace("null", "");
                    floor_image = objectRow.getString("floor_image").replace("null", "");
                    building_code = objectRow.getString("building_code").replace("null", "");


                    mapFloorMatterArrayList.add(new MapFloorMatter(i+1+"",floor_id,floor_name,floor_code,floor_image,building_code));
                    //mHomeListnew = mHomeList;
                }

                buildRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", MapFloorAlrmActivity.this);
            }
        }

    }
    private void buildRecyclerView() {

        recyclerView.setHasFixedSize(true);
        mapFloorAdapter = new MapFloorAdapter(mapFloorMatterArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 1));
        recyclerView.setAdapter(mapFloorAdapter);

        mapFloorAdapter.setOnItemClickListener(new MapFloorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                floor_id= mapFloorMatterArrayList.get(position).getFloor_id();
                floor_code = mapFloorMatterArrayList.get(position).getFloor_code();
                floor_image = mapFloorMatterArrayList.get(position).getFloor_image();
                floor_name = mapFloorMatterArrayList.get(position).getFloor_name();
                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(MapFloorAlrmActivity.this, MapSensorAlarmActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(MapFloorAlrmActivity.this,
                            Pair.create(view, "imageTransition"));
                    startActivity(intent, options.toBundle());
                }
            }
        });
    }
    //@Override
    //protected void onStop() {
        //super.onStop();
       // startService(new Intent(this, NotificationService.class));
    //}
}
package com.quickblox.sample.videochat.java.CCTV.CCTVLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;

import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapBuild.MapBuildAdapter;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapBuild.MapBuildMatter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CCTVBuildActivity extends AppCompatActivity {
    private static final int GRID_SPAN_COUNT = 2;
    ArrayList<MapBuildMatter> mapBuildAdapterList;
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    RecyclerView recyclerView;
    public static String building_code, building_name, city_code;
    private MapBuildAdapter mapBuildAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_cctv);
        setTitle("Building CCTV");
        recyclerView = findViewById(R.id.recyc);
        new readMapbuild().execute(Url + "CCTVMonitor/get_info_camera");
        Log.d("readMapbuild", Url + "CCTVMonitor/get_info_camera");
    }
    private class readMapbuild extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mapBuildAdapterList = new ArrayList<>();
            String building_id, building_name, building_code, city_code;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", CCTVBuildActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    building_id = objectRow.getString("building_id").replace("null", "");
                    building_name = objectRow.getString("building_name").replace("null", "");
                    building_code = objectRow.getString("building_code").replace("null", "0");
                    city_code = objectRow.getString("city_code").replace("null", "0");


                    mapBuildAdapterList.add(new MapBuildMatter(building_id, building_name, building_code, city_code));
                    //mHomeListnew = mHomeList;
                }

                buildRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CCTVBuildActivity.this);
            }
        }

    }

    private void buildRecyclerView() {

        recyclerView.setHasFixedSize(true);
        mapBuildAdapter = new MapBuildAdapter(mapBuildAdapterList);
        recyclerView.setLayoutManager(new GridLayoutManager(CCTVBuildActivity.this, GRID_SPAN_COUNT));
        recyclerView.setAdapter(mapBuildAdapter);
        mapBuildAdapter.setOnItemClickListener(new MapBuildAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                building_code = mapBuildAdapterList.get(position).getBuilding_code();
                building_name = mapBuildAdapterList.get(position).getBuilding_name();
                city_code = mapBuildAdapterList.get(position).getCity_code();

                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                    Intent intent = new Intent(CCTVBuildActivity.this, CCTVFloorActivity.class);
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(CCTVBuildActivity.this,
                            Pair.create(view, "imageTransition"));
                    startActivity(intent, options.toBundle());
                }

            }
        });
    }
}
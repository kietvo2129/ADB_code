package com.quickblox.sample.videochat.java.DigitalData.SensorList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SensorCheckActivity extends AppCompatActivity implements Animation.AnimationListener,Toolbar.OnMenuItemClickListener {
    Animation animSlideup, animSlidedown, animSlideupinfo;
    RelativeLayout re_search, ngancach, thuhep, thuhep2;
    ImageView len, xuong;
    boolean visible;
    String animStatus = "xuong";


    LinearLayout sensor_ifo;
    TextView mintemp, maxtemp, minhum, maxhum, minpress, maxpress, minpow, maxpow;
    Button button;
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<SensorMatter> sensorMatterArrayList;
    private ArrayList<SensorMatter> sensorMatterArrayListnew;
    RecyclerView mRecyclerView;

    SensorAdapter sensorAdapter;
    public static String idSensor;

    public static int mSelectedItem = -1;
    SearchView searchView;

    String oftion = "Code";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sensor_check);
        thuhep = findViewById(R.id.thuhep);
        thuhep2 = findViewById(R.id.thuhep2);
        len = findViewById(R.id.len);
        xuong = findViewById(R.id.xuong);
        ngancach = findViewById(R.id.ngancach);
        re_search = findViewById(R.id.re_search);
        sensor_ifo = findViewById(R.id.sensor_ifo);
        re_search.setVisibility(View.GONE);
        len.setVisibility(View.GONE);
        xuong.setVisibility(View.VISIBLE);

        mintemp = findViewById(R.id.mintemp);
        maxtemp = findViewById(R.id.maxtemp);
        minhum = findViewById(R.id.minhum);
        maxhum = findViewById(R.id.maxhum);

        minpress = findViewById(R.id.minpress);
        maxpress = findViewById(R.id.maxpress);
        minpow = findViewById(R.id.minpow);
        maxpow = findViewById(R.id.maxpow);
        mRecyclerView = findViewById(R.id.recyclerView);


        setTitle("Sensor Check");
        new readsensorcheck().execute(Url + "Monitor/GetMonitor");
        Log.d("readsensorcheck", Url + "Monitor/GetMonitor");


        animSlideup = AnimationUtils.loadAnimation(SensorCheckActivity.this,
                R.anim.slide_up);
        animSlideup.setAnimationListener(this);
        animSlidedown = AnimationUtils.loadAnimation(SensorCheckActivity.this,
                R.anim.slide_down);
        animSlidedown.setAnimationListener(this);

        animSlideupinfo = AnimationUtils.loadAnimation(SensorCheckActivity.this,
                R.anim.slide_up_info);
        animSlideupinfo.setAnimationListener(this);


        ngancach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (len.getVisibility() == View.VISIBLE) {
                    thuhep2.startAnimation(animSlideup);
                    animStatus = "len";
                    len.setVisibility(View.GONE);
                    xuong.setVisibility(View.VISIBLE);
                    sensor_ifo.setVisibility(View.GONE);
                } else {
                    len.setVisibility(View.VISIBLE);
                    re_search.setVisibility(View.VISIBLE);
                    animStatus = "xuong";
                    thuhep2.startAnimation(animSlidedown);
                    xuong.setVisibility(View.GONE);
                    sensor_ifo.setVisibility(View.GONE);
                }
            }
        });
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_func);
        toolbar.setOnMenuItemClickListener(this);
        searchView = findViewById(R.id.searchView);

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(s,oftion);
                searchView.clearFocus();
                sensor_ifo.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s,oftion);
                sensor_ifo.setVisibility(View.GONE);
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                mSelectedItem = -1;
//                Intent intent = new Intent(SensorCheckActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });
    }

    private void xuly_sensor(int i) {

        sensor_ifo.setVisibility(View.VISIBLE);
        sensor_ifo.startAnimation(animSlideupinfo);

        //  Toast.makeText(this, ""+ sensorMatterArrayList.get(i).getMin_humi(), Toast.LENGTH_SHORT).show();

        mintemp.setText("Min: " + sensorMatterArrayList.get(i).getMin_temp() + "°C");
        maxtemp.setText("Max: " + sensorMatterArrayList.get(i).getMax_temp() + "°C");
        minhum.setText("Min: " + sensorMatterArrayList.get(i).getMin_humi() + "%");
        maxhum.setText("Max: " + sensorMatterArrayList.get(i).getMax_humi() + "%");

        minpress.setText("Min: " + sensorMatterArrayList.get(i).getMin_press() + "Pa");
        maxpress.setText("Max: " + sensorMatterArrayList.get(i).getMax_press() + "Pa");
        minpow.setText("Min: " + sensorMatterArrayList.get(i).getMin_pow() + "W");
        maxpow.setText("Max: " + sensorMatterArrayList.get(i).getMax_pow() + "W");

    }

    private class readsensorcheck extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            sensorMatterArrayList = new ArrayList<>();
            sensorMatterArrayListnew = new ArrayList<>();
            String ss_no, ss_nm, current_temp, current_press, current_pow, current_humi,
                    temp_issue, humi_issue, press_issue, pow_issue, time_update,
                    temp_issue_nm, humi_issue_nm, press_issue_nm, pow_issue_nm,
                    min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow,building_name,floor_name;

            try {
                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", SensorCheckActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    current_temp = objectRow.getString("current_temp").replace("null", "0").replace("-999", "");
                    current_press = objectRow.getString("current_press").replace("null", "0").replace("-999", "");
                    current_pow = objectRow.getString("current_pow").replace("null", "0").replace("-999", "");
                    current_humi = objectRow.getString("current_humi").replace("null", "0").replace("-999", "");
                    temp_issue = objectRow.getString("temp_issue").replace("null", "");
                    humi_issue = objectRow.getString("humi_issue").replace("null", "");
                    press_issue = objectRow.getString("press_issue").replace("null", "");
                    pow_issue = objectRow.getString("pow_issue").replace("null", "");
                    time_update = objectRow.getString("time_update").replace("null", "");
                    temp_issue_nm = objectRow.getString("temp_issue_nm").replace("null", "");
                    humi_issue_nm = objectRow.getString("humi_issue_nm").replace("null", "");
                    press_issue_nm = objectRow.getString("press_issue_nm").replace("null", "");
                    pow_issue_nm = objectRow.getString("pow_issue_nm").replace("null", "");
                    min_temp = objectRow.getString("min_temp").replace("null", "0").replace("-999", "");
                    max_temp = objectRow.getString("max_temp").replace("null", "0").replace("-999", "");
                    min_press = objectRow.getString("min_press").replace("null", "0").replace("-999", "");
                    max_press = objectRow.getString("max_press").replace("null", "0").replace("-999", "");
                    min_humi = objectRow.getString("min_humi").replace("null", "0").replace("-999", "");
                    max_humi = objectRow.getString("max_humi").replace("null", "0").replace("-999", "");
                    min_pow = objectRow.getString("min_pow").replace("null", "0").replace("-999", "");
                    max_pow = objectRow.getString("max_pow").replace("null", "0").replace("-999", "");
                    building_name = objectRow.getString("building_name").replace("null", "")
                                .replace("[\"", "").replace("\"]", "");
                    floor_name = objectRow.getString("floor_name").replace("null", "")
                                .replace("[\"", "").replace("\"]", "");

                    sensorMatterArrayList.add(new SensorMatter(ss_no, ss_nm, current_temp, current_press, current_pow, current_humi,
                            temp_issue, humi_issue, press_issue, pow_issue, time_update,
                            temp_issue_nm, humi_issue_nm, press_issue_nm, pow_issue_nm,
                            min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow,building_name,floor_name));
                    //mHomeListnew = mHomeList;

                }

                sensorMatterArrayListnew = sensorMatterArrayList;
                buildRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", SensorCheckActivity.this);
            }
        }

    }

    private void buildRecyclerView() {


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(SensorCheckActivity.this);
        //mLayoutManager.setAutoMeasureEnabled(false);
        sensorAdapter = new SensorAdapter(sensorMatterArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //  Context context = mRecyclerView.getContext();
        mRecyclerView.setAdapter(sensorAdapter);


        sensorAdapter.setOnItemClickListener(new SensorAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Button bt) {
                button = bt;
                if (button.getText().equals("ON")){
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + sensorMatterArrayList.get(position).getSs_no() + "&sts=002");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + sensorMatterArrayList.get(position).getSs_no() + "&sts=002");
                }else {
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + sensorMatterArrayList.get(position).getSs_no() + "&sts=001");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + sensorMatterArrayList.get(position).getSs_no() + "&sts=001");
                }
            }
        });
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
                    if (button.getText().equals("ON")){
                        button.setBackgroundResource(R.drawable.toggle_state_on);
                        button.setText("ON");
                    }else {
                        button.setBackgroundResource(R.drawable.toggle_state_off);
                        button.setText("OFF");
                    }
                }else {
                    if (button.getText().equals("ON")){
                        button.setBackgroundResource(R.drawable.toggle_state_off);
                        button.setText("OFF");
                    }else {
                        button.setBackgroundResource(R.drawable.toggle_state_on);
                        button.setText("ON");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                if (button.getText().equals("ON")){
                    button.setBackgroundResource(R.drawable.toggle_state_on);
                    button.setText("ON");
                }else {
                    button.setBackgroundResource(R.drawable.toggle_state_off);
                    button.setText("OFF");
                }
            }
        }

    }

    @Override
    public void onAnimationStart(Animation animation) {

    }

    @Override
    public void onAnimationEnd(Animation animation) {
        if (animStatus.equals("len")) {
            re_search.setVisibility(View.GONE);
        }
    }

    @Override
    public void onAnimationRepeat(Animation animation) {
    }

    @Override
    public void onBackPressed() {
        mSelectedItem = -1;
        super.onBackPressed();
    }


    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

            case R.id.SearchCode:
                searchView.setQueryHint("Search Code");
                oftion = "Code";
                sensor_ifo.setVisibility(View.GONE);
                searchView.setQuery("",true);
                return true;
            case R.id.SearchName:
                searchView.setQueryHint("Search Name");
                oftion = "Name";
                sensor_ifo.setVisibility(View.GONE);
                searchView.setQuery("",true);
                return true;
            case R.id.Searchbuild:
                searchView.setQueryHint("Search Building");
                oftion = "Building";
                sensor_ifo.setVisibility(View.GONE);
                searchView.setQuery("",true);
                return true;
            case R.id.Searchfloor:
                searchView.setQueryHint("Search Floor");
                oftion = "Floor";
                sensor_ifo.setVisibility(View.GONE);
                searchView.setQuery("",true);
                return true;
        }
        return false;
    }

    private void filter(String text, String oftion) {
        ArrayList<SensorMatter> filteredList = new ArrayList<>();
        for (SensorMatter item : sensorMatterArrayListnew) {
            if (oftion.equals("Code")) {
                if (item.getSs_no().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Name")){
                if (item.getSs_nm().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Floor")){
                if (item.getFloor_name().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Building")){
                if (item.getBuilding_name().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        sensorMatterArrayList = filteredList;
        sensorAdapter.filterList(filteredList);
    }
    //@Override
    //protected void onStop() {
        //super.onStop();
       // startService(new Intent(this, NotificationService.class));
    //}
}

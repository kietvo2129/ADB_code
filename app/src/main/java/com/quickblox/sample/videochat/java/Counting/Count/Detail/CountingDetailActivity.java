package com.quickblox.sample.videochat.java.Counting.Count.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.Counting.Count.CountActivity;
import com.quickblox.sample.videochat.java.R;
import com.quickblox.sample.videochat.java.fragment.Homeinfosensor.HomesensorinforAdapter;
import com.quickblox.sample.videochat.java.fragment.Homeinfosensor.HomesensorinforMaster;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountingDetailActivity extends AppCompatActivity{
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    String id_line = CountActivity.id_line;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    CountingDetailAdapter countingDetailAdapter;
    List<DetailMaster> detailMastersList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Counting Detail");
        setContentView(R.layout.activity_counting_detail);
        mRecyclerView = findViewById(R.id.home_sensor_info);
        popupdialog();
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void popupdialog() {
        new loadpopupdialog().execute(Url + "plan/getDataLineTimeToday?id=" + id_line);
        Log.d("loadpopupdialog", Url + "plan/getDataLineTimeToday?id=" + id_line);
    }


    private class loadpopupdialog extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            detailMastersList = new ArrayList<>();
            String id, work_time, start_time, actual_qty, defect_qty, target_qty;
            try {

                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", CountingDetailActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id = jsonObject.getString("id");
                    work_time = jsonObject.getString("work_time");
                    start_time = jsonObject.getString("start_time");
                    actual_qty = jsonObject.getString("actual_qty");
                    defect_qty = jsonObject.getString("defect_qty");
                    target_qty = jsonObject.getString("target_qty");
                    detailMastersList.add(new DetailMaster(id, work_time, start_time, actual_qty, defect_qty, target_qty));
                }


                buildRecyc();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountingDetailActivity.this);
            }
        }

    }

    private void buildRecyc() {

        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(CountingDetailActivity.this);
        //mLayoutManager.setAutoMeasureEnabled(false);
        countingDetailAdapter = new CountingDetailAdapter(detailMastersList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        //  Context context = mRecyclerView.getContext();
        mRecyclerView.setAdapter(countingDetailAdapter);
    }
}
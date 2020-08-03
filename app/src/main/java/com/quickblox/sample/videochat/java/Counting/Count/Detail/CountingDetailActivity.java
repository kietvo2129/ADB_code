package com.quickblox.sample.videochat.java.Counting.Count.Detail;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
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


    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    CountingDetailAdapter countingDetailAdapter;
    List<DetailMaster> detailMastersList;
    String id_line = "";
    BarChart barChart;
    ArrayList<BarEntry> bargroupActual;
    ArrayList<BarEntry> bargroupDefective;
    ArrayList<String> labels;
    ImageView imaview;
    int checkima =1;
    RelativeLayout rl_table;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Counting Detail");
        setContentView(R.layout.activity_counting_detail);
        mRecyclerView = findViewById(R.id.home_sensor_info);
        Bundle bundle = getIntent().getExtras();
        id_line = bundle.getString("id_line");
        barChart = (BarChart) findViewById (R.id.barchart);
        popupdialog();
        imaview = findViewById(R.id.imaview);
        imaview.setBackgroundResource(R.drawable.ic_view_barchar);
        rl_table = findViewById(R.id.rl_table);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        imaview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (checkima==0){
                    checkima = 1;
                    imaview.setBackgroundResource(R.drawable.ic_view_barchar);
                    barChart.setVisibility(View.GONE);
                    rl_table.setVisibility(View.VISIBLE);

                }else {
                    checkima = 0;
                    imaview.setBackgroundResource(R.drawable.ic_view_table);
                    barChart.setVisibility(View.VISIBLE);
                    rl_table.setVisibility(View.GONE);
                }
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
            bargroupActual = new ArrayList<>();
            bargroupDefective = new ArrayList<>();
            labels = new ArrayList<String>();
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
                    actual_qty = jsonObject.getString("actual_qty").replace("null","0");
                    defect_qty = jsonObject.getString("defect_qty").replace("null","0");
                    target_qty = jsonObject.getString("target_qty").replace("null","0");
                    detailMastersList.add(new DetailMaster(id, work_time, start_time, actual_qty, defect_qty, target_qty));
                    bargroupActual.add(new BarEntry(Integer.parseInt(actual_qty),i));
                    bargroupDefective.add(new BarEntry(Integer.parseInt(defect_qty),i));
                    labels.add(start_time.substring(0,2)+":"+start_time.substring(2,4));
                }

                buildRecyc();
                setChartBar();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountingDetailActivity.this);
            }
        }

    }

    private void setChartBar() {
        ArrayList<BarDataSet> dataSets = new ArrayList<>();
        BarDataSet barDataSetActual = new BarDataSet(bargroupActual, "Actual");
        barDataSetActual.setColor(Color.parseColor("#92D050"));
        BarDataSet barDataSetDefective = new BarDataSet(bargroupDefective, "Defective");
        barDataSetDefective.setColor(Color.parseColor("#FF0000"));
        barChart.setDescription("");
        dataSets.add(barDataSetActual);
        dataSets.add(barDataSetDefective);
        BarData data = new BarData(labels, dataSets);
        barChart.setData(data);
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
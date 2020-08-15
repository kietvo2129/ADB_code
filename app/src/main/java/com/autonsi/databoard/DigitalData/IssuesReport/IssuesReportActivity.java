package com.autonsi.databoard.DigitalData.IssuesReport;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.autonsi.databoard.AlerError.AlerError;

import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class IssuesReportActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {

    RecyclerView recyclerView;
    private IssualReportAdapter issualReportadapter;
    private ArrayList<IssuesReport> issualReport;
    private String issue, info_warning, time_sending, info_finish, time_finish;
    private int ppoossii;
    SearchView searchView;
    private String oftion = "Code";
    private ArrayList<IssuesReport> issualReportFull;
    String Url = com.autonsi.databoard.Url.webUrl;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues_report);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_func); // Menu Search
        toolbar.setOnMenuItemClickListener(this);
        searchView = findViewById(R.id.searchView);
        TextView title_tv = findViewById(R.id.title_tv);
        title_tv.setText("Issues Report");


        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(s, oftion );
                searchView.clearFocus();
//                sensor_ifo.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s,oftion);
//                sensor_ifo.setVisibility(View.GONE);
                return true;
            }
        });

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSelectedItem = -1;
//                Intent intent = new Intent(SensorCheckActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });


        recyclerView = findViewById(R.id.recycler_view_issues_report);
        //issualReport = new ArrayList<>();
        //setupRecyclerView();

        String url = Url + "Monitor/GetIssuesHisApp";
        Log.d("GetReport", url);
        new getIssuesReport().execute(url);
    }

    private void setupRecyclerView() {
        issualReportadapter = new IssualReportAdapter(issualReport);
        recyclerView.setLayoutManager(new LinearLayoutManager(IssuesReportActivity.this));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(issualReportadapter);

        issualReportadapter.setOnItemClickListener(new IssualReportAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                for (int i = 0; i < issualReport.size(); i++) {
                    issualReport.get(i).setIdColor(false);
                }

                issualReport.get(position).setIdColor(true);
                issualReportadapter.notifyDataSetChanged();

                ppoossii = position;
                //Toast.makeText(IssuesReportActivity.this, "Posi tion " + position, Toast.LENGTH_SHORT).show();
                String url = Url + "Monitor/getinfoissue?id=" + issualReport.get(position).getId();
                new getdataActionDetailPopup().execute(url);
            }
        });
    }

    private class getIssuesReport extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            issualReport = new ArrayList<>();
            issualReportFull = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", IssuesReportActivity.this);

                } else {
                    Log.d("cout json", String.valueOf(jsonArray.length()));

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);

                        // String sts_send = jsonObject.getJSONArray("sts_send").getString(0);
                        String sts_send = jsonObject.getString("sts_send1").replace("[", "").replace("]", "").replace("[", "\"");
                        if (sts_send.equals("003")) {
                            String id = jsonObject.getString("id");
                            String ss_no = jsonObject.getString("ss_no");
                            String ss_nm = jsonObject.getString("ss_nm");
                            String lct_nm = jsonObject.getString("lct_nm");
                            String time_update = jsonObject.getString("time_update");

                            String img_issue = jsonObject.getString("img_issue");
                            String floor_name = jsonObject.getString("floor_name");
                            String building_name = jsonObject.getString("building_name");

                            issualReport.add(new IssuesReport(ss_no, ss_nm, building_name + " - "+ floor_name, time_update, id, false,floor_name,building_name,img_issue));
                        } else {
                        }
                    }
                    Log.d("cout lensh", String.valueOf(issualReport.size()));
                    issualReportFull = issualReport;
                    setupRecyclerView();
                    // issualReportadapter.notifyDataSetChanged();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesReportActivity.this);
            }
        }

    }

    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.SearchCode:
                searchView.setQueryHint("Search Code");
                oftion = "Code";
                searchView.setQuery("",true);
                return true;
            case R.id.SearchName:
                searchView.setQueryHint("Search Name");
                oftion = "Name";
                searchView.setQuery("",true);
                return true;
            case R.id.Searchbuild:
                searchView.setQueryHint("Search Building");
                oftion = "Building";
                searchView.setQuery("",true);
                return true;
            case R.id.Searchfloor:
                searchView.setQueryHint("Search Floor");
                oftion = "Floor";
                searchView.setQuery("",true);
                return true;
        }
        return false;
    }

    private void viewPoupop(int position) {

        Dialog dialog = new Dialog(this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        View dialogView = LayoutInflater.from(this).inflate(R.layout.popup_warning_detail, null);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });

        TextView tvId, tvLocation, tvNm, tvIssue, tvOrder, tvTime, tvAction, tvTimeend;
        RelativeLayout rl;
        tvId = dialog.findViewById(R.id.tvId);
        tvLocation = dialog.findViewById(R.id.tvLocation);
        tvNm = dialog.findViewById(R.id.tvNm);
        tvIssue = dialog.findViewById(R.id.tvIssue);
        tvOrder = dialog.findViewById(R.id.tvOrder);
        tvTime = dialog.findViewById(R.id.tvTime);
        tvAction = dialog.findViewById(R.id.tvAction);
        tvTimeend = dialog.findViewById(R.id.tvTimeend);
        rl = dialog.findViewById(R.id.rl);
        ImageView imaAction;
//        if (info_finish=="" && time_finish==""){
//            rl.setVisibility(View.GONE);
//        }else {
//            rl.setVisibility(View.VISIBLE);
//        }
        imaAction = dialog.findViewById(R.id.imaAction);
        tvIssue.setText(issue);
        tvOrder.setText(info_warning);
        tvTime.setText(time_sending);
        tvAction.setText(info_finish);
        tvTimeend.setText(time_finish);
        Glide.with(IssuesReportActivity.this)
                .load(Url + "Images/Monitor/IssuesHistory/" + issualReport.get(position).getImg_issue() )
                .error(R.drawable.errorimage)
                .into(imaAction);

        tvId.setText(issualReport.get(position).getSensorID());
        tvNm.setText(issualReport.get(position).getSensorName());
        tvLocation.setText(issualReport.get(position).getLocation());
        dialog.show();
    }


    private class getdataActionDetailPopup extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", IssuesReportActivity.this);

                } else {
                    Log.d("cout json", String.valueOf(jsonArray.length()));

                    JSONObject jsonObject = jsonArray.getJSONObject(0);
                    issue = jsonObject.getString("issue");
                    time_sending = jsonObject.getString("time_sending");
                    info_warning = jsonObject.getString("info_warning");
                    info_finish = jsonObject.getString("info_finish");
                    time_finish = jsonObject.getString("time_finish");

                    viewPoupop(ppoossii);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesReportActivity.this);
            }
        }

    }

    private void filter(String text, String oftion) {
        ArrayList<IssuesReport> filteredList = new ArrayList<>();
        for (IssuesReport item : issualReportFull) {
            if (oftion.equals("Code")) {
                if (item.getSensorID().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Name")){
                if (item.getSensorName().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Floor")){
                if (item.getFloor().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Building")){
                if (item.getBuilding().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        issualReport = filteredList;
        issualReportadapter.filterList(filteredList);
    }

}
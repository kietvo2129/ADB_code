package com.quickblox.sample.videochat.java.CCTV.CCTVList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;

import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CCTVListActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener{
    SearchView searchView;
    String oftion = "Code";
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    private ArrayList<CCTVMaster> cctvMasterArrayList;
    private ArrayList<CCTVMaster> cctvMasterArrayListnew;
    CCTVAdapter cctvAdapter;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    public static String nm_camera;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_list);
        mRecyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_func);
        toolbar.setOnMenuItemClickListener(this);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(s,oftion);
                searchView.clearFocus();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s,oftion);
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                //mSelectedItem = -1;
                finish();
            }
        });
        setTitle("CCTV List");
        new readlistsensor().execute(Url + "CCTVMonitor/GetCamList");
        Log.d("readlistsensor", Url + "CCTVMonitor/GetCamList");
    }
    private void filter(String text, String oftion) {
        ArrayList<CCTVMaster> filteredList = new ArrayList<>();
        for (CCTVMaster item : cctvMasterArrayListnew) {
            if (oftion.equals("Code")) {
                if (item.getCamera_no().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Name")){
                if (item.getCamera_nm().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Floor")){
                if (item.getFloor_code().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Building")){
                if (item.getbuilding_code().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        cctvMasterArrayList = filteredList;
        cctvAdapter.filterList(filteredList);
    }
    //@Override
    //protected void onStop() {
        //super.onStop();
       // startService(new Intent(this, NotificationService.class));
    //}
    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()) {

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
    private class readlistsensor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            cctvMasterArrayList = new ArrayList<>();
            cctvMasterArrayListnew = new ArrayList<>();

            String camera_no,camera_nm,building_code,floor_code;

            try {
                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", CCTVListActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    camera_no = objectRow.getString("camera_no").replace("null", "");
                    camera_nm = objectRow.getString("camera_nm").replace("null", "");
                    building_code = objectRow.getString("building_code").replace("null", "");
                    floor_code = objectRow.getString("floor_code").replace("null", "");


                    cctvMasterArrayList.add(new CCTVMaster(camera_no,camera_nm,building_code,floor_code));

                }

                cctvMasterArrayListnew = cctvMasterArrayList;
                buildRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CCTVListActivity.this);
            }
        }

    }
    private void buildRecyclerView() {


        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(CCTVListActivity.this);
        //mLayoutManager.setAutoMeasureEnabled(false);
        cctvAdapter = new CCTVAdapter(cctvMasterArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(cctvAdapter);

        cctvAdapter.setOnItemClickListener(new CCTVAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Intent intent = new Intent(CCTVListActivity.this, CCTVActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("nm_camera", cctvMasterArrayList.get(position).getCamera_no());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });


    }
}
package com.autonsi.databoard.Counting.CountList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.ProgressBar;
import android.widget.SearchView;

import com.github.ybq.android.spinkit.style.Circle;
import com.autonsi.databoard.Counting.Count.Detail.CountingDetailActivity;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.autonsi.databoard.AlerError.AlerError.Baoloi;

public class CountListActivity extends AppCompatActivity {
    RecyclerView recyclerViewCL;
    private ProgressBar progressBar;
    String Url = com.autonsi.databoard.Url.webUrl;
    private CountListAdaptor countListAdaptor;
    private ArrayList<CountListItem> countlistitems;
    private ArrayList<CountListItem> countlistitemsFull;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_list);

        setupdata();
    }

    private void setupdata() {
        setTitle("Count List");
        recyclerViewCL = findViewById(R.id.recyclerViewCL);
        progressBar = findViewById(R.id.Spinkit);

        // Start Animation
        progressBar.setVisibility(View.VISIBLE);
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

        // load  Data
        String  urr = Url + "Counting/PartialView_Databoard_api";
        new loadCountList().execute(urr);
        countlistitems = new ArrayList<>();
//        countlistitems.add(new CountListItem("1","lineid1","line name 1","10%","10","1","200","20"));
//        countlistitems.add(new CountListItem("2","lineid2","line name 2","20%","20","2","300","30"));
//        countlistitems.add(new CountListItem("3","lineid3","line name 3","30%","30","3","400","40"));
//        countlistitems.add(new CountListItem("4","lineid4","line name 4","40%","40","4","500","50"));
//        countlistitemsFull = countlistitems;
        buidRecycler();
    }

    private void filter(String key, String value) {
        ArrayList<CountListItem> filteredList = new ArrayList<>();
        for (CountListItem item : countlistitemsFull) {
            Log.d("for",item.toString());
            if (key.equals("Code")) {
                if (item.getLineName().toLowerCase().contains(value.toLowerCase())) {
                    filteredList.add(item);
                }
           }

//            else if (oftion.equals("Name")){
//                if (item.getSs_nm().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(item);
//                }
//            }else if (oftion.equals("Floor")){
//                if (item.getFloor_name().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(item);
//                }
//            }else if (oftion.equals("Building")){
//                if (item.getBuilding_name().toLowerCase().contains(text.toLowerCase())) {
//                    filteredList.add(item);
//                }
//            }
        }

        countlistitems = filteredList;
        countListAdaptor.filterList(filteredList);
    }

    private class loadCountList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            progressBar.setVisibility(View.GONE);
            countlistitems = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");
                if(jsonArray.length() ==0){
                    Baoloi("No data", CountListActivity.this);
                } else {
                            for (int i=0;i<jsonArray.length();i++){
                                JSONObject object = jsonArray.getJSONObject(i);
                                String id = object.getString("id");
                                String line_no = object.getString("line_no");
                                String line_nm = object.getString("line_nm");
                                String defect_qty = object.getString("defect_qty");
                                String  target_qty = object.getString("target_qty");
                                String actual_qty = object.getString("actual_qty");
                                String efficiency = object.getString("efficiency");
                                String taget_hour = String.valueOf(object.optInt("taget_hour",0));
                                String Alarm_Range1 = object.getString("Alarm_Range1");
                                String Alarm_Range2 = object.getString("Alarm_Range2");
                                countlistitems.add(new CountListItem(id,line_no,line_nm,efficiency,actual_qty,defect_qty,target_qty,taget_hour,Alarm_Range1,Alarm_Range2));
                            }
                }
                countlistitemsFull = countlistitems;
                buidRecycler();

            } catch (JSONException e) {
                e.printStackTrace();
                Baoloi("The server error" + e.toString(), CountListActivity.this);
            }

        }

    }

    private void buidRecycler(){

        countListAdaptor = new CountListAdaptor(countlistitems, this);
        recyclerViewCL.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewCL.setHasFixedSize(true);
        recyclerViewCL.setAdapter(countListAdaptor);

        //recyclerViewCL.getLayoutManager().scrollToPosition(oldPosition);
        countListAdaptor.setOnItemClickListener(new CountListAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {

                Intent intent = new Intent(CountListActivity.this, CountingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_line", countlistitems.get(position).getId());
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_search, menu);
        MenuItem searchItem = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) searchItem.getActionView();
        searchView.setFocusable(true);
        searchView.setQueryHint("Search line name");
        searchView.setImeOptions(EditorInfo.IME_ACTION_DONE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }
            @Override
            public boolean onQueryTextChange(String newText) {
                filter("Code", newText);
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
    }


}
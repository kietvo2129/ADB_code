package com.quickblox.sample.videochat.java.Counting.CountList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.github.ybq.android.spinkit.style.Circle;
import com.quickblox.sample.videochat.java.DigitalData.IssuesList.IssuesActivity;
import com.quickblox.sample.videochat.java.DigitalData.IssuesList.IssuesAdapter;
import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;

public class CountListActivity extends AppCompatActivity {
    RecyclerView recyclerViewCL;
    private ProgressBar progressBar;
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    private CountListAdaptor countListAdaptor;
    private ArrayList<CountListItem> countlistitems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count_list);

        setupdata();
    }

    private void setupdata() {
        setTitle("Issues List");
        recyclerViewCL = findViewById(R.id.recyclerViewCL);
        progressBar = findViewById(R.id.Spinkit);

        // Start Animation
        progressBar.setVisibility(View.VISIBLE);
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);

        // load  Data
        String  urr = Url ;
       // new loadCountList().execute(urr);
        countlistitems = new ArrayList<>();
        countlistitems.add(new CountListItem("1","lineid1","line name 1","10%","10","1","200","20"));
        countlistitems.add(new CountListItem("2","lineid2","line name 2","20%","20","2","300","30"));
        countlistitems.add(new CountListItem("3","lineid3","line name 3","30%","30","3","400","40"));
        countlistitems.add(new CountListItem("4","lineid4","line name 4","40%","40","4","500","50"));

        buidRecycler();
    }

    private class loadCountList extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s){
            super.onPostExecute(s);
            countlistitems = new ArrayList<>();

        }

    }

    private void buidRecycler(){

        countListAdaptor = new CountListAdaptor(countlistitems);
        recyclerViewCL.setLayoutManager(new GridLayoutManager(this,2));
        recyclerViewCL.setHasFixedSize(true);
        recyclerViewCL.setAdapter(countListAdaptor);

        //recyclerViewCL.getLayoutManager().scrollToPosition(oldPosition);
        countListAdaptor.setOnItemClickListener(new CountListAdaptor.OnItemClickListener() {
            @Override
            public void onItemClick(int position) {
                Toast.makeText(CountListActivity.this, "tex:" + position, Toast.LENGTH_SHORT).show();
            }
        });

    }
}
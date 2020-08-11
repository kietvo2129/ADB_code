package com.quickblox.sample.videochat.java.AlarmData.IssuesList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.graphics.Matrix;
import android.graphics.PointF;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.Circle;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class AlarmIssuesList extends AppCompatActivity implements View.OnTouchListener,Toolbar.OnMenuItemClickListener {

    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    private ArrayList<DoorHistoryMaster> doorHistoryMasters;
    private ArrayList<DoorHistoryMaster> doorHistoryMastersnew;
    private RecyclerView mRecyclerView;
    private DoorHistoryAdapter historyAdapter;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;
    private RelativeLayout rl;
    ImageView imagezoom,thoat;
    String oftion = "Code";

    private static final String TAG = "Touch";
    @SuppressWarnings("unused")
    private static final float MIN_ZOOM = 1f,MAX_ZOOM = 1f;

    // These matrices will be used to scale points of the image
    Matrix matrix = new Matrix();
    Matrix savedMatrix = new Matrix();

    // The 3 states (events) which the user is trying to perform
    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    // these PointF objects are used to record the point(s) the user is touching
    PointF start = new PointF();
    PointF mid = new PointF();
    float oldDist = 1f;
    SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm_issues_list);

        mRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.Spinkit);
        progressBar.setVisibility(View.VISIBLE);
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        rl = findViewById(R.id.rl);
        imagezoom= findViewById(R.id.imagezoom);
        thoat = findViewById(R.id.thoat);

        Toolbar toolbar= (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_func);
        toolbar.setOnMenuItemClickListener(this);
        toolbar.setNavigationOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                finish();
            }
        });

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

        thoat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl.setVisibility(View.GONE);
            }
        });

        new ReadData().execute(Url + "MotionAlarm/GetSensorsHistory?sts=today&rows=1000&page=1&sidx=&sord=asc");
        Log.d("ReadData", Url + "MotionAlarm/GetSensorsHistory?sts=today&rows=1000&page=1&sidx=&sord=asc");
        imagezoom.setOnTouchListener(this);
    }
    private class ReadData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            doorHistoryMasters = new ArrayList<>();
            String id, ss_no, ss_nm, Time, building_code, floor_code, img,RfKey;

            try {
                JSONObject jsonObjectA = new JSONObject(s);

                JSONArray jsonArray = jsonObjectA.getJSONArray("rows");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", AlarmIssuesList.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);
                    id = objectRow.getString("id").replace("null", "");
                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    RfKey =  objectRow.getString("RfKey").replace("null", "");

                    Time = objectRow.getString("Time").replace("null", "");
                    building_code = objectRow.getString("building_code").replace("null", "");
                    floor_code = objectRow.getString("floor_code").replace("null", "");
                    img = objectRow.getString("img").replace("null", "");
                    doorHistoryMasters.add(new DoorHistoryMaster(id, ss_no, ss_nm, Time, building_code, floor_code, img,RfKey));
                    //mHomeListnew = mHomeList;

                }
                doorHistoryMastersnew =doorHistoryMasters;
                buildRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
                progressBar.setVisibility(View.GONE);
                AlerError.Baoloi("Could not connect to server", AlarmIssuesList.this);
            }

        }

        private void buildRecyclerView() {


            mRecyclerView.setHasFixedSize(true);
            mLayoutManager = new LinearLayoutManager(AlarmIssuesList.this);
            historyAdapter = new DoorHistoryAdapter(doorHistoryMasters);
            mRecyclerView.setLayoutManager(mLayoutManager);
            mRecyclerView.setAdapter(historyAdapter);

            historyAdapter.setOnItemClickListener(new DoorHistoryAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(int position) {
                    //Toast.makeText(getActivity(), ""+position, Toast.LENGTH_SHORT).show();
                    rl.setVisibility(View.VISIBLE);

                    Glide.with(AlarmIssuesList.this)
                            .load(Url + "Images/MQTT/"+ doorHistoryMasters.get(position).getImg())
                            .error(R.drawable.errorimage)
                            .into(imagezoom);
                }
            });

            progressBar.setVisibility(View.GONE);
        }
    }


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

    private void filter(String text, String oftion) {
        ArrayList<DoorHistoryMaster> filteredList = new ArrayList<>();
        for (DoorHistoryMaster item : doorHistoryMastersnew) {
            if (oftion.equals("Code")) {
                if (item.getSs_no().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Name")){
                if (item.getSs_nm().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Floor")){
                if (item.getFloor_code().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }else if (oftion.equals("Building")){
                if (item.getBuilding_code().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        doorHistoryMasters = filteredList;
        historyAdapter.filterList(filteredList);
    }


    @Override
    public boolean onTouch(View view, MotionEvent motionEvent)
    {
        ImageView vieww = (ImageView) view;
        vieww.setScaleType(ImageView.ScaleType.MATRIX);
        float scale;

        dumpEvent(motionEvent);
        // Handle touch events here...

        switch (motionEvent.getAction() & MotionEvent.ACTION_MASK)
        {
            case MotionEvent.ACTION_DOWN:   // first finger down only
                savedMatrix.set(matrix);
                start.set(motionEvent.getX(), motionEvent.getY());
                Log.d(TAG, "mode=DRAG"); // write to LogCat
                mode = DRAG;
                break;

            case MotionEvent.ACTION_UP: // first finger lifted

            case MotionEvent.ACTION_POINTER_UP: // second finger lifted

                mode = NONE;
                Log.d(TAG, "mode=NONE");
                break;

            case MotionEvent.ACTION_POINTER_DOWN: // first and second finger down

                oldDist = spacing(motionEvent);
                Log.d(TAG, "oldDist=" + oldDist);
                if (oldDist > 5f) {
                    savedMatrix.set(matrix);
                    midPoint(mid, motionEvent);
                    mode = ZOOM;
                    Log.d(TAG, "mode=ZOOM");
                }
                break;

            case MotionEvent.ACTION_MOVE:

                if (mode == DRAG)
                {
                    matrix.set(savedMatrix);
                    matrix.postTranslate(motionEvent.getX() - start.x, motionEvent.getY() - start.y); // create the transformation in the matrix  of points
                }
                else if (mode == ZOOM)
                {
                    // pinch zooming
                    float newDist = spacing(motionEvent);
                    Log.d(TAG, "newDist=" + newDist);
                    if (newDist > 5f)
                    {
                        matrix.set(savedMatrix);
                        scale = newDist / oldDist; // setting the scaling of the
                        // matrix...if scale > 1 means
                        // zoom in...if scale < 1 means
                        // zoom out
                        matrix.postScale(scale, scale, mid.x, mid.y);
                    }
                }
                break;
        }

        vieww.setImageMatrix(matrix); // display the transformation on screen

        return true; // indicate event was handled
    }

    /*
     * --------------------------------------------------------------------------
     * Method: spacing Parameters: MotionEvent Returns: float Description:
     * checks the spacing between the two fingers on touch
     * ----------------------------------------------------
     */

    private float spacing(MotionEvent event)
    {
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return (float) Math.sqrt(x * x + y * y);
    }

    /*
     * --------------------------------------------------------------------------
     * Method: midPoint Parameters: PointF object, MotionEvent Returns: void
     * Description: calculates the midpoint between the two fingers
     * ------------------------------------------------------------
     */

    private void midPoint(PointF point, MotionEvent event)
    {
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }

    /** Show an event in the LogCat view, for debugging */
    private void dumpEvent(MotionEvent event)
    {
        String names[] = { "DOWN", "UP", "MOVE", "CANCEL", "OUTSIDE","POINTER_DOWN", "POINTER_UP", "7?", "8?", "9?" };
        StringBuilder sb = new StringBuilder();
        int action = event.getAction();
        int actionCode = action & MotionEvent.ACTION_MASK;
        sb.append("event ACTION_").append(names[actionCode]);

        if (actionCode == MotionEvent.ACTION_POINTER_DOWN || actionCode == MotionEvent.ACTION_POINTER_UP)
        {
            sb.append("(pid ").append(action >> MotionEvent.ACTION_POINTER_ID_SHIFT);
            sb.append(")");
        }

        sb.append("[");
        for (int i = 0; i < event.getPointerCount(); i++)
        {
            sb.append("#").append(i);
            sb.append("(pid ").append(event.getPointerId(i));
            sb.append(")=").append((int) event.getX(i));
            sb.append(",").append((int) event.getY(i));
            if (i + 1 < event.getPointerCount())
                sb.append(";");
        }

        sb.append("]");
        Log.d("Touch Events ---------", sb.toString());
    }
}
package com.quickblox.sample.videochat.java.CCTV.CCTVLayout;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.CCTV.CCTVList.CCTVActivity;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapInfoSensor;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapMater;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class CCTVSensorActivity extends AppCompatActivity {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;

    ImageView imageView;
    RelativeLayout rl;
    TextView location;

    public static String nm_camera;
    ArrayList<MapMater> mapMaterArrayList;
    ArrayList<MapInfoSensor> mapInfoSensorArrayList;
    int finalHeight, finalWidth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cctv_sensor);
        setTitle("CCTV");
        rl = (RelativeLayout) findViewById(R.id.rl);
        imageView=findViewById(R.id.imageview);
        location=findViewById(R.id.location);

        location.setText(CCTVBuildActivity.building_name+ " - "+ CCTVFloorActivity.floor_name);

//load Image
        Glide.with(CCTVSensorActivity.this)
                .load(Url + "Images/Monitor/"+ CCTVFloorActivity.floor_image)
                .error(R.drawable.errorimage)
                .into(imageView);
        Log.d("image",Url + "Images/Monitor/"+ CCTVFloorActivity.floor_image);




        new readSensor().execute(Url + "CCTVMonitor/get_info_camera?"+ "build=" + CCTVBuildActivity.building_code +"&floor=" + CCTVFloorActivity.floor_code);
        Log.d("readSensor", Url + "CCTVMonitor/get_info_camera?"+ "build=" + CCTVBuildActivity.building_code+"&floor=" + CCTVFloorActivity.floor_code);
    }
    private class readSensor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mapMaterArrayList = new ArrayList<>();
            String ssid,ss_no,ss_nm,top_position,left_posistion,type;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", CCTVSensorActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ssid = objectRow.getString("id").replace("null", "");
                    ss_no = objectRow.getString("camera_no").replace("null", "");
                    ss_nm = objectRow.getString("camera_nm").replace("null", "");
                    top_position = objectRow.getString("top_position").replace("null", "");
                    left_posistion = objectRow.getString("left_posistion").replace("null", "");
                    type = "";

                    mapMaterArrayList.add(new MapMater(ssid,ss_no,ss_nm,top_position,left_posistion,type));
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finalHeight = imageView.getMeasuredHeight();
                        finalWidth = imageView.getMeasuredWidth();
                        SetSensor();
                    }
                }, 1500);

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CCTVSensorActivity.this);
            }
        }

    }

    private void SetSensor() {

        if (mapMaterArrayList == null){
            return;
        }

        final ImageView[] tv = new ImageView[mapMaterArrayList.size()+1];
        final TextView[] tvs = new TextView[mapMaterArrayList.size() + 1];
        for (int i = 0;i<mapMaterArrayList.size();i++){
            tv[i+1] = new ImageView(this);
            tvs[i + 1] = new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

            String leftx = mapMaterArrayList.get(i).getLeft_posistion().replace("%","");
            String topx = mapMaterArrayList.get(i).getTop_position().replace("%","");

            double dbleft = Double.parseDouble(leftx);
            double dbtop = Double.parseDouble(topx);
            if (dbleft >92){
                dbleft =92;
            }

            if (dbtop >92){
                dbtop =92;
            }
            int left = (int) (finalWidth*dbleft/100);
            int top = (int) (finalHeight*dbtop/100);
            int left2 = (int) (finalWidth * dbleft / 100)- 30;
            int top2 = (int) (finalHeight * dbtop / 100)+ 30;

            params.leftMargin = left;
            params.topMargin = top;
            params2.leftMargin = left2;
            params2.topMargin = top2;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tv[i+1].setImageDrawable(getDrawable(R.drawable.circle_cmr));
                tvs[i + 1].setText(mapMaterArrayList.get(i).getSs_nm());
                tvs[i + 1].setTextColor(Color.parseColor("#000000"));
            }
            tv[i+1].setLayoutParams(params);
            tvs[i + 1].setLayoutParams(params2);
            int finalI = i;
            tv[i+1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(CCTVSensorActivity.this, CCTVActivity.class);

                    Bundle bundle = new Bundle();
                    bundle.putString("nm_camera", mapMaterArrayList.get(finalI).getSs_no());
                    intent.putExtras(bundle);
                    startActivity(intent);
                    //nm_camera = mapMaterArrayList.get(finalI).getSs_no();
                }
            });
            rl.addView(tv[i+1]);
            rl.addView(tvs[i + 1]);
        }

    }

    //@Override
    //protected void onStop() {
        //super.onStop();
       // startService(new Intent(this, NotificationService.class));
    //}
}
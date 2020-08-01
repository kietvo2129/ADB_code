package com.quickblox.sample.videochat.java.fragment.Homeinfosensor;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.pedro.vlc.VlcVideoLibrary;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.CCTV.CCTVList.CCTVActivity;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapInfoSensor;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor.MapMater;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class IssuesToday extends AppCompatActivity{

    List<HomesensorinforMaster> mNoteList = HomesensorinforAdapter.mNoteList;
    ArrayList<MapInfoSensor> mapInfoSensorArrayList;
    int pos = HomesensorinforAdapter.row_index;
    TextView id,name,location,updatetime,warning,action,issuesclose,temp,hum,pow,press,control,status,picture;
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    private VlcVideoLibrary vlcVideoLibrary;
    ImageView imaAction;
    private String[] options = new String[]{":fullscreen"};
    ArrayList<MapMater> mapMaterArrayList;
    int finalHeight, finalWidth;
    int bientam = -1;
    private String nmCCTV="";
    RelativeLayout rl;

    Button buttontoggle;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_issues_today);

        id = findViewById(R.id.id);
        name = findViewById(R.id.name);
        location = findViewById(R.id.location);
        updatetime = findViewById(R.id.updatetime);
        warning = findViewById(R.id.warning);
        action = findViewById(R.id.action);
        issuesclose = findViewById(R.id.issuesclose);
        temp = findViewById(R.id.temp);
        hum = findViewById(R.id.hum);
        pow = findViewById(R.id.pow);
        press = findViewById(R.id.press);
        control = findViewById(R.id.control);
        status = findViewById(R.id.status);
        picture = findViewById(R.id.picture);
        imaAction = findViewById(R.id.imaAction);
        rl = (RelativeLayout) findViewById(R.id.rl);

        id.setText(mNoteList.get(pos).getSs_no());
        name.setText(mNoteList.get(pos).getSs_nm());
        location.setText(mNoteList.get(pos).getBuilding_name()+ " - " + mNoteList.get(pos).getFloor_name());
        updatetime.setText(mNoteList.get(pos).getTime_update());

        if (mNoteList.get(pos).getInfo_warning().equals("")){
            warning.setText("Inform");
        }else {
            warning.setText(mNoteList.get(pos).getInfo_warning());
        }

        if (mNoteList.get(pos).getInfo_finish().equals("")){
            action.setText("Not Informed");
        }else {
            action.setText(mNoteList.get(pos).getInfo_finish());
        }

        issuesclose.setText(mNoteList.get(pos).getTime_finish());
        temp.setText(mNoteList.get(pos).getCurrent_temp().replace("-999",""));
        hum.setText(mNoteList.get(pos).getCurrent_humi().replace("-999",""));
        pow.setText(mNoteList.get(pos).getCurrent_pow().replace("-999",""));
        press.setText(mNoteList.get(pos).getCurrent_press().replace("-999",""));

        if (mNoteList.get(pos).getSs_no().equals("SS-18")){
            control.setText("");
            status.setText("");
        }else {
            control.setText("ON/OFF");
            if (mNoteList.get(pos).getSts_nd().equals("001")){
                status.setText("ON");
            }else {
                status.setText("OFF");
            }
        }

        issuesclose.setText(mNoteList.get(pos).getTime_finish());
        picture.setText("");


        new readSensor().execute(Url + "Monitor/get_info_sensor?" + "build=" + mNoteList.get(pos).getBuilding_code() + "&floor=" + mNoteList.get(pos).getFloor_code());
        Log.d("readSensor", Url + "Monitor/get_info_sensor?" + "build=" + mNoteList.get(pos).getBuilding_code() + "&floor=" + mNoteList.get(pos).getFloor_code());


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
            String ssid, ss_no, ss_nm, top_position, left_posistion,type;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", IssuesToday.this);
                    return;
                }

                Glide.with(IssuesToday.this)
                        .load(Url + "Images/Monitor/" + jsonObject.getString("img") )
                        .error(R.drawable.errorimage)
                        .into(imaAction);
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ssid = objectRow.getString("ssid").replace("null", "");
                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    type = objectRow.getString("type").replace("null", "");
                    top_position = objectRow.getString("top_position").replace("null", "");
                    left_posistion = objectRow.getString("left_posistion").replace("null", "");


                    mapMaterArrayList.add(new MapMater(ssid, ss_no, ss_nm, top_position, left_posistion,type));
                }

                new Handler().postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        finalHeight = imaAction.getMeasuredHeight();
                        finalWidth = imaAction.getMeasuredWidth();
                        SetSensor();
                    }
                }, 1500);

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesToday.this);
            }
        }

    }
    private void SetSensor() {

        if (mapMaterArrayList == null) {
            return;
        }

        final ImageView[] tv = new ImageView[mapMaterArrayList.size() + 1];
        final TextView[] tvs = new TextView[mapMaterArrayList.size() + 1];
        for (int i = 0; i < mapMaterArrayList.size(); i++) {
            tv[i + 1] = new ImageView(this);
            tvs[i + 1] = new TextView(this);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);
            RelativeLayout.LayoutParams params2 = new RelativeLayout.LayoutParams
                    ((int) RelativeLayout.LayoutParams.WRAP_CONTENT, (int) RelativeLayout.LayoutParams.WRAP_CONTENT);

            String leftx = mapMaterArrayList.get(i).getLeft_posistion().replace("%", "");
            String topx = mapMaterArrayList.get(i).getTop_position().replace("%", "");

            double dbleft = Double.parseDouble(leftx);
            double dbtop = Double.parseDouble(topx);
            if (dbleft > 92) {
                dbleft = 92;
            }

            if (dbtop > 92) {
                dbtop = 92;
            }
            int left = (int) (finalWidth * dbleft / 100);
            int top = (int) (finalHeight * dbtop / 100);
            int left2 = (int) (finalWidth * dbleft / 100)- 30;
            int top2 = (int) (finalHeight * dbtop / 100)+ 30;

            params.leftMargin = left;
            params.topMargin = top;
            params2.leftMargin = left2;
            params2.topMargin = top2;

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                if (mapMaterArrayList.get(i).getType().equals("SS")) {
                    tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_view));
                    tvs[i + 1].setText(mapMaterArrayList.get(i).getSs_nm());
                    tvs[i + 1].setTextColor(Color.parseColor("#000000"));
                }else if (mapMaterArrayList.get(i).getType().equals("CMR")) {
                    tv[i + 1].setImageDrawable(getDrawable(R.drawable.circle_cmr));
                    tvs[i + 1].setText(mapMaterArrayList.get(i).getSs_nm());
                    tvs[i + 1].setTextColor(Color.parseColor("#000000"));
                }
            }
            tv[i + 1].setLayoutParams(params);
            tvs[i + 1].setLayoutParams(params2);
            int finalI = i;
            tv[i + 1].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    bientam = finalI;
                    if (mapMaterArrayList.get(finalI).getType().equals("SS")) {
                        new readInfosensor().execute(Url + "/Monitor/ShowDataSS?ss_no=" + mapMaterArrayList.get(finalI).getSs_no());
                        Log.d("readInfosensor", Url + "/Monitor/ShowDataSS?ss_no=" + mapMaterArrayList.get(finalI).getSs_no());
                    }else if (mapMaterArrayList.get(finalI).getType().equals("CMR")) {

                        Intent intent = new Intent(IssuesToday.this, CCTVActivity.class);
                        Bundle bundle = new Bundle();
                        bundle.putString("nm_camera", mapMaterArrayList.get(finalI).getSs_no());
                        intent.putExtras(bundle);
                        startActivity(intent);

                    }


                }
            });
            rl.addView(tv[i + 1]);
            rl.addView(tvs[i + 1]);
        }

    }


    private class readInfosensor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            mapInfoSensorArrayList = new ArrayList<>();
            String ssid, ss_no, ss_nm, current_temp, current_press, current_pow, current_humi, time_update,
                    min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow;

            try {

                JSONObject jsonObject = new JSONObject(s);
                JSONArray jsonArray = jsonObject.getJSONArray("result");

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", IssuesToday.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ssid = objectRow.getString("ssid").replace("null", "");
                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    current_temp = objectRow.getString("current_temp").replace("null", "0").replace("-999", "");
                    current_press = objectRow.getString("current_press").replace("null", "0").replace("-999", "");
                    current_pow = objectRow.getString("current_pow").replace("null", "0").replace("-999", "");
                    current_humi = objectRow.getString("current_humi").replace("null", "0").replace("-999", "");
                    time_update = objectRow.getString("time_update").replace("null", "");
                    min_temp = objectRow.getString("min_temp").replace("null", "0").replace("-999", "");
                    max_temp = objectRow.getString("max_temp").replace("null", "0").replace("-999", "");
                    min_press = objectRow.getString("min_press").replace("null", "0").replace("-999", "");
                    max_press = objectRow.getString("max_press").replace("null", "0").replace("-999", "");
                    min_humi = objectRow.getString("min_humi").replace("null", "0").replace("-999", "");
                    max_humi = objectRow.getString("max_humi").replace("null", "0").replace("-999", "");
                    min_pow = objectRow.getString("min_pow").replace("null", "0").replace("-999", "");
                    max_pow = objectRow.getString("max_pow").replace("null", "0").replace("-999", "");

                    mapInfoSensorArrayList.add(new MapInfoSensor(ssid, ss_no, ss_nm, current_temp, current_press, current_pow, current_humi, time_update,
                            min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow));
                }

                displaysensor();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesToday.this);
            }
        }

    }
    private void displaysensor() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.item_history);//popup_sensor);
        new getOn().execute(Url + "Monitor/Searchnhietdo?ss_no=" + mapMaterArrayList.get(bientam).getSs_no());
        Log.d("getOn", Url + "Monitor/Searchnhietdo?ss_no=" + mapMaterArrayList.get(bientam).getSs_no());

        TextView tvid, tvname, tvtemp, tvhum, tvlocation, tvtime,tvpow,tvpress;
        LinearLayout lnear;
        ImageView icon_temp, icon_hum,icon_press,icon_pow;
        RelativeLayout rlpow,rlpress,rltemp,rlhum;


        tvid = dialog.findViewById(R.id.tvid);
        tvname = dialog.findViewById(R.id.tvname);
        tvtemp = dialog.findViewById(R.id.tvtemp);
        tvhum = dialog.findViewById(R.id.tvhum);
        tvpress = dialog.findViewById(R.id.tvpress);
        tvpow = dialog.findViewById(R.id.tvpow);
        tvlocation = dialog.findViewById(R.id.tvlocation);
        tvlocation.setVisibility(View.GONE);
        tvtime = dialog.findViewById(R.id.tvtime);
        icon_temp = dialog.findViewById(R.id.icon_temp);
        icon_hum = dialog.findViewById(R.id.icon_hum);
        icon_press = dialog.findViewById(R.id.icon_press);
        icon_pow = dialog.findViewById(R.id.icon_pow);
        lnear = dialog.findViewById(R.id.lnear);
        rlpow = dialog.findViewById(R.id.rlpow);
        rlpress = dialog.findViewById(R.id.rlpress);
        rltemp = dialog.findViewById(R.id.rltemp);
        rlhum = dialog.findViewById(R.id.rlhum);
        buttontoggle = dialog.findViewById(R.id.buttontoggle);

        tvid.setText(mapInfoSensorArrayList.get(0).getSs_no());
        tvname.setText(mapInfoSensorArrayList.get(0).getSs_nm());

        if (mapInfoSensorArrayList.get(0).getSs_no().equals("SS-18")){
            buttontoggle.setVisibility(View.GONE);
        }

        buttontoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (buttontoggle.getText().equals("ON")){
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=002");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=002");
                }else {
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=001");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + mapInfoSensorArrayList.get(0).getSs_no() + "&sts=001");
                }
            }
        });


        //tvlocation.setText(mapInfoSensorArrayList.get(0).getBuilding_name() + " - "+ mapInfoSensorArrayList.get(0).getFloor_name());
        tvtime.setText(mapInfoSensorArrayList.get(0).getTime_update());

        if (!mapInfoSensorArrayList.get(0).getCurrent_temp().equals("")){
            rltemp.setVisibility(View.VISIBLE);
            tvtemp.setText(mapInfoSensorArrayList.get(0).getCurrent_temp() + "°C");
            // if (mapInfoSensorArrayList.get(0).getTemp_issue().equals("003")) {
            icon_temp.setBackgroundResource(R.drawable.ic_temgreen);
//            } else {
//                icon_temp.setBackgroundResource(R.drawable.ic_temred);
//            }
        }else {
            rltemp.setVisibility(View.GONE);
        }

        if (!mapInfoSensorArrayList.get(0).getCurrent_humi().equals("")){
            rlhum.setVisibility(View.VISIBLE);
            tvhum.setText(mapInfoSensorArrayList.get(0).getCurrent_humi() + "%");
            // if (mapInfoSensorArrayList.get(0).getHumi_issue().equals("006")) {
            icon_hum.setBackgroundResource(R.drawable.ic_humgreen);
            // } else {
            //    icon_hum.setBackgroundResource(R.drawable.ic_humred);
            //}
        }else {
            rlhum.setVisibility(View.GONE);
        }

        if (!mapInfoSensorArrayList.get(0).getCurrent_press().equals("")){
            rlpress.setVisibility(View.VISIBLE);
            tvpress.setText(mapInfoSensorArrayList.get(0).getCurrent_press() + "Pa");
            // if (mapInfoSensorArrayList.get(0).getPress_issue().equals("009")) {
            icon_press.setBackgroundResource(R.drawable.ic_pressgreen);
            // } else {
            //     icon_press.setBackgroundResource(R.drawable.ic_pressred);
            // }
        }else {
            rlpress.setVisibility(View.GONE);
        }

        if (!mapInfoSensorArrayList.get(0).getCurrent_pow().equals("")){
            rlpow.setVisibility(View.VISIBLE);
            tvpow.setText(mapInfoSensorArrayList.get(0).getCurrent_pow() + "W");
            // if (mapInfoSensorArrayList.get(0).getPow_issue().equals("012")) {
            icon_pow.setBackgroundResource(R.drawable.ic_powgreen);
            // } else {
            //     icon_pow.setBackgroundResource(R.drawable.ic_powred);
            // }
        }else {
            rlpow.setVisibility(View.GONE);
        }

        dialog.show();
    }
    private class getOn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("result").replace("[\"", "").replace("\"]", "").equals("001")) {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                } else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
                    if (buttontoggle.getText().equals("ON")){
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    }else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    }
                }else {
                    if (buttontoggle.getText().equals("ON")){
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    }else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                if (buttontoggle.getText().equals("ON")){
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                }else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            }
        }

    }
}
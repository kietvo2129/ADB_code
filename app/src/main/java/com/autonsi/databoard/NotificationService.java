package com.autonsi.databoard;


import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.Service;
import android.app.TaskStackBuilder;
import android.content.Context;
import android.content.Intent;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.IBinder;
import android.util.Log;

import androidx.core.app.NotificationCompat;

import com.autonsi.databoard.DigitalData.IssuesList.IssuesActivity;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class NotificationService extends Service {
    String Url = com.autonsi.databoard.Url.webUrl;
    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 5;
    List<String> listID =new ArrayList<>();
    List<String> listCu =new ArrayList<>();
    @Override
    public IBinder onBind(Intent arg0) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.e(TAG, "onStartCommand");
        super.onStartCommand(intent, flags, startId);

        startTimer();

        return START_STICKY;
    }


    @Override
    public void onCreate() {
        Log.e(TAG, "onCreate");


    }

    @Override
    public void onDestroy() {
        Log.e(TAG, "onDestroy");
        //stoptimertask();
        super.onDestroy();


    }

    //we are going to use a handler to be able to run in our TimerTask
    final Handler handler = new Handler();


    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 5000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }

    public void stoptimertask() {
        //stop the timer, if it's not already null
        if (timer != null) {
            timer.cancel();
            timer = null;
        }
    }

    public void initializeTimerTask() {

        timerTask = new TimerTask() {
            public void run() {

                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {


                        YOURNOTIFICATIONFUNCTION();

                    }
                });
            }
        };
    }

    private void YOURNOTIFICATIONFUNCTION() {
        new DataExpectDate().execute(Url+"monitor/AlarmIssues");
        Log.d("DataExpectDate",Url+ "monitor/AlarmIssues");


    }
    class DataExpectDate extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            listID =new ArrayList<>();
            try {
                JSONObject object = new JSONObject(s);
                Log.e("Data",s);
                JSONArray jsonArray = object.getJSONArray("result");

                if (jsonArray.length() == 0){
                    listID =new ArrayList<>();
                    return;
                }
                else {
                    Intent intent = new Intent(getBaseContext(), IssuesActivity.class);
                    String name = "";
                    for (int i=0;i<jsonArray.length();i++){
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        listID.add(jsonObject.getString("id"));
                        name += jsonObject.getString("building_nm")+" - " +jsonObject.getString("floor_nm")+ ". ";
                    }

                    if (!listID.toString().equals(listCu.toString())) {
                        listCu = listID;
                        showNotification(1, getBaseContext(), "DataBoard!", "Alert from " + name, intent);
                        Log.e("IDDD",listCu + "  "+ listID);

                        new Handler().postDelayed(new Runnable() {

                            @Override
                            public void run() {
                                for (int i =0;i<listID.size();i++){

                                    new Datarelace().execute(Url+ "monitor/ReplaceAlarmIssues?id=" + listID.get(i));
                                    Log.d("Datarelace", Url+"monitor/ReplaceAlarmIssues?id=" + listID.get(i));
                                }
                            }
                        }, 60000);


                    }
                }



            } catch (JSONException e) {
                e.printStackTrace();
            }


        }
    }
    class Datarelace extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... strings) {
            return NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            Log.e("relace","OKEE");

        }
    }

    private String NoiDung_Tu_URL(String theUrl) {
        StringBuilder content = new StringBuilder();
        try {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }
    public void showNotification(int notificationId, Context context, String title, String body, Intent intent) {
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        String channelId = "channel-01";
        String channelName = "Channel Name";
        int importance = NotificationManager.IMPORTANCE_HIGH;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel mChannel = new NotificationChannel(
                    channelId, channelName, importance);
            notificationManager.createNotificationChannel(mChannel);
        }

        try {
            Uri notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
            Ringtone r = RingtoneManager.getRingtone(getApplicationContext(), notification);
            r.play();
        } catch (Exception e) {
            e.printStackTrace();
        }

        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context, channelId)
                .setSmallIcon(R.mipmap.ic_noti)
                .setContentTitle(title)
                .setAutoCancel(true)
                .setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
                .setGroup("GROUP_KEY_WORK_EMAIL")
                .setContentText(body);

        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        //stackBuilder.addNextIntent(intent);
//        PendingIntent resultPendingIntent = stackBuilder.getPendingIntent(
//                0,
//                PendingIntent.FLAG_UPDATE_CURRENT
//        );
 //       mBuilder.setContentIntent(resultPendingIntent);
        notificationManager.notify(notificationId, mBuilder.build());
    }
}

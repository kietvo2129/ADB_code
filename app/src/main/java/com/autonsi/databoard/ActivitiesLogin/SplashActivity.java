package com.autonsi.databoard.ActivitiesLogin;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Counting.Count.CountActivity;
import com.autonsi.databoard.NotificationService;
import com.quickblox.sample.videochat.java.BuildConfig;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SplashActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    private static final int SPLASH_DELAY = 4000;
    private SharedPreferences sharedPreferences;
    int versionCode = BuildConfig.VERSION_CODE;
    String versionName = BuildConfig.VERSION_NAME;
    String latestVersion="",latestVersionCode="",url="",releaseNotes="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startService(new Intent(this, NotificationService.class));
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }
        new read_infor_app().execute(Url+"APIProduct/GetApp_info");
    }

    private class read_infor_app extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                latestVersion = jsonObject.getString("latestVersion").replace("null","");
                latestVersionCode = jsonObject.getString("latestVersionCode").replace("null","");
                url = jsonObject.getString("url").replace("null","");
                releaseNotes = jsonObject.getString("releaseNotes").replace("null","");
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", SplashActivity.this);
            }
            checkversionapp();
        }

    }

    private void checkversionapp(){
        //Toast.makeText(this, latestVersionCode+"    "+latestVersion, Toast.LENGTH_SHORT).show();
        if (latestVersionCode.equals(versionCode+"")&&latestVersion.equals(versionName)){

            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (sharedPreferences.getBoolean("TK",false)) {
                        //  LoginService.start(SplashActivity.this, sharedPrefsHelper.getQbUser());
                        // OpponentsActivity.start(SplashActivity.this);

                        Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        LoginActivity.start(SplashActivity.this);
                    }
                    finish();
                }
            }, SPLASH_DELAY);

        }else {
            Baoloi("Please, update new version app.");
        }
    }

    public void Baoloi(String text) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Warning!!!");
        alertDialog.setMessage(text + "\n"+ releaseNotes);
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });
        alertDialog.show();
    }

    @Override
    protected void onResume() {
        //checkversionapp();
        new read_infor_app().execute(Url+"APIProduct/GetApp_info");
        super.onResume();
    }
}
package com.quickblox.sample.videochat.java.ActivitiesLogin;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.quickblox.sample.videochat.java.NotificationService;
import com.quickblox.sample.videochat.java.R;

public class SplashActivity extends AppCompatActivity {
    private static final int SPLASH_DELAY = 4000;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        startService(new Intent(this, NotificationService.class));
        sharedPreferences = getSharedPreferences("datalogin", MODE_PRIVATE);


        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

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
    }

}
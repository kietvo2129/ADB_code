package com.quickblox.sample.videochat.java;

import android.app.Application;
import android.os.Handler;

import java.util.Timer;
import java.util.TimerTask;

public class App extends Application {

    private static App instance;
    Timer timer;
    TimerTask timerTask;
    String TAG = "Timers";
    int Your_X_SECS = 10;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
      //  startTimer();
    }

    public void startTimer() {
        //set a new Timer
        timer = new Timer();
        //initialize the TimerTask's job
        initializeTimerTask();

        //schedule the timer, after the first 5000ms the TimerTask will run every 10000ms
        timer.schedule(timerTask, 10000, Your_X_SECS * 1000); //
        //timer.schedule(timerTask, 5000,1000); //
    }
    final Handler handler = new Handler();
    public void initializeTimerTask() {
        timerTask = new TimerTask() {
            public void run() {
                //use a handler to run a toast that shows the current timestamp
                handler.post(new Runnable() {
                    public void run() {
                        //YOURNOTIFICATIONFUNCTION();
                    }
                });
            }
        };
    }

}
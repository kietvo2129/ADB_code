package com.quickblox.sample.videochat.java.CCTV.CCTVList;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.quickblox.sample.videochat.java.R;


public class CCTVActivity extends AppCompatActivity /*implements VlcListener, View.OnClickListener*/ {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    WebView myWebView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_cctv);
        setTitle("CCTV");
        ImageView icback = findViewById(R.id.icback);
        icback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        myWebView = (WebView) findViewById(R.id.webview);
        WebSettings webSettings = myWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLoadWithOverviewMode(true);
        Bundle bundle = getIntent().getExtras();
        String nm_camera = bundle.getString("nm_camera");
        myWebView.loadUrl(Url + "/Display/Camera?ss_no=" + nm_camera);
    }
}

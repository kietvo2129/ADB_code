package com.quickblox.sample.videochat.java.CCTV.CCTVList;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.pedro.vlc.VlcListener;
import com.pedro.vlc.VlcVideoLibrary;
import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;

import org.videolan.libvlc.MediaPlayer;

import java.util.Arrays;

/**
 * Created by pedro on 25/06/17.
 */
public class CCTVActivity extends AppCompatActivity implements VlcListener, View.OnClickListener {

  private VlcVideoLibrary vlcVideoLibrary;
  private ProgressDialog dialog;
  private String[] options = new String[]{":fullscreen"};
  SurfaceView surfaceView;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
    setContentView(R.layout.activity_cctv);
    setTitle("CCTV");
    surfaceView = (SurfaceView) findViewById(R.id.surfaceView);
    ImageView icback = findViewById(R.id.icback);
    icback.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        finish();
      }
    });
    dialog = new ProgressDialog(this);

    dialog.setMessage("Loading...");
    dialog.setCancelable(false);
    dialog.show();

    vlcVideoLibrary = new VlcVideoLibrary(this, this, surfaceView);
    vlcVideoLibrary.setOptions(Arrays.asList(options));
    new Handler().postDelayed(new Runnable() {

      @Override
      public void run() {
        Bundle bundle = getIntent().getExtras();
        String nm_camera = bundle.getString("nm_camera");
        if (nm_camera.equals("camera_01")) {
          vlcVideoLibrary.play("rtsp://admin:sy0630hi@180.93.223.114:554");
        }else if(nm_camera.equals("camera_02")){
          vlcVideoLibrary.play("rtsp://admin:sy0630hi@192.168.100.141");
        }else if(nm_camera.equals("camera_03")){
          vlcVideoLibrary.play("rtsp://admin:sy0630hi@192.168.100.142");
        }else {
          dialog.dismiss();
          AlerError.Baoloi("Unregistered camera", CCTVActivity.this);
        }
      }
    }, 2000);

  }

  @Override
  public void onComplete() {
    Toast.makeText(this, "Playing", Toast.LENGTH_SHORT).show();
  }


  @Override
  public void onError() {
    Toast.makeText(this, "Error, make sure your endpoint is correct", Toast.LENGTH_SHORT).show();
    vlcVideoLibrary.stop();
  }

  @Override
  public void onBuffering(MediaPlayer.Event event) {
    if (event.getBuffering()>50) {
      dialog.dismiss();
    }
  }

  @Override
  public void onClick(View view) {
//    if (!vlcVideoLibrary.isPlaying()) {
//      vlcVideoLibrary.play("rtsp://admin:sy0630hi@180.93.223.114:554");
//      bStartStop.setText("stop");//getString(R.string.stop_player));
//    } else {
//      vlcVideoLibrary.stop();
//      bStartStop.setText("start");//getString(R.string.start_player));
//    }
  }
}

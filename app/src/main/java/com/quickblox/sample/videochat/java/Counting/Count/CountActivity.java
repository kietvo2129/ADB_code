package com.quickblox.sample.videochat.java.Counting.Count;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;

public class CountActivity extends AppCompatActivity {

    CardView cv_id,cv_infor;
    TextView tv_actual,tv_id;
    TextSwitcher temperatureSwitcher;
    RelativeLayout tv_actual_plus;
    //int animH[] = new int[] {R.anim.slide_in_right, R.anim.slide_out_left};
    int animV[] = new int[] {R.anim.slide_in_top, R.anim.slide_out_bottom};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        cv_id = findViewById(R.id.h1);
        cv_infor = findViewById(R.id.h2);
        //tv_id= findViewById(R.id.tv_id);
        tv_actual = findViewById(R.id.tv_actual);
        tv_actual_plus = findViewById(R.id.tv_actual_plus);
        cv_infor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CountActivity.this, "ladlsdkas", Toast.LENGTH_SHORT).show();
            }
        });
        cv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeID();
            }
        });

        tv_actual.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CountActivity.this, "OKKK", Toast.LENGTH_SHORT).show();
                tv_actual.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_actual.setEnabled(true);
                    }
                },1000);
            }
        });
        tv_actual_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(CountActivity.this, "OKKK", Toast.LENGTH_SHORT).show();
                tv_actual_plus.setEnabled(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        tv_actual_plus.setEnabled(true);
                    }
                },1000);
            }
        });

        temperatureSwitcher = (TextSwitcher) findViewById(R.id.ts_temperature);
        temperatureSwitcher.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        temperatureSwitcher.setCurrentText("AAAA");

    }

    private class TextViewFactory implements  ViewSwitcher.ViewFactory {

        @StyleRes
        final int styleId;
        final boolean center;

        TextViewFactory(@StyleRes int styleId, boolean center) {
            this.styleId = styleId;
            this.center = center;
        }

        @SuppressWarnings("deprecation")
        @Override
        public View makeView() {
            final TextView textView = new TextView(CountActivity.this);

            if (center) {
                textView.setGravity(Gravity.CENTER);
            }

            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                textView.setTextAppearance(CountActivity.this, styleId);
            } else {
                textView.setTextAppearance(styleId);
            }

            return textView;
        }

    }

    private void showChangeID() {
        ArrayList<String> listItems = new ArrayList<>();
        listItems.add("Line 1 - line san xuat");
        listItems.add("Line 2 - line cat");
        listItems.add("Line 3 - line hap");
        //final String[] listItems = {"Line 1 - line san xuat","Line 2 - line cat","Line 3 - line hap"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(CountActivity.this);
        mbuilder.setTitle("Choose line");

        mbuilder.setSingleChoiceItems(listItems.toArray(new String[listItems.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {

                temperatureSwitcher.setInAnimation(CountActivity.this, animV[0]);
                temperatureSwitcher.setOutAnimation(CountActivity.this, animV[1]);
                temperatureSwitcher.setText(listItems.get(i).toString());


           //     recreate();
                dialog.dismiss();
            }
        });

        AlertDialog mdialog = mbuilder.create();
        mdialog.show();
    }
//    Animation slideUp = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_top);
//                tv_id.startAnimation(slideUp);
//
//                slideUp.setAnimationListener(new Animation.AnimationListener() {
//        @Override
//        public void onAnimationStart(Animation animation) {
//        }
//        @Override
//        public void onAnimationEnd(Animation animation) {
//            Animation slidedown = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slide_in_bottom);
//            tv_id.startAnimation(slidedown);
//            tv_id.setText(listItems.get(i));
//            slidedown.setAnimationListener(new Animation.AnimationListener() {
//                @Override
//                public void onAnimationStart(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationEnd(Animation animation) {
//
//                }
//
//                @Override
//                public void onAnimationRepeat(Animation animation) {
//
//                }
//            });
//
//        }
//        @Override
//        public void onAnimationRepeat(Animation animation) {
//        }
//    });
}
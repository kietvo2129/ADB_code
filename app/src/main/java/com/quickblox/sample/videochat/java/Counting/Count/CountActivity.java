package com.quickblox.sample.videochat.java.Counting.Count;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.quickblox.sample.videochat.java.AlerError.AlerError;
import com.quickblox.sample.videochat.java.CCTV.CCTVLayout.CCTVBuildActivity;
import com.quickblox.sample.videochat.java.Counting.Count.Line.LineMaster;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapBuild.MapBuildMatter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class CountActivity extends AppCompatActivity {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    CardView cv_id, cv_infor;
    TextView tvlocation,tv_taget,tv_time;
    TextSwitcher tv_id, tv_actual, tv_defective;
    RelativeLayout rl_actual_plus, rl_actual_sub, rl_defective_sub, rl_defective_plus;
    int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
    int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};
    int animreV[] = new int[]{R.anim.slide_in_bottom, R.anim.slide_out_top};
    int numActual = 0;
    int numDefective = 0;

    ArrayList<LineMaster> lineMaster;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);
        setTitle("Counting");
        cv_id = findViewById(R.id.h1);
        cv_infor = findViewById(R.id.h2);
        tv_defective = findViewById(R.id.tv_defective);
        tv_actual = findViewById(R.id.tv_actual);
        rl_actual_plus = findViewById(R.id.rl_actual_plus);
        rl_actual_sub = findViewById(R.id.rl_actual_sub);
        rl_defective_sub = findViewById(R.id.rl_defective_sub);
        rl_defective_plus = findViewById(R.id.rl_defective_plus);


        tvlocation = findViewById(R.id.tvlocation);
        tv_taget = findViewById(R.id.tv_taget);
        tv_time = findViewById(R.id.tv_time);

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
                tv_actual.setEnabled(false);
            }
        });

        rl_actual_plus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputnum("Actual");
                return true;
            }
        });
        rl_defective_plus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputnum("Defective");
                return true;
            }
        });

        rl_actual_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_actual_plus.setEnabled(false);
                numActual += 1;
                tv_actual.setInAnimation(CountActivity.this, animV[0]);
                tv_actual.setOutAnimation(CountActivity.this, animV[1]);
                tv_actual.setText(numActual + "");
                DelayClick(rl_actual_plus);

            }
        });
        rl_actual_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_actual_sub.setEnabled(false);
                numActual -= 1;
                if (numActual < 0) {
                    numActual = 0;
                    DelayClick(rl_actual_sub);
                } else {
                    tv_actual.setInAnimation(CountActivity.this, animreV[0]);
                    tv_actual.setOutAnimation(CountActivity.this, animreV[1]);
                    tv_actual.setText(numActual + "");
                    DelayClick(rl_actual_sub);
                }
            }
        });

        rl_defective_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_defective_plus.setEnabled(false);
                numDefective += 1;
                tv_defective.setInAnimation(CountActivity.this, animV[0]);
                tv_defective.setOutAnimation(CountActivity.this, animV[1]);
                tv_defective.setText(numDefective + "");
                DelayClick(rl_defective_plus);

            }
        });
        rl_defective_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_defective_sub.setEnabled(false);
                numDefective -= 1;
                if (numDefective < 0) {
                    numDefective = 0;
                    DelayClick(rl_defective_sub);
                } else {
                    tv_defective.setInAnimation(CountActivity.this, animreV[0]);
                    tv_defective.setOutAnimation(CountActivity.this, animreV[1]);
                    tv_defective.setText(numDefective + "");
                    DelayClick(rl_defective_sub);
                }
            }
        });
        tv_id = (TextSwitcher) findViewById(R.id.ts_temperature);
        tv_id.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        tv_actual.setFactory(new TextViewFactory(R.style.NumActualTextView, true));
        tv_defective.setFactory(new TextViewFactory(R.style.NumActualTextView, true));


        getDataline();
    }

    private void getDataline() {
        new getLineData().execute(Url + "plan/getDataLineInfoToday");
        Log.d("getLineData",Url + "plan/getDataLineInfoToday");
    }

    private class getLineData extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lineMaster = new ArrayList<>();
            String id,line_no,line_nm;
            //String id,line_no,line_nm,building_nm,floor_nm,target_qty,actual_qty,defect_qty,start_time,end_time;
            try {

                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("Plan does not exist today. Please, insert Plan for today.", CountActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    id = objectRow.getString("id").replace("null", "");
                    line_no = objectRow.getString("line_no").replace("null", "");
                    line_nm = objectRow.getString("line_nm").replace("null", "");
                    lineMaster.add(new LineMaster(id,line_no,line_nm));
                }
                tv_id.setText(lineMaster.get(0).getLine_no()+ "/"+lineMaster.get(0).getLine_nm());
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountActivity.this);
            }
        }

    }

    private void DelayClick(RelativeLayout rl) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                rl.setEnabled(true);
            }
        }, 1000);
    }

    private class TextViewFactory implements ViewSwitcher.ViewFactory {

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
        for (int i =0 ; i<lineMaster.size();i++){
            listItems.add(lineMaster.get(i).getLine_no() + "/" + lineMaster.get(i).getLine_nm());
        }
        //final String[] listItems = {"Line 1 - line san xuat","Line 2 - line cat","Line 3 - line hap"};
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(CountActivity.this);
        mbuilder.setTitle("Choose line");

        mbuilder.setSingleChoiceItems(listItems.toArray(new String[listItems.size()]), -1, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                tv_id.setInAnimation(CountActivity.this, animH[0]);
                tv_id.setOutAnimation(CountActivity.this, animH[1]);
                tv_id.setText(listItems.get(i).toString());

                loadDataAll(i);

                dialog.dismiss();
            }
        });

        AlertDialog mdialog = mbuilder.create();
        mdialog.show();
    }

    private void loadDataAll(int pos) {

        new loadDataAll().execute(Url+"plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(pos).getId());
        Log.d("loadDataAll",Url+"plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(pos).getId());

    }
    private class loadDataAll extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lineMaster = new ArrayList<>();
            String id,line_no,line_nm;
            //String id,line_no,line_nm,building_nm,floor_nm,target_qty,actual_qty,defect_qty,start_time,end_time;
            try {

                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("Plan does not exist today. Please, insert Plan for today.", CountActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    id = objectRow.getString("id").replace("null", "");
                    line_no = objectRow.getString("line_no").replace("null", "");
                    line_nm = objectRow.getString("line_nm").replace("null", "");
                    lineMaster.add(new LineMaster(id,line_no,line_nm));
                }
                tv_id.setText(lineMaster.get(0).getLine_no()+ "/"+lineMaster.get(0).getLine_nm());
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountActivity.this);
            }
        }

    }


    private void inputnum(String key) {
        AlertDialog.Builder builder = new AlertDialog.Builder(CountActivity.this);
        View viewInflated = LayoutInflater.from(CountActivity.this).inflate(R.layout.number_input_layout, null);
        builder.setTitle("Input " + key);
        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);
        TextView tv_title = viewInflated.findViewById(R.id.tv_title);
        if (key.equals("Actual")) {
            int tong = numActual;
            String title = key + " new= " + numActual + " + " + "0" + " = " + tong;
            tv_title.setText(title);
        }else if (key.equals("Defective")){
            int tong = numDefective;
            String title = key + " new= " + numDefective + " + " + "0" + " = " + tong;
            tv_title.setText(title);
        }


        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int numinput2 = 0;
                if (input.getText().toString().length() == 0) {
                    numinput2 = 0;
                }else {
                    numinput2 = Integer.parseInt(input.getText().toString());
                }
                int tong = 0;
                if (key.equals("Actual")) {
                    tong = numActual + numinput2;
                    String title = key + " new= " + numActual + " + " + numinput2 + " = " + tong;
                    tv_title.setText(title);
                }else if(key.equals("Defective")){
                    tong = numDefective + numinput2;
                    String title = key + " new= " + numDefective + " + " + numinput2 + " = " + tong;
                    tv_title.setText(title);
                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });


        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if (input.getText().toString().length() == 0 || input.getText().toString().equals("0")) {
                    return;
                } else if (key.equals("Actual")) {
                    numActual += Integer.parseInt(input.getText().toString().trim());
                    tv_actual.setInAnimation(CountActivity.this, animV[0]);
                    tv_actual.setOutAnimation(CountActivity.this, animV[1]);
                    tv_actual.setText(numActual + "");
                    dialog.dismiss();
                } else if (key.equals("Defective")) {
                    numDefective += Integer.parseInt(input.getText().toString().trim());
                    tv_defective.setInAnimation(CountActivity.this, animV[0]);
                    tv_defective.setOutAnimation(CountActivity.this, animV[1]);
                    tv_defective.setText(numDefective + "");
                    dialog.dismiss();
                }
            }
        });

        builder.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        builder.show();
    }
}
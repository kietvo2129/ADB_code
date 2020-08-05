package com.quickblox.sample.videochat.java.Counting.Count;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import com.quickblox.sample.videochat.java.Counting.Count.Detail.CountingDetailActivity;
import com.quickblox.sample.videochat.java.Counting.Count.Line.DataAllCountingMaster;
import com.quickblox.sample.videochat.java.Counting.Count.Line.LineMaster;
import com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapBuild.MapBuildMatter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class CountActivity extends AppCompatActivity {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    CardView cv_id, cv_infor;
    TextSwitcher tv_taget, tv_time;
    TextSwitcher tv_id, tv_actual, tv_defective,tvlocation;
    RelativeLayout rl_actual_plus, rl_actual_sub, rl_defective_sub, rl_defective_plus;
    int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
    int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};
    int animreV[] = new int[]{R.anim.slide_in_bottom, R.anim.slide_out_top};
    int numActual = 0;
    int numDefective = 0, giatri_Input = 0;
    String vitri_bam = "";

    String id_line = "";

    ArrayList<LineMaster> lineMaster;
    ArrayList<DataAllCountingMaster> dataAllCountingMasters;
    DecimalFormat formatter = new DecimalFormat("#,###,###");

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

                Intent intent = new Intent(CountActivity.this, CountingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_line", id_line);
                intent.putExtras(bundle);
                startActivity(intent);

            }
        });
        cv_id.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showChangeID();
            }
        });


        rl_actual_plus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputnum("Actual");
                vitri_bam = "APL";
                return true;
            }
        });
        rl_defective_plus.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                inputnum("Defective");
                vitri_bam = "DPL";
                return true;
            }
        });

        rl_actual_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_actual_plus.setEnabled(false);
                vitri_bam = "AP";
                sendDataCounting(1);
                DelayClick(rl_actual_plus);

            }
        });
        rl_actual_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_actual_sub.setEnabled(false);
                if (numActual <= 0) {
                    numActual = 0;
                    DelayClick(rl_actual_sub);
                } else {
                    vitri_bam = "AS";
                    sendDataCounting(-1);
                    DelayClick(rl_actual_sub);
                }
            }
        });

        rl_defective_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_defective_plus.setEnabled(false);
                vitri_bam = "DP";
                sendDataCounting(1);
                DelayClick(rl_defective_plus);

            }
        });
        rl_defective_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rl_defective_sub.setEnabled(false);
                if (numDefective <= 0) {
                    numDefective = 0;
                    DelayClick(rl_defective_sub);
                } else {
                    vitri_bam = "DS";
                    sendDataCounting(-1);
                    DelayClick(rl_defective_sub);
                }
            }
        });
        tv_id = (TextSwitcher) findViewById(R.id.ts_temperature);
        tv_id.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        tv_actual.setFactory(new TextViewFactory(R.style.NumActualTextView, true));
        tv_defective.setFactory(new TextViewFactory(R.style.NumActualTextView, true));
        tvlocation.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        tv_time.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        tv_taget.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        getDataline();
    }


    private void sendDataCounting(int value) {
        if (vitri_bam.equals("AP")) {
            new sendDataCount().execute(Url + "plan/updateActualToday?id=" + id_line + "&actual_qty=" + value + "&defect_qty=" + 0);
        } else if (vitri_bam.equals("AS")) {
            new sendDataCount().execute(Url + "plan/updateActualToday?id=" + id_line + "&actual_qty=" + value + "&defect_qty=" + 0);
        } else if (vitri_bam.equals("DP")) {
            new sendDataCount().execute(Url + "plan/updateActualToday?id=" + id_line + "&actual_qty=" + 0 + "&defect_qty=" + value);
        } else if (vitri_bam.equals("DS")) {
            new sendDataCount().execute(Url + "plan/updateActualToday?id=" + id_line + "&actual_qty=" + 0 + "&defect_qty=" + value);
        } else if (vitri_bam.equals("APL")) {
            new sendDataCount().execute(Url + "plan/updateActualToday?id=" + id_line + "&actual_qty=" + value + "&defect_qty=" + 0);
        } else if (vitri_bam.equals("DPL")) {
            new sendDataCount().execute(Url + "plan/updateActualToday?id=" + id_line + "&actual_qty=" + 0 + "&defect_qty=" + value);
        }
    }

    private class sendDataCount extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getBoolean("result")) {
                    if (vitri_bam.equals("AP")) {
                        numActual = jsonObject.getInt("valueActual");
                        //numActual += 1;
                        setAnimation(tv_actual, numActual + "");
                    } else if (vitri_bam.equals("AS")) {
                        numActual = jsonObject.getInt("valueActual");
                        //numActual -= 1;
                        setAnimation(tv_actual, numActual + "");
                    } else if (vitri_bam.equals("DP")) {
                        //numDefective += 1;
                        numDefective = jsonObject.getInt("valueDefect");
                        setAnimation(tv_defective, numDefective + "");
                    } else if (vitri_bam.equals("DS")) {
                        //numDefective -= 1;
                        numDefective = jsonObject.getInt("valueDefect");
                        setAnimation(tv_defective, numDefective + "");
                    } else if (vitri_bam.equals("APL")) {
                        //numActual += giatri_Input;
                        numActual = jsonObject.getInt("valueActual");
                        setAnimation(tv_actual, numActual + "");
                    } else if (vitri_bam.equals("DPL")) {
                        //numDefective += giatri_Input;
                        numDefective = jsonObject.getInt("valueDefect");
                        setAnimation(tv_defective, numDefective + "");
                    }


                } else {
                    AlerError.Baoloi(jsonObject.getString("message"), CountActivity.this);
                    return;
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountActivity.this);
            }
        }

    }

    private void getDataline() {
        new getLineData().execute(Url + "plan/getDataLineInfoToday");
        Log.d("getLineData", Url + "plan/getDataLineInfoToday");
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
            String id, line_no, line_nm;
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
                    lineMaster.add(new LineMaster(id, line_no, line_nm));
                }
                tv_id.setText(lineMaster.get(0).getLine_no() + "/" + lineMaster.get(0).getLine_nm());
                id_line = lineMaster.get(0).getId();
                loadDataAll(0);
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
        }, 500);
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
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(CountActivity.this, android.R.layout.select_dialog_singlechoice);
        for (int i = 0; i < lineMaster.size(); i++) {
            arrayAdapter.add(lineMaster.get(i).getLine_no() + "/" + lineMaster.get(i).getLine_nm());
        }
        AlertDialog.Builder mbuilder = new AlertDialog.Builder(CountActivity.this);
        AlertDialog.Builder builderSingle = new AlertDialog.Builder(CountActivity.this);
        builderSingle.setTitle("Select One Line:");
        builderSingle.setNegativeButton("cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        builderSingle.setAdapter(arrayAdapter, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int i) {
                tv_id.setInAnimation(CountActivity.this, animH[0]);
                tv_id.setOutAnimation(CountActivity.this, animH[1]);
                tv_id.setText(arrayAdapter.getItem(i).toString());
                id_line = lineMaster.get(i).getId();
                loadDataAll(i);
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    private void loadDataAll(int pos) {

        new loadDataAll().execute(Url + "plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(pos).getId());
        Log.d("loadDataAll", Url + "plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(pos).getId());

    }

    private class loadDataAll extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dataAllCountingMasters = new ArrayList<>();
            String building_nm, floor_nm, target_qty, actual_qty, defect_qty, start_time, end_time;
            try {

                JSONObject jsonObject = new JSONObject(s);

                if (!jsonObject.getBoolean("result")) {
                    AlerError.Baoloi(jsonObject.getString("message"), CountActivity.this);
                    return;
                }
                JSONObject jsonObject1 = jsonObject.getJSONObject("list_data");

                building_nm = jsonObject1.getString("building_nm").replace("null", "");
                floor_nm = jsonObject1.getString("floor_nm").replace("null", "");
                target_qty = jsonObject1.getString("target_qty").replace("null", "");
                actual_qty = jsonObject1.getString("actual_qty").replace("null", "");
                defect_qty = jsonObject1.getString("defect_qty").replace("null", "");
                start_time = jsonObject1.getString("start_time").replace("null", "");
                end_time = jsonObject1.getString("end_time").replace("null", "");
                dataAllCountingMasters.add(new DataAllCountingMaster(building_nm, floor_nm, target_qty, actual_qty, defect_qty, start_time, end_time));
                setDataAll();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", CountActivity.this);
            }
        }

    }

    private void setDataAll() {

        numActual = Integer.parseInt(dataAllCountingMasters.get(0).getActual_qty());
        numDefective = Integer.parseInt(dataAllCountingMasters.get(0).getDefect_qty());
        tvlocation.setText(dataAllCountingMasters.get(0).getBuilding_nm() + " - " + dataAllCountingMasters.get(0).getFloor_nm());
        tv_taget.setText(formatter.format(Integer.parseInt(dataAllCountingMasters.get(0).getTarget_qty())));
        String time = dataAllCountingMasters.get(0).getStart_time().substring(0, 2) + ":" + dataAllCountingMasters.get(0).getStart_time().substring(2, 4)
                + " ~ " + dataAllCountingMasters.get(0).getEnd_time().substring(0, 2) + ":" + dataAllCountingMasters.get(0).getEnd_time().substring(2, 4);
        tv_time.setText(time);

        setAnimation(tv_actual, numActual + "");
        setAnimation(tv_defective, numDefective + "");
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
            String title = key + " new = " + formatter.format(numActual) + " + " + "0" + " = " + formatter.format(tong);
            tv_title.setText(title);
        } else if (key.equals("Defective")) {
            int tong = numDefective;
            String title = key + " new = " + formatter.format(numDefective) + " + " + "0" + " = " + formatter.format(tong);
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
                } else {
                    numinput2 = Integer.parseInt(input.getText().toString());
                }
                int tong = 0;
                if (key.equals("Actual")) {
                    tong = numActual + numinput2;
                    String title = key + " new = " + formatter.format(numActual) + " + " + formatter.format(numinput2) + " = " + formatter.format(tong);
                    tv_title.setText(title);
                } else if (key.equals("Defective")) {
                    tong = numDefective + numinput2;
                    String title = key + " new = " + formatter.format(numDefective) + " + " + formatter.format(numinput2) + " = " + formatter.format(tong);
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
                } //else if (key.equals("Actual")) {
                giatri_Input = Integer.parseInt(input.getText().toString().trim());
                sendDataCounting(Integer.parseInt(input.getText().toString().trim()));
                dialog.dismiss();
//                } else if (key.equals("Defective")) {
//                    giatri_Input=Integer.parseInt(input.getText().toString().trim());
//                    sendDataCounting(Integer.parseInt(input.getText().toString().trim()));
//                    dialog.dismiss();
//                }
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

    public void setAnimation(TextSwitcher tv, String value) {


        if (vitri_bam.equals("AP") || vitri_bam.equals("DP") || vitri_bam.equals("APL") || vitri_bam.equals("DPL")) {
            tv.setInAnimation(CountActivity.this, animV[0]);
            tv.setOutAnimation(CountActivity.this, animV[1]);
            tv.setText(formatter.format(Integer.parseInt(value)));
        } else {
            tv.setInAnimation(CountActivity.this, animreV[0]);
            tv.setOutAnimation(CountActivity.this, animreV[1]);
            tv.setText(formatter.format(Integer.parseInt(value)));
        }

    }
}
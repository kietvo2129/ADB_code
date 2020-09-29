package com.autonsi.databoard.Counting.Count;

import androidx.annotation.StyleRes;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.PendingIntent;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbInterface;
import android.hardware.usb.UsbManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.StrictMode;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextSwitcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Barcode.BarcodeAdapter;
import com.autonsi.databoard.Barcode.BarcodeMaster;
import com.autonsi.databoard.Counting.Count.Detail.CountingDetailActivity;
import com.autonsi.databoard.Counting.Count.Line.DataAllCountingMaster;
import com.autonsi.databoard.Counting.Count.Line.LineMaster;
import com.autonsi.databoard.Counting.Count.QCCheck.QCCheckActivity;
import com.bixolon.labelprinter.BixolonLabelPrinter;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

public class CountActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    CardView cv_id, cv_infor;
    TextSwitcher tv_taget, tv_time, tv_product, tv_qc;
    TextSwitcher tv_id, tv_actual, tv_defective, tvlocation;
    RelativeLayout rl_actual_plus, rl_actual_sub, rl_defective_sub, rl_defective_plus;
    int animH[] = new int[]{R.anim.slide_in_right, R.anim.slide_out_left};
    int animV[] = new int[]{R.anim.slide_in_top, R.anim.slide_out_bottom};
    int animreV[] = new int[]{R.anim.slide_in_bottom, R.anim.slide_out_top};
    int numActual = 0;
    int numDefective = 0, giatri_Input = 0;
    String vitri_bam = "";

    String id_line = "";
    int posss = -1;

    ArrayList<LineMaster> lineMaster;
    ArrayList<DataAllCountingMaster> dataAllCountingMasters;
    DecimalFormat formatter = new DecimalFormat("#,###,###");

    ProgressBar VerticalProgressBar;
    int intValue = 90;
    Handler handler = new Handler();

    BarcodeAdapter barcodeAdapter;
    ArrayList<BarcodeMaster> barcodeMasterArrayList;


    private UsbDevice device;
    private boolean tryedAutoConnect = false;
    private UsbManager usbManager;
    static BixolonLabelPrinter mBixolonLabelPrinter;
    private PendingIntent mPermissionIntent;
    private final String ACTION_USB_PERMISSION = "com.android.example.USB_PERMISSION";
    private String mConnectedDeviceName = null;
    private boolean mIsConnected;
    public ArrayList<BluetoothDevice> m_LeDevices;

    public static String line_no_d = "", line_noend = "";
    private int vitriluulai = -1;


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
        tv_product = findViewById(R.id.tv_product);
        tv_qc = findViewById(R.id.tv_qc);


//        findViewById(R.id.QCcheck).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                TextView tv = (TextView) tv_id.getCurrentView();
//                TextView tv1 = (TextView) tv_product.getCurrentView();
//                TextView tv2 = (TextView) tv_actual.getCurrentView();
//                TextView tv3 = (TextView) tv_qc.getCurrentView();
//                if (tv3.getText().toString().length()==0){
//                    AlerError.Baoloi("Please insert QC code.",CountActivity.this);
//                }else if (numActual!=0) {
//                    Intent intent = new Intent(CountActivity.this, QCCheckActivity.class);
//                    intent.putExtra("line",tv.getText().toString());
//                    intent.putExtra("product_nm", tv1.getText().toString());
//                    intent.putExtra("tv_Actual", tv2.getText().toString());
//                    intent.putExtra("tv_qc", tv3.getText().toString());
//                    startActivity(intent);
//                }else {
//                    AlerError.Baoloi("Actual = 0",CountActivity.this);
//                }
//            }
//        });


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
//        rl_defective_plus.setOnLongClickListener(new View.OnLongClickListener() {
//            @Override
//            public boolean onLongClick(View view) {
//                inputnum("Defective");
//                vitri_bam = "DPL";
//                return true;
//            }
//        });

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
//                rl_defective_plus.setEnabled(false);
//                vitri_bam = "DP";
//                sendDataCounting(1);
//                DelayClick(rl_defective_plus);

                TextView tv = (TextView) tv_id.getCurrentView();
                TextView tv1 = (TextView) tv_product.getCurrentView();
                TextView tv2 = (TextView) tv_actual.getCurrentView();
                TextView tv3 = (TextView) tv_qc.getCurrentView();
                TextView tv4 = (TextView) tv_defective.getCurrentView();
                if (tv3.getText().toString().length() == 0) {
                    AlerError.Baoloi("Please insert QC code.", CountActivity.this);
                } else if (numActual != 0) {
                    Intent intent = new Intent(CountActivity.this, QCCheckActivity.class);
                    intent.putExtra("line", tv.getText().toString());
                    intent.putExtra("product_nm", tv1.getText().toString());
                    intent.putExtra("tv_Actual", tv2.getText().toString());
                    intent.putExtra("tv_defective", tv4.getText().toString());
                    intent.putExtra("tv_qc", tv3.getText().toString());
                    startActivity(intent);
                } else {
                    AlerError.Baoloi("Actual = 0", CountActivity.this);
                }

            }
        });
//        rl_defective_sub.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                rl_defective_sub.setEnabled(false);
//                if (numDefective <= 0) {
//                    numDefective = 0;
//                    DelayClick(rl_defective_sub);
//                } else {
//                    vitri_bam = "DS";
//                    sendDataCounting(-1);
//                    DelayClick(rl_defective_sub);
//                }
//            }
//        });
        tv_id = (TextSwitcher) findViewById(R.id.ts_temperature);
        tv_id.setFactory(new TextViewFactory(R.style.TemperatureTextView, true));
        tv_actual.setFactory(new TextViewFactory(R.style.NumActualTextView, true));
        tv_defective.setFactory(new TextViewFactory(R.style.NumActualTextView, true));
        tvlocation.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        tv_time.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        tv_product.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        tv_taget.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        tv_qc.setFactory(new TextViewFactory(R.style.TemperatureTextView2, false));
        getDataline();

        mBixolonLabelPrinter = new BixolonLabelPrinter(this, mHandler, Looper.getMainLooper());

        final int ANDROID_NOUGAT = 24;
        if (Build.VERSION.SDK_INT >= ANDROID_NOUGAT) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

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
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
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
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
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
                line_noend = lineMaster.get(0).getLine_no();
                id_line = lineMaster.get(0).getId();
                loadDataAll(0);
                posss = 0;
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
                line_noend = lineMaster.get(i).getLine_no();
                id_line = lineMaster.get(i).getId();
                loadDataAll(i);
                posss = i;
                dialog.dismiss();
            }
        });
        builderSingle.show();
    }

    private void loadDataAll(int pos) {
        vitriluulai = pos;
        new loadDataAll().execute(Url + "plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(pos).getId());
        Log.d("loadDataAll", Url + "plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(pos).getId());
    }

    private class loadDataAll extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            dataAllCountingMasters = new ArrayList<>();
            String building_nm, floor_nm, target_qty, actual_qty, defect_qty, start_time, end_time, prd_no, qc_cd, line_no_d;
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
                prd_no = jsonObject1.getString("prd_no").replace("null", "");
                qc_cd = jsonObject1.getString("qc_cd").replace("null", "");
                line_no_d = jsonObject1.getString("line_no_d").replace("null", "");

                dataAllCountingMasters.add(new DataAllCountingMaster(building_nm, floor_nm, target_qty, actual_qty, defect_qty, start_time, end_time, prd_no, qc_cd, line_no_d));
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
        tv_qc.setText(dataAllCountingMasters.get(0).getQc_cd());
        line_no_d = dataAllCountingMasters.get(0).getLine_no_d();
        String time = dataAllCountingMasters.get(0).getStart_time().substring(0, 2) + ":" + dataAllCountingMasters.get(0).getStart_time().substring(2, 4)
                + " ~ " + dataAllCountingMasters.get(0).getEnd_time().substring(0, 2) + ":" + dataAllCountingMasters.get(0).getEnd_time().substring(2, 4);
        tv_time.setText(time);
        tv_product.setText(dataAllCountingMasters.get(0).getPrd_no());

        setAnimation(tv_actual, numActual + "");
        setAnimation(tv_defective, numDefective + "");

        setSeekbar(numActual);
    }

    private void setSeekbar(int numActual) {
        intValue = numActual * 100 / Integer.parseInt(dataAllCountingMasters.get(0).getTarget_qty());
        VerticalProgressBar.getProgressDrawable().setColorFilter(Color.CYAN, PorterDuff.Mode.SRC_IN);
        VerticalProgressBar.setProgress(intValue);
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
            setSeekbar(numActual);
        } else {
            tv.setInAnimation(CountActivity.this, animreV[0]);
            tv.setOutAnimation(CountActivity.this, animreV[1]);
            tv.setText(formatter.format(Integer.parseInt(value)));
            setSeekbar(numActual);
        }

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_print) {
            openPrint();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void openPrint() {


        new creat_Barcode_print().execute(Url + "Plan/PlanCrQrLot?id=" + lineMaster.get(posss).getId() +
                "&prd_no=" + dataAllCountingMasters.get(0).getPrd_no() + "&actual_qty=" + numActual);
        Log.d("creat_Barcode_print", Url + "Plan/PlanCrQrLot?id=" + lineMaster.get(posss).getId() +
                "&prd_no=" + dataAllCountingMasters.get(0).getPrd_no() + "&actual_qty=" + numActual);
    }


    private class creat_Barcode_print extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new print_barcode().execute(Url + "Plan/QRbarcodeCouting?id=" + lineMaster.get(posss).getId() +
                    "&prd_no=" + dataAllCountingMasters.get(0).getPrd_no());
            Log.d("print_barcode", Url + "Plan/QRbarcodeCouting?id" + lineMaster.get(posss).getId() +
                    "&prd_no=" + dataAllCountingMasters.get(0).getPrd_no());

        }

    }

    private class print_barcode extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            barcodeMasterArrayList = new ArrayList<>();
            String id, barcode;
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() == 0) {
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    id = jsonObject.getString("id").replace("null", "");
                    barcode = jsonObject.getString("prd_lot").replace("null", "");
                    barcodeMasterArrayList.add(new BarcodeMaster(id, barcode, i + 1 + "", true));
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }

            call_Popup_barcode();

        }

    }

    private void call_Popup_barcode() {
        Dialog dialog = new Dialog(CountActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        View dialogView = LayoutInflater.from(CountActivity.this).inflate(R.layout.popup_barcode, null);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        RecyclerView recyclerViewCL;
        recyclerViewCL = dialogView.findViewById(R.id.recyclerViewCL);

        recyclerViewCL.setHasFixedSize(true);
        RecyclerView.LayoutManager mLayoutManager;

        mLayoutManager = new LinearLayoutManager(CountActivity.this);
        barcodeAdapter = new BarcodeAdapter(barcodeMasterArrayList);
        recyclerViewCL.setLayoutManager(mLayoutManager);
        recyclerViewCL.setAdapter(barcodeAdapter);

        dialog.findViewById(R.id.xacnhanin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                in_tem();
            }
        });


        dialog.show();
    }

    private void in_tem() {
        final boolean hasResponse = false;
        final int responseLength = 0;

        for (int i = 0; i < barcodeMasterArrayList.size(); i++) {

            if (barcodeMasterArrayList.get(i).isChecked()) {
                String command =
                        "CB\n" +
                                "SS3\n" +    // Set Speed to 5 ips
                                "SD15\n" +    // Set Density level to 20
                                "SW320\n" +    // Set Label Width 320//800
                                //"SOT\n" +    // Set Printing Orientation from Top to Bottom
                                //"BD0,0,320,232,B,3\n" +   //Box thikness 5
                                //"T50,20,4,1,1,0,0,N,N,'" + "tieu de" + "'\n" +    //text 1. (4)Font - 15 pt
                                "B2110,30,Q,3,M,4,0,'" + barcodeMasterArrayList.get(i).getBarcode() + "'\n" +        //Qr .
                                "T20,150,1,1,1,0,0,N,N,'" + barcodeMasterArrayList.get(i).getBarcode() + "'\n" +        //text 3 (2)Font - 10 pt
                                "P1";
                if (command.length() > 0) {
                    CountActivity.mBixolonLabelPrinter.executeDirectIo(command, hasResponse, responseLength);
                }
            }

        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        VerticalProgressBar = (ProgressBar) findViewById(R.id.progressBar1);
        String namedevice = android.os.Build.MODEL;
        if (namedevice.equals("D1s")) {
            getMenuInflater().inflate(R.menu.menu_print, menu);
        }
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!tryedAutoConnect) {
            isConnectedPrinter();
        }
    }

    private void isConnectedPrinter() {
        try {
            usbManager = (UsbManager) getSystemService(Context.USB_SERVICE);
            HashMap<String, UsbDevice> deviceList = usbManager.getDeviceList();
            Iterator<UsbDevice> deviceIterator = deviceList.values().iterator();
            mPermissionIntent = PendingIntent.getBroadcast(this, 0,
                    new Intent(ACTION_USB_PERMISSION), PendingIntent.FLAG_UPDATE_CURRENT);
            while (deviceIterator.hasNext()) {
                UsbDevice d = deviceIterator.next();
                if (d != null) {
                    for (int i = 0; i < d.getInterfaceCount(); i++) {
                        UsbInterface usbInterface = d.getInterface(i);
                        if (usbInterface.getInterfaceClass() == 7 && usbInterface.getInterfaceSubclass() == 1 && usbInterface.getInterfaceProtocol() == 2) {
                            device = d;
                            break;
                        }
                    }
                }
            }

            IntentFilter filter = new IntentFilter(
                    ACTION_USB_PERMISSION);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_ATTACHED);
            filter.addAction(UsbManager.ACTION_USB_DEVICE_DETACHED);
            filter.addAction(BluetoothDevice.ACTION_ACL_DISCONNECTED);
            filter.addAction(BluetoothAdapter.ACTION_CONNECTION_STATE_CHANGED);
            registerReceiver(mUsbReceiver, filter);
            if (device != null) {
                usbManager.requestPermission(device, mPermissionIntent);
                tryedAutoConnect = true;
                Log.e("Exception", "Printer connected");
            } else {
                Log.e("Exception", "Printer not found");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private BroadcastReceiver mUsbReceiver = new BroadcastReceiver() {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(action)) {
                mBixolonLabelPrinter.connect();
                Toast.makeText(getApplicationContext(), "Found USB device", Toast.LENGTH_SHORT).show();
            } else if (UsbManager.ACTION_USB_DEVICE_DETACHED.equals(action)) {
                mBixolonLabelPrinter.disconnect();
                Toast.makeText(getApplicationContext(), "USB device removed", Toast.LENGTH_SHORT).show();
            } else if (ACTION_USB_PERMISSION.equals(action)) {
                mBixolonLabelPrinter.connect(device);
                device = null;
            }

        }
    };

//    private final void setStatus(CharSequence subtitle) {
//        final ActionBar actionBar = getActionBar();
//        actionBar.setSubtitle(subtitle);
//    }

    @SuppressLint("HandlerLeak")
    private final Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case BixolonLabelPrinter.MESSAGE_STATE_CHANGE:
                    switch (msg.arg1) {
                        case BixolonLabelPrinter.STATE_CONNECTED:
                            //setStatus(getString(R.string.title_connected_to, mConnectedDeviceName));
                            //mListView.setEnabled(true);
                            //mIsConnected = true;
                            invalidateOptionsMenu();
                            CountActivity.mBixolonLabelPrinter.setOrientation(BixolonLabelPrinter.ORIENTATION_TOP_TO_BOTTOM);
                            break;

                        case BixolonLabelPrinter.STATE_CONNECTING:
                            // setStatus(getString(R.string.title_connecting));
                            break;

                        case BixolonLabelPrinter.STATE_NONE:
                            // setStatus(getString(R.string.title_not_connected));
                            //mListView.setEnabled(false);
                            //mIsConnected = false;
                            invalidateOptionsMenu();
                            break;
                    }
                    break;

                case BixolonLabelPrinter.MESSAGE_READ:
                    // CountActivity.this.dispatchMessage(msg);
                    break;

                case BixolonLabelPrinter.MESSAGE_DEVICE_NAME:
                    mConnectedDeviceName = msg.getData().getString(BixolonLabelPrinter.DEVICE_NAME);
                    Toast.makeText(getApplicationContext(), mConnectedDeviceName, Toast.LENGTH_LONG).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_TOAST:
                    //mListView.setEnabled(false);
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.TOAST), Toast.LENGTH_SHORT).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_LOG:
                    Toast.makeText(getApplicationContext(), msg.getData().getString(BixolonLabelPrinter.LOG), Toast.LENGTH_SHORT).show();
                    break;

                case BixolonLabelPrinter.MESSAGE_BLUETOOTH_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No paired device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showBluetoothDialog(CountActivity.this, (Set<BluetoothDevice>) msg.obj);
                    }
                    break;

                case BixolonLabelPrinter.MESSAGE_USB_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No connected device", Toast.LENGTH_SHORT).show();
                    } else {
                        DialogManager.showUsbDialog(CountActivity.this, (Set<UsbDevice>) msg.obj, mUsbReceiver);
                    }
                    break;

                case BixolonLabelPrinter.MESSAGE_NETWORK_DEVICE_SET:
                    if (msg.obj == null) {
                        Toast.makeText(getApplicationContext(), "No connectable device", Toast.LENGTH_SHORT).show();
                    }
                    DialogManager.showNetworkDialog(CountActivity.this, (Set<String>) msg.obj);
                    break;

            }
        }
    };
//    private final void setStatus(CharSequence subtitle) {
//        final ActionBar actionBar = getActionBar();
//        actionBar.setSubtitle(subtitle);
//    }


    @Override
    protected void onPostResume() {
        if (vitriluulai!=-1) {
            new loadDataAll().execute(Url + "plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(vitriluulai).getId());
            Log.d("loadDataAll", Url + "plan/getDataLineInfoTodayDetail?id=" + lineMaster.get(vitriluulai).getId());
        }
        super.onPostResume();
    }
}
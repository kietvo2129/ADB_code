package com.autonsi.databoard.Receving.ReceivingOder;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Receving.ReceivingOder.OderInformation.OrderInforActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class ReceivingActivity extends AppCompatActivity {
    TabLayout tabLayout;
    String Url = com.autonsi.databoard.Url.webUrl;

    ArrayList<ReceivingMaster> receivingMasterArrayList;
    ArrayList<ReceivingMaster> receivingMasterArrayListnew;

    RecyclerView rycview;
    ReceivingAdapter receivingAdapter;
    Dialog filterDialog;
    TextView orderInfor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_receiving);
        setTitle("Receiving");
        tabLayout = findViewById(R.id.tab_item);
        rycview = findViewById(R.id.rycview);
        orderInfor= findViewById(R.id.orderInfor);
        getMaterials();
        gettabs();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                timkiemtheotype(tab.getText().toString());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        orderInfor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ReceivingActivity.this, OrderInforActivity.class);
                startActivity(intent);
            }
        });
    }


    private void timkiemtheotype(String text) {
        ArrayList<ReceivingMaster> filteredList = new ArrayList<>();
        for (ReceivingMaster item : receivingMasterArrayListnew) {
            if (item.getType_name().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        receivingMasterArrayList = filteredList;
        //if (filteredList.size()!=0) {
            receivingAdapter.filterList(filteredList);
       // }
    }

    private void getMaterials() {
        new getMaterials().execute(Url + "WMSConfiguration/GetMaterials");

    }

    private void gettabs() {
        new gettabs().execute(Url + "Common/GetMaterialTypes");
    }

    private class gettabs extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONArray jsonArray = new JSONArray(s);
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", ReceivingActivity.this);
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        tabLayout.addTab(tabLayout.newTab().setText(jsonObject.getString("Name")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ReceivingActivity.this);
            }
        }

    }

    private class getMaterials extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            receivingMasterArrayList = new ArrayList<>();
            receivingMasterArrayListnew = new ArrayList<>();
            try {

                JSONArray jsonArray = new JSONArray(s);
                String mtid, mt_cd, mt_nm, mt_type, unit_cd, bundle_qty, sp_cd, qc_cd, qc_range, unit_price, bundle_unit_price, currency_code, image, re_mark, use_yn, expiry_days, sp_nm, sp_type, bundle_price, name, currency_name, type_name, unit_name;
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", ReceivingActivity.this);
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mtid = jsonObject.getString("mtid").replace("null", "");
                        mt_cd = jsonObject.getString("mt_cd").replace("null", "");
                        mt_nm = jsonObject.getString("mt_nm").replace("null", "");
                        mt_type = jsonObject.getString("mt_type").replace("null", "");
                        unit_cd = jsonObject.getString("unit_cd").replace("null", "");
                        bundle_qty = jsonObject.getString("bundle_qty").replace("null", "");
                        sp_cd = jsonObject.getString("sp_cd").replace("null", "");
                        qc_cd = jsonObject.getString("qc_cd").replace("null", "");
                        qc_range = jsonObject.getString("qc_range").replace("null", "");
                        unit_price = jsonObject.getString("unit_price").replace("null", "");
                        bundle_unit_price = jsonObject.getString("bundle_unit_price").replace("null", "");
                        currency_code = jsonObject.getString("currency_code").replace("null", "");
                        image = jsonObject.getString("image").replace("null", "");
                        re_mark = jsonObject.getString("re_mark").replace("null", "");
                        use_yn = jsonObject.getString("use_yn").replace("null", "");
                        expiry_days = jsonObject.getString("expiry_days").replace("null", "");
                        sp_nm = jsonObject.getString("sp_nm").replace("null", "");
                        sp_type = jsonObject.getString("sp_type").replace("null", "");
                        bundle_price = jsonObject.getString("bundle_price").replace("null", "");
                        name = jsonObject.getString("name").replace("null", "");
                        currency_name = jsonObject.getString("currency_name").replace("null", "");
                        type_name = jsonObject.getString("type_name").replace("null", "");
                        unit_name = jsonObject.getString("unit_name").replace("null", "");
                        receivingMasterArrayList.add(new ReceivingMaster(mtid, mt_cd, mt_nm, mt_type, unit_cd, bundle_qty, sp_cd, qc_cd, qc_range, unit_price, bundle_unit_price
                                , currency_code, image, re_mark, use_yn, expiry_days, sp_nm, sp_type, bundle_price, name, currency_name, type_name, unit_name));
                    }
                }
                receivingMasterArrayListnew = receivingMasterArrayList;
                buildRecyclerView();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ReceivingActivity.this);
            }
        }
    }

    private void buildRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager;
        rycview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ReceivingActivity.this);
        receivingAdapter = new ReceivingAdapter(receivingMasterArrayList);
        rycview.setLayoutManager(mLayoutManager);
        rycview.setAdapter(receivingAdapter);

        receivingAdapter.setOnItemClickListener(new ReceivingAdapter.OnItemClickListener() {
            @Override
            public void onwarningClick(int position) {
                popupAction(position);
            }
        });

    }

    private void popupAction(int position) {
        filterDialog = new Dialog(ReceivingActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        filterDialog.setContentView(R.layout.popup_add_materials);
        filterDialog.setCancelable(false);
        filterDialog.getWindow().setLayout(getWidth(ReceivingActivity.this), ((getHight(ReceivingActivity.this) / 100) * 80));
        filterDialog.getWindow().setGravity(Gravity.BOTTOM);
        filterDialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.cancel();
            }
        });
        TextView numinput;
        ImageView im_plus,im_sub;
        FloatingActionButton fab;

        TextView tvId,tvNm,tvLocation,tvQC,tvQCrange,tvUnitPrice,tvBundleQty,tvBundlePrice,tvUnit,tvexpiry,tvdesc;
        tvId = filterDialog.findViewById(R.id.tvId);
        tvNm = filterDialog.findViewById(R.id.tvNm);
        tvLocation = filterDialog.findViewById(R.id.tvLocation);
        tvQC = filterDialog.findViewById(R.id.tvQC);
        tvQCrange = filterDialog.findViewById(R.id.tvQCrange);
        tvUnitPrice = filterDialog.findViewById(R.id.tvUnitPrice);
        tvBundleQty = filterDialog.findViewById(R.id.tvBundleQty);
        tvBundlePrice = filterDialog.findViewById(R.id.tvBundlePrice);
        tvUnit = filterDialog.findViewById(R.id.tvUnit);
        tvexpiry = filterDialog.findViewById(R.id.tvexpiry);
        tvdesc = filterDialog.findViewById(R.id.tvdesc);
        tvId.setText(receivingMasterArrayList.get(position).getType_name());
        tvNm.setText(receivingMasterArrayList.get(position).getMt_cd());
        tvLocation.setText(receivingMasterArrayList.get(position).getMt_nm());

        tvQC.setText(receivingMasterArrayList.get(position).getQc_cd());
        tvQCrange.setText(receivingMasterArrayList.get(position).getQc_range()+" %");
        tvUnitPrice.setText(receivingMasterArrayList.get(position).getUnit_price()+" "+receivingMasterArrayList.get(position).getCurrency_name());

        tvUnit.setText(receivingMasterArrayList.get(position).getUnit_name());
        tvBundleQty.setText(receivingMasterArrayList.get(position).getBundle_qty());
        tvBundlePrice.setText(receivingMasterArrayList.get(position).getBundle_price()+" "+receivingMasterArrayList.get(position).getCurrency_name());

        tvexpiry.setText(receivingMasterArrayList.get(position).getExpiry_days()+ " Day");
        tvdesc.setText(receivingMasterArrayList.get(position).getRe_mark());


        fab = filterDialog.findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.toolbar_subtitle_color)));
        fab.setBackgroundColor(Color.parseColor("#B1B0B0"));
        im_plus = filterDialog.findViewById(R.id.im_plus);
        im_sub = filterDialog.findViewById(R.id.im_sub);
        im_sub.setBackgroundResource(R.drawable.ic_remove);
        im_plus.setBackgroundResource(R.drawable.ic_plus);

        numinput = filterDialog.findViewById(R.id.numinput);
        numinput.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inputnum(numinput);
            }
        });

        numinput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                int num = Integer.parseInt(numinput.getText().toString());
                if (num<1){
                    im_sub.setBackgroundResource(R.drawable.ic_remove);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#B1B0B0")));
                }else {
                    im_sub.setBackgroundResource(R.drawable.ic_remove_blue);
                    fab.setBackgroundTintList(ColorStateList.valueOf(Color.parseColor("#009688")));

                }

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        im_plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(numinput.getText().toString());
                numinput.setText(num+1+"");
            }
        });
        im_sub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(numinput.getText().toString());
                if (num<=0){
                    return;
                }else {
                    numinput.setText(num - 1 + "");
                }
            }
        });
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int num = Integer.parseInt(numinput.getText().toString());
                if (num<=0){
                    return;
                }else {

                    receivingMaterials(numinput.getText().toString(),position);

                }
            }
        });


        filterDialog.show();
    }

    private void receivingMaterials(String input,int position) {
        new receivingMaterials().execute(Url + "WMSReceiving/InsertOrderMaterial?id=" +
                receivingMasterArrayList.get(position).getMtid() +
                "&mtCode=" +
                receivingMasterArrayList.get(position).getMt_cd() +
                "&mtName=" +
                receivingMasterArrayList.get(position).getMt_nm() +
                "&orderBundleQty=" +
                input +
                "&remark=" +
                "");
    }

    public static int getWidth(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }
    public static int getHight(Context context) {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        WindowManager windowmanager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        windowmanager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }
    private void inputnum(TextView numinput) {
        AlertDialog.Builder builder = new AlertDialog.Builder(ReceivingActivity.this);
        View viewInflated = LayoutInflater.from(ReceivingActivity.this).inflate(R.layout.number_input_layout, null);

        final EditText input = (EditText) viewInflated.findViewById(R.id.input);
        builder.setView(viewInflated);
        TextView tv_title = viewInflated.findViewById(R.id.tv_title);
        tv_title.setText("Input number Receiving");

        input.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (input.getText().length()==0){
                    numinput.setText("0");
                }else {
                    numinput.setText(input.getText().toString().trim());
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    private class receivingMaterials extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")){
                    AlerError.Baoloi(jsonObject.getString("message"), ReceivingActivity.this);
                }else {
                    Toast.makeText(ReceivingActivity.this, "Done", Toast.LENGTH_SHORT).show();
                    filterDialog.dismiss();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ReceivingActivity.this);
            }
        }
    }

}
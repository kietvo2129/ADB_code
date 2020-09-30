package com.autonsi.databoard.Ship.Confirm;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.DigitalData.IssuesList.IssuesActivity;
import com.autonsi.databoard.Ship.Confirm.detail.ConfirmDetailAdapter;
import com.autonsi.databoard.Ship.Confirm.detail.ConfirmDetailChildAdapter;
import com.autonsi.databoard.Ship.Confirm.detail.ConfirmdetailChildMaster;
import com.autonsi.databoard.Ship.Confirm.detail.ConfirmdetailMaster;
import com.autonsi.databoard.VolleyMultipartRequest;
import com.google.android.material.tabs.TabLayout;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ConfirmActivity extends AppCompatActivity {
    TabLayout tabLayout;
    String Url = com.autonsi.databoard.Url.webUrl;
    RecyclerView rycview, rycviewdetail, rycviewchilddetail;

    ArrayList<ConfirmMaster> confirmMasterArrayList;
    ArrayList<ConfirmMaster> confirmMasterArrayListnew;

    TextView tvback;
    TextView textview;
    TextView tvnext;
    ConfirmAdapter confirmAdapter;
    Dialog filterDialog;

    ArrayList<ConfirmdetailMaster> confirmdetailMasterArrayList;
    ArrayList<ConfirmdetailChildMaster> confirmdetailChildArrayList;

    Bitmap fileHinh;
    String vitriConfirm = "";
    int vitri = -1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm);
        setTitle("Confirm");
        tabLayout = findViewById(R.id.tab_item);
        tabLayout.addTab(tabLayout.newTab().setText("ALL"));
        rycview = findViewById(R.id.rycview);
        getMaterialsConfirm();
        gettabs();
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                if (tab.getText().toString().equals("ALL")) {
                    timkiemtheotype("");
                } else {
                    timkiemtheotype(tab.getText().toString());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }

    private void getMaterialsConfirm() {
        new getMaterialsConfirm().execute(Url + "WMSShipping/getConFirm");
    }

    private class getMaterialsConfirm extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            confirmMasterArrayList = new ArrayList<>();
            confirmMasterArrayListnew = new ArrayList<>();
            try {

                JSONArray jsonArray = new JSONArray(s);
                String mrid, mr_no, mr_sts_cd, lct_nm, mt_qty, worker_id, manager_id, req_rec_dt;
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", ConfirmActivity.this);
                    //return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mrid = jsonObject.getString("mrid").replace("null", "");
                        mr_no = jsonObject.getString("mr_no").replace("null", "");
                        mr_sts_cd = jsonObject.getString("mr_sts_cd").replace("null", "");
                        lct_nm = jsonObject.getString("lct_nm").replace("null", "");
                        mt_qty = jsonObject.getString("mt_qty").replace("null", "");
                        worker_id = jsonObject.getString("worker_id").replace("null", "");
                        manager_id = jsonObject.getString("manager_id").replace("null", "");
                        req_rec_dt = jsonObject.getString("req_rec_dt").replace("null", "");
                        confirmMasterArrayList.add(new ConfirmMaster(mrid, mr_no, mr_sts_cd, lct_nm, mt_qty, worker_id, manager_id
                                , req_rec_dt));
                    }
                }
                confirmMasterArrayListnew = confirmMasterArrayList;
                buildRecyclerView();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
            }
        }
    }

    private void buildRecyclerView() {
        RecyclerView.LayoutManager mLayoutManager;
        rycview.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ConfirmActivity.this);
        confirmAdapter = new ConfirmAdapter(confirmMasterArrayList);
        rycview.setLayoutManager(mLayoutManager);
        rycview.setAdapter(confirmAdapter);

        confirmAdapter.setOnItemClickListener(new ConfirmAdapter.OnItemClickListener() {
            @Override
            public void onwarningClick(int position) {
                popupAction(position);
                vitriConfirm = confirmMasterArrayList.get(position).getMrid();
            }
        });

    }

    private void gettabs() {
        new gettabs().execute(Url + "WMSShipping/getWHlocation");
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
                    AlerError.Baoloi("not found data", ConfirmActivity.this);
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        tabLayout.addTab(tabLayout.newTab().setText(jsonObject.getString("lct_nm")));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
            }
        }

    }

    private void timkiemtheotype(String text) {
        ArrayList<ConfirmMaster> filteredList = new ArrayList<>();
        for (ConfirmMaster item : confirmMasterArrayListnew) {
            if (item.getLct_nm().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        confirmMasterArrayList = filteredList;
        //if (filteredList.size()!=0) {
        confirmAdapter.filterList(filteredList);
        //}
    }

    private void popupAction(int position) {
        filterDialog = new Dialog(ConfirmActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        filterDialog.setContentView(R.layout.popup_confirm_detail);
        filterDialog.setCancelable(false);
        filterDialog.getWindow().setLayout(getWidth(ConfirmActivity.this), ((getHight(ConfirmActivity.this) / 100) * 90));
        filterDialog.getWindow().setGravity(Gravity.BOTTOM);

        filterDialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                filterDialog.cancel();
            }
        });
        rycviewdetail = filterDialog.findViewById(R.id.rycview);

        getdatadetail(position);

        filterDialog.findViewById(R.id.xacnhan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                question_take_picture( position);

            }
        });

        filterDialog.show();
    }

    private void question_take_picture(int position) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Confirm");
        builder.setMessage("Do you want to take pictures?");

        builder.setPositiveButton("YES", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                tack_picture();
                dialog.dismiss();
            }
        });

        builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                confirm();
                dialog.dismiss();
            }
        });

        AlertDialog alert = builder.create();
        alert.show();

    }

    private void tack_picture() {
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivityForResult(intent, 0);
    }

    private void confirm() {
        String UPLOAD_URL = Url + "WMSShipping/updateConfirmAPI";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            // Json như binh thuong
                            JSONObject obj = new JSONObject(new String(response.data));

                            String result = obj.getString("result");
                          /*  String path = obj.getString("path");
                            String duoihinh = obj.getString("duoihinh");*/
                            //ImageUrl = "http://" + path;

                            if (result.equals("true")) {
                                Toast.makeText(ConfirmActivity.this, "Confirm success", Toast.LENGTH_SHORT).show();
                                getMaterialsConfirm();
                                filterDialog.cancel();

                            }else {
                                Toast.makeText(ConfirmActivity.this, "Can not Confirm", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("Json", obj.toString()/*+ "\n" + path + "\n" + duoihinh*/);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
                    }
                }) {

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("model", vitriConfirm + "");
                return params;
            }

        };

        //adding the request to volley
        Volley.newRequestQueue(ConfirmActivity.this).add(volleyMultipartRequest);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
            if (data != null && resultCode != Activity.RESULT_CANCELED) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int nh = (int) (photo.getHeight() * (480.0 / photo.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(photo, 480, nh, true);
                fileHinh = scaled;
                uploadBitmap(fileHinh);
                confirm();
            } else {
            }
        }
    }

    //hinh anh
    private void uploadBitmap(final Bitmap bitmap) {

        String UPLOAD_URL = Url + "WMSShipping/UploadImgConfirm";
        VolleyMultipartRequest volleyMultipartRequest = new VolleyMultipartRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<NetworkResponse>() {
                    @Override
                    public void onResponse(NetworkResponse response) {
                        try {

                            // Json như binh thuong
                            JSONObject obj = new JSONObject(new String(response.data));

                            String result = obj.getString("result");
                          /*  String path = obj.getString("path");
                            String duoihinh = obj.getString("duoihinh");*/
                            //ImageUrl = "http://" + path;

                            if (result.equals("true")) {
                                Toast.makeText(ConfirmActivity.this, "Upload photo success", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(ConfirmActivity.this, "Can not Upload photo", Toast.LENGTH_SHORT).show();
                            }
                            Log.d("Json", obj.toString()/*+ "\n" + path + "\n" + duoihinh*/);


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.toString(), Toast.LENGTH_SHORT).show();
                        AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
                    }
                }) {

            /*
             * If you want to add more parameters with the image
             * you can do it here
             * here we have only one parameter with the image
             * which is tags
             * */

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> params = new HashMap<>();
                params.put("pdid", vitriConfirm + "");
                return params;
            }

            /*
             * Here we are passing image by renaming it with a unique name
             * */

            @Override
            protected Map<String, DataPart> getByteData() {
                Map<String, DataPart> params = new HashMap<>();
                SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyyMMddHHmmss");
                Date myDate = new Date();
                String filename = timeStampFormat.format(myDate);
                //long imagename = System.currentTimeMillis();
                params.put("logo", new DataPart(filename + ".png", getFileDataFromDrawable(bitmap)));

                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(ConfirmActivity.this).add(volleyMultipartRequest);
    }

    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


    private void getdatadetail(int position) {
        new getdatadetail().execute(Url + "WMSShipping/GetConFirmDetail?id=" + confirmMasterArrayList.get(position).getMrid());
    }

    private class getdatadetail extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            confirmdetailMasterArrayList = new ArrayList<>();
            try {

                JSONArray jsonArray = new JSONArray(s);
                String mr_no, mr_sts_cd, mrdid, mt_no, mrd_no, req_bundle_qty1, req_qty1, req_bundle_qty, req_qty, mt_nm;
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", ConfirmActivity.this);
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mr_no = jsonObject.getString("mr_no").replace("null", "");
                        mr_sts_cd = jsonObject.getString("mr_sts_cd").replace("null", "");
                        mrdid = jsonObject.getString("mrdid").replace("null", "");
                        mt_no = jsonObject.getString("mt_no").replace("null", "");
                        mrd_no = jsonObject.getString("mrd_no").replace("null", "");
                        req_bundle_qty1 = jsonObject.getString("req_bundle_qty1").replace("null", "");
                        req_qty1 = jsonObject.getString("req_qty1").replace("null", "");
                        req_bundle_qty = jsonObject.getString("req_bundle_qty").replace("null", "");
                        req_qty = jsonObject.getString("req_qty").replace("null", "");
                        mt_nm = jsonObject.getString("mt_nm").replace("null", "");
                        confirmdetailMasterArrayList.add(new ConfirmdetailMaster(mr_no, mr_sts_cd, mrdid, mt_no, mrd_no, req_bundle_qty1, req_qty1, req_bundle_qty, req_qty, mt_nm));
                    }
                    buildRecyclerViewdetail();
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
            }
        }
    }

    private void buildRecyclerViewdetail() {
        RecyclerView.LayoutManager mLayoutManager;
        ConfirmDetailAdapter confirmDetailAdapter;
        rycviewdetail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ConfirmActivity.this);
        confirmDetailAdapter = new ConfirmDetailAdapter(confirmdetailMasterArrayList);
        rycviewdetail.setLayoutManager(mLayoutManager);
        rycviewdetail.setAdapter(confirmDetailAdapter);

        confirmDetailAdapter.setOnItemClickListener(new ConfirmDetailAdapter.OnItemClickListener() {
            @Override
            public void onwarningClick(int position, RecyclerView recyclerView, TextView a, TextView b, TextView c) {

                tvback = a;
                textview =b;
                tvnext = c;
                getDetailChild(position);
                rycviewchilddetail = recyclerView;
                vitri = position;

            }
        });

    }

    private void getDetailChild(int position) {
        new getdatadetailChild().execute(Url + "WMSShipping/GetConFirmListLot?lot_table_id=" + confirmdetailMasterArrayList.get(position).getMrdid()+"&page=1&rows=20&sidx=&sord=asc&_search=false");
    }

    private class getdatadetailChild extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            confirmdetailChildArrayList = new ArrayList<>();
            int total = 0,page= 0,records= 0;
            try {

                JSONObject jsonObjectmain = new JSONObject(s);

                total = jsonObjectmain.getInt("total");
                page = jsonObjectmain.getInt("page");
                records = jsonObjectmain.getInt("records");

                JSONArray jsonArray = jsonObjectmain.getJSONArray("rows");
                String mt_lot_cd, lot_qty, expiry_date;
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", ConfirmActivity.this);
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mt_lot_cd = jsonObject.getString("mt_lot_cd").replace("null", "");
                        lot_qty = jsonObject.getString("lot_qty").replace("null", "");
                        expiry_date = jsonObject.getString("expiry_date").replace("null", "");
                        confirmdetailChildArrayList.add(new ConfirmdetailChildMaster(mt_lot_cd, lot_qty, expiry_date));
                    }
                    buildRecyclerViewdetailChild(total,page,records);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
            }
        }
    }

    private void buildRecyclerViewdetailChild(int total,int page, int records) {
        RecyclerView.LayoutManager mLayoutManager;
        ConfirmDetailChildAdapter confirmDetailChildAdapter;
        rycviewchilddetail.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(ConfirmActivity.this);
        confirmDetailChildAdapter = new ConfirmDetailChildAdapter(confirmdetailChildArrayList);
        rycviewchilddetail.setLayoutManager(mLayoutManager);
        rycviewchilddetail.setAdapter(confirmDetailChildAdapter);

//        tvback.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                if (page -1 <=0){
//                    return;
//                }else {
//                  nextbage(page-1,vitri);
//                }
//            }
//        });
        tvnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (page + 1 > total){
                    return;
                }else {
                    nextbage(page + 1,vitri);
                }
            }
        });
        textview.setText("Page: " + page + "/" + total + " \nView " + (20*page - 19) + " - "+ 20*page + " of "+ records);
    }

    private void nextbage(int page,int position) {
        new getdatadetailChildnextbage().execute(Url + "WMSShipping/GetConFirmListLot?lot_table_id=" + confirmdetailMasterArrayList.get(position).getMrdid()+"&page=" +
                page + "&rows=20&sidx=&sord=asc&_search=false");
    }

    private class getdatadetailChildnextbage extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            int total = 0,page= 0,records= 0;
            try {

                JSONObject jsonObjectmain = new JSONObject(s);

                total = jsonObjectmain.getInt("total");
                page = jsonObjectmain.getInt("page");
                records = jsonObjectmain.getInt("records");

                JSONArray jsonArray = jsonObjectmain.getJSONArray("rows");
                String mt_lot_cd, lot_qty, expiry_date;
                if (jsonArray.length() == 0) {
                    AlerError.Baoloi("not found data", ConfirmActivity.this);
                    return;
                } else {
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject jsonObject = jsonArray.getJSONObject(i);
                        mt_lot_cd = jsonObject.getString("mt_lot_cd").replace("null", "");
                        lot_qty = jsonObject.getString("lot_qty").replace("null", "");
                        expiry_date = jsonObject.getString("expiry_date").replace("null", "");
                        confirmdetailChildArrayList.add(new ConfirmdetailChildMaster(mt_lot_cd, lot_qty, expiry_date));
                    }
                    buildRecyclerViewdetailChild(total,page,records);
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ConfirmActivity.this);
            }
        }
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
}
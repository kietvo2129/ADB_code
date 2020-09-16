package com.autonsi.databoard.DigitalData.IssuesList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.github.ybq.android.spinkit.style.Circle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;
import com.autonsi.databoard.AlerError.AlerError;
import com.quickblox.sample.videochat.java.R;
import com.autonsi.databoard.VolleyMultipartRequest;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class IssuesActivity extends AppCompatActivity implements Toolbar.OnMenuItemClickListener {
    private ArrayList<IssuesMater> issuesMaterArrayList;
    private ArrayList<IssuesMater> issuesMaterArrayListnew;
    private RecyclerView mRecyclerView;
    private IssuesAdapter issuesAdapter;
    String Url = com.autonsi.databoard.Url.webUrl;
    private RecyclerView.LayoutManager mLayoutManager;
    ProgressBar progressBar;
    Button buttontoggle;
    Dialog dialog;
    Dialog dialogaction;
    Button buttontoggleaction;
    ImageView impicture;
    int vitri = 0;

    String issue, time_sending, info_warning, info_finish, time_finish;
    int pos = -1;
    private Bitmap fileHinh;

    SearchView searchView;
    private String oftion = "Code";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_issues);
        mRecyclerView = findViewById(R.id.recyclerView);
        progressBar = findViewById(R.id.Spinkit);
        setTitle("Issues List");
        progressBar.setVisibility(View.VISIBLE);
        Circle circle = new Circle();
        progressBar.setIndeterminateDrawable(circle);
        loaddata();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.inflateMenu(R.menu.search_func); // Menu Search
        toolbar.setOnMenuItemClickListener(this);
        searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                filter(s, oftion);
                searchView.clearFocus();
//                sensor_ifo.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                filter(s, oftion);
//                sensor_ifo.setVisibility(View.GONE);
                return true;
            }
        });
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //mSelectedItem = -1;
//                Intent intent = new Intent(SensorCheckActivity.this, MainActivity.class);
//                startActivity(intent);
                finish();
            }
        });

    }

    private void filter(String text, String oftion) {
        ArrayList<IssuesMater> filteredList = new ArrayList<>();
        for (IssuesMater item : issuesMaterArrayListnew) {
            if (oftion.equals("Code")) {
                if (item.getSs_no().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            } else if (oftion.equals("Name")) {
                if (item.getSs_nm().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            } else if (oftion.equals("Floor")) {
                if (item.getFloor_name().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            } else if (oftion.equals("Building")) {
                if (item.getBuilding_name().toLowerCase().contains(text.toLowerCase())) {
                    filteredList.add(item);
                }
            }
        }

        issuesMaterArrayList = filteredList;
        issuesAdapter.filterList(filteredList);
    }

    private void loaddata() {
        new readissues().execute(Url + "Monitor/GetIssuesHisApp");
        Log.d("readhistory", Url + "Monitor/GetIssuesHisApp");
    }

    private class readissues extends AsyncTask<String, Integer, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            issuesMaterArrayList = new ArrayList<>();
            issuesMaterArrayListnew = new ArrayList<>();
            String ss_no, ss_nm, current_temp, current_press, current_pow, current_humi,
                    temp_issue, humi_issue, press_issue, pow_issue, time_update,
                    temp_issue_nm, humi_issue_nm, press_issue_nm, pow_issue_nm,
                    min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow, building_name, floor_name, info_warning, time_finish, sts_send, id, img_issue;

            try {
                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray == null || jsonArray.length() == 0) {
                    AlerError.Baoloi("No data!", IssuesActivity.this);
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {

                    JSONObject objectRow = jsonArray.getJSONObject(i);

                    ss_no = objectRow.getString("ss_no").replace("null", "");
                    ss_nm = objectRow.getString("ss_nm").replace("null", "");
                    current_temp = objectRow.getString("current_temp").replace("null", "0").replace("-999", "");
                    current_press = objectRow.getString("current_press").replace("null", "0").replace("-999", "");
                    current_pow = objectRow.getString("current_pow").replace("null", "0").replace("-999", "");
                    current_humi = objectRow.getString("current_humi").replace("null", "0").replace("-999", "");
                    temp_issue = objectRow.getString("temp_issue").replace("null", "");
                    humi_issue = objectRow.getString("humi_issue").replace("null", "");
                    press_issue = objectRow.getString("press_issue").replace("null", "");
                    pow_issue = objectRow.getString("pow_issue").replace("null", "");
                    time_update = objectRow.getString("time_update").replace("null", "");

                    temp_issue_nm = objectRow.getString("temp_issue_nm").replace("[", "").replace("]", "").replace("\"", "");
                    humi_issue_nm = objectRow.getString("humi_issue_nm").replace("[", "").replace("]", "").replace("\"", "");
                    press_issue_nm = objectRow.getString("press_issue_nm").replace("[", "").replace("]", "").replace("\"", "");
                    pow_issue_nm = objectRow.getString("pow_issue_nm").replace("[", "").replace("]", "").replace("\"", "");

                    min_temp = objectRow.getString("min_temp").replace("null", "0");
                    max_temp = objectRow.getString("max_temp").replace("null", "0");
                    min_press = objectRow.getString("min_press").replace("null", "0");
                    max_press = objectRow.getString("max_press").replace("null", "0");
                    min_humi = objectRow.getString("min_humi").replace("null", "0");
                    max_humi = objectRow.getString("max_humi").replace("null", "0");
                    min_pow = objectRow.getString("min_pow").replace("null", "0");
                    max_pow = objectRow.getString("max_pow").replace("null", "0");
                    building_name = objectRow.getString("building_name").replace("null", "").replace("[", "").replace("]", "").replace("\"", "");
                    floor_name = objectRow.getString("floor_name").replace("null", "").replace("[", "").replace("]", "").replace("\"", "");
                    info_warning = objectRow.getString("info_warning");
                    time_finish = objectRow.getString("time_finish");
                    sts_send = objectRow.getString("sts_send").replace("[", "").replace("]", "").replace("\"", "");
                    id = objectRow.getString("id");
                    img_issue = objectRow.getString("img_issue").replace("null", "");

                    issuesMaterArrayList.add(new IssuesMater(ss_no, ss_nm, current_temp, current_press, current_pow, current_humi,
                            temp_issue, humi_issue, press_issue, pow_issue, time_update,
                            temp_issue_nm, humi_issue_nm, press_issue_nm, pow_issue_nm,
                            min_temp, max_temp, min_press, max_press, min_humi, max_humi, min_pow, max_pow, building_name, floor_name,
                            info_warning, time_finish, sts_send, id, img_issue));
                    //mHomeListnew = mHomeList;
                }
                issuesMaterArrayListnew = issuesMaterArrayList;
                progressBar.setVisibility(View.GONE);
                buildRecyclerView();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
            }
        }

    }

    private void buildRecyclerView() {
        mRecyclerView.setHasFixedSize(true);
        mLayoutManager = new LinearLayoutManager(IssuesActivity.this);
        issuesAdapter = new IssuesAdapter(issuesMaterArrayList);
        mRecyclerView.setLayoutManager(mLayoutManager);
        mRecyclerView.setAdapter(issuesAdapter);

        mRecyclerView.getLayoutManager().scrollToPosition(vitri);

        issuesAdapter.setOnItemClickListener(new IssuesAdapter.OnItemClickListener() {
            @Override
            public void onwarningClick(int position) {

                if (issuesMaterArrayList.get(position).getInfo_warning().equals("null")) {
                    popupwarning(position);
                    vitri = position;
                } else {
                    vitri = position;
                    pos = position;
                    new readAction().execute(Url + "Monitor/getinfoissue?id=" + issuesMaterArrayList.get(position).getId());
                    Log.d("readAction", Url + "Monitor/getinfoissue?id=" + issuesMaterArrayList.get(position).getId());
                }
            }

            @Override
            public void onsendClick(int position) {

                if (issuesMaterArrayList.get(position).getSts_send().equals("Pending")) {
                    popupAction(position);
                    vitri = position;

                }

            }
        });

    }

    private class readAction extends AsyncTask<String, Void, String> {
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
                    AlerError.Baoloi("No Data.", IssuesActivity.this);
                    return;
                }
                JSONObject jsonObject = jsonArray.getJSONObject(0);
                issue = jsonObject.getString("issue").replace("null", "");
                time_sending = jsonObject.getString("time_sending").replace("null", "");
                info_warning = jsonObject.getString("info_warning").replace("null", "");
                info_finish = jsonObject.getString("info_finish").replace("null", "");
                time_finish = jsonObject.getString("time_finish").replace("null", "");
                popupwarningdetail(pos);
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
            }
        }

    }

    private void popupAction(int position) {
        dialogaction = new Dialog(IssuesActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        View dialogView = LayoutInflater.from(IssuesActivity.this).inflate(R.layout.popup_action, null);
        dialogaction.setCancelable(false);
        dialogaction.setContentView(dialogView);
        dialogaction.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogaction.cancel();
            }
        });
        TextView tvId, tvLocation, tvNm, tvError, tvmin, tvmax, tvIssue;
        FloatingActionButton fab;
        TextInputEditText tvinput;
        Button Picture;

        tvId = dialogaction.findViewById(R.id.tvId);
        tvLocation = dialogaction.findViewById(R.id.tvLocation);
        tvNm = dialogaction.findViewById(R.id.tvNm);
        tvError = dialogaction.findViewById(R.id.tvError);
        tvmin = dialogaction.findViewById(R.id.tvmin);
        tvmax = dialogaction.findViewById(R.id.tvmax);
        fab = dialogaction.findViewById(R.id.fab);
        tvinput = dialogaction.findViewById(R.id.tvinput);
        Picture = dialogaction.findViewById(R.id.bt1);
        impicture = dialogaction.findViewById(R.id.impicture);
        tvIssue = dialogaction.findViewById(R.id.tvIssue);

        tvId.setText(issuesMaterArrayList.get(position).ss_no);
        tvNm.setText(issuesMaterArrayList.get(position).ss_nm);
        String error = "", min = "", max = "", issues = "";
        if (!issuesMaterArrayList.get(position).getTemp_issue().equals("003")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_temp() + "°C" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_temp() + "°C" + " - ";
            max = max + issuesMaterArrayList.get(position).getMax_temp() + "°C" + " - ";
            issues += issuesMaterArrayList.get(position).getTemp_issue_nm() + " - ";
        }
        if (!issuesMaterArrayList.get(position).getHumi_issue().equals("006")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_humi() + "%" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_humi() + "%" + " - ";
            max = max + issuesMaterArrayList.get(position).getMax_humi() + "%" + " - ";
            issues += issuesMaterArrayList.get(position).getHumi_issue_nm() + " - ";
        }
        if (!issuesMaterArrayList.get(position).getPress_issue().equals("009")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_press() + "Pa" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_press() + "Pa" + " - ";
            max = max + issuesMaterArrayList.get(position).getMax_press() + "Pa" + " - ";
            issues += issuesMaterArrayList.get(position).getPress_issue_nm() + " - ";
        }
        if (!issuesMaterArrayList.get(position).getPow_issue().equals("012")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_pow() + "W" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_pow() + "W" + " - ";
            max = max + issuesMaterArrayList.get(position).getMin_pow() + "W" + " - ";
            issues += issuesMaterArrayList.get(position).getPow_issue_nm() + " - ";
        }
        tvIssue.setText(issues.substring(0, issues.length() - 2));
        tvError.setText(error.substring(0, error.length() - 2));
        tvmin.setText(min.substring(0, min.length() - 2));
        tvmax.setText(max.substring(0, max.length() - 2));

        tvLocation.setText(issuesMaterArrayList.get(position).getBuilding_name() + " - " + issuesMaterArrayList.get(position).getFloor_name());
        buttontoggleaction = dialogaction.findViewById(R.id.buttontoggle);

        new getOnaction().execute(Url + "Monitor/Searchnhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no());
        Log.d("getOnaction", Url + "Monitor/Searchnhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no());
        buttontoggleaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttontoggleaction.getText().equals("ON")) {
                    new setOnAction().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=002");
                    Log.d("setOnAction", Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=002");
                } else {
                    new setOnAction().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=001");
                    Log.d("setOnAction", Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=001");
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvinput.length() == 0) {
                    tvinput.setError("Input here!");
                } else {
                    if (fileHinh == null) {
                        AlerError.Baoloi("Please take picture", IssuesActivity.this);
                    } else {
                        uploadBitmap(fileHinh, tvinput.getText().toString(), position);
                    }
                }
            }
        });

        Picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
                startActivityForResult(intent, 0);
            }
        });

        dialogaction.show();
    }


    private void sendaction(String noidung, int position, String hinh) {
        new saveaction().execute(Url + "Monitor/Save_dt_history_finish?id=" + issuesMaterArrayList.get(position).getId() + "&value_all=" + noidung + "&img_issue=" + hinh);
        Log.d("saveaction", Url + "Monitor/Save_dt_history_finish?id=" + issuesMaterArrayList.get(position).getId() + "&value_all=" + noidung + "&img_issue=" + hinh);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 0 && data != null) {
            if (data != null && resultCode != Activity.RESULT_CANCELED) {
                Bitmap photo = (Bitmap) data.getExtras().get("data");
                int nh = (int) (photo.getHeight() * (480.0 / photo.getWidth()));
                Bitmap scaled = Bitmap.createScaledBitmap(photo, 480, nh, true);
                impicture.setImageBitmap(scaled);
                fileHinh = scaled;
            } else {
            }
        }
    }

    private void popupwarningdetail(int position) {
        Dialog dialog = new Dialog(IssuesActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        View dialogView = LayoutInflater.from(IssuesActivity.this).inflate(R.layout.popup_warning_detail, null);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        TextView tvId, tvLocation, tvNm, tvIssue, tvOrder, tvTime, tvAction, tvTimeend;
        RelativeLayout rl;
        ImageView imaAction;


        tvId = dialog.findViewById(R.id.tvId);
        tvLocation = dialog.findViewById(R.id.tvLocation);
        tvNm = dialog.findViewById(R.id.tvNm);
        tvIssue = dialog.findViewById(R.id.tvIssue);
        tvOrder = dialog.findViewById(R.id.tvOrder);
        tvTime = dialog.findViewById(R.id.tvTime);
        tvAction = dialog.findViewById(R.id.tvAction);
        tvTimeend = dialog.findViewById(R.id.tvTimeend);
        rl = dialog.findViewById(R.id.rl);
        imaAction = dialog.findViewById(R.id.imaAction);

        if (info_finish == "" && time_finish == "") {
            rl.setVisibility(View.GONE);
        } else {
            rl.setVisibility(View.VISIBLE);
        }

        tvIssue.setText(issue);
        tvOrder.setText(info_warning);
        tvTime.setText(time_sending);
        tvAction.setText(info_finish);
        tvTimeend.setText(time_finish);

        tvId.setText(issuesMaterArrayList.get(position).ss_no);
        tvNm.setText(issuesMaterArrayList.get(position).ss_nm);
        tvLocation.setText(issuesMaterArrayList.get(position).getBuilding_name() + " - " + issuesMaterArrayList.get(position).getFloor_name());

        Glide.with(IssuesActivity.this)
                .load(Url + "Images/Monitor/IssuesHistory/" + issuesMaterArrayList.get(position).getImg_issue())
                .error(R.drawable.errorimage)
                .into(imaAction);

        Log.d("image", Url + "Images/Monitor/IssuesHistory/" + issuesMaterArrayList.get(position).getImg_issue());

        dialog.show();
    }


    private void popupwarning(int position) {
        dialog = new Dialog(IssuesActivity.this, R.style.Theme_AppCompat_DayNight_Dialog_Alert);
        View dialogView = LayoutInflater.from(IssuesActivity.this).inflate(R.layout.popup_warning, null);
        dialog.setCancelable(false);
        dialog.setContentView(dialogView);
        dialog.findViewById(R.id.btclose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.cancel();
            }
        });
        TextView tvId, tvLocation, tvNm, tvError, tvmin, tvmax, tvIssue;
        FloatingActionButton fab;
        TextInputEditText tvinput;

        tvId = dialog.findViewById(R.id.tvId);
        tvLocation = dialog.findViewById(R.id.tvLocation);
        tvNm = dialog.findViewById(R.id.tvNm);
        tvError = dialog.findViewById(R.id.tvError);
        tvmin = dialog.findViewById(R.id.tvmin);
        tvmax = dialog.findViewById(R.id.tvmax);
        fab = dialog.findViewById(R.id.fab);
        tvinput = dialog.findViewById(R.id.tvinput);
        tvIssue = dialog.findViewById(R.id.tvIssue);
        tvId.setText(issuesMaterArrayList.get(position).ss_no);
        tvNm.setText(issuesMaterArrayList.get(position).ss_nm);

        String error = "", min = "", max = "";
        String issues = "";
        if (!issuesMaterArrayList.get(position).getTemp_issue().equals("003")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_temp() + "°C" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_temp() + "°C" + " - ";
            max = max + issuesMaterArrayList.get(position).getMax_temp() + "°C" + " - ";
            issues += issuesMaterArrayList.get(position).getTemp_issue_nm() + " - ";
        }
        if (!issuesMaterArrayList.get(position).getHumi_issue().equals("006")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_humi() + "%" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_humi() + "%" + " - ";
            max = max + issuesMaterArrayList.get(position).getMax_humi() + "%" + " - ";
            issues += issuesMaterArrayList.get(position).getHumi_issue_nm() + " - ";
        }
        if (!issuesMaterArrayList.get(position).getPress_issue().equals("009")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_press() + "Pa" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_press() + "Pa" + " - ";
            max = max + issuesMaterArrayList.get(position).getMax_press() + "Pa" + " - ";
            issues += issuesMaterArrayList.get(position).getPress_issue_nm() + " - ";
        }
        if (!issuesMaterArrayList.get(position).getPow_issue().equals("012")) {
            error = error + issuesMaterArrayList.get(position).getCurrent_pow() + "W" + " - ";
            min = min + issuesMaterArrayList.get(position).getMin_pow() + "W" + " - ";
            max = max + issuesMaterArrayList.get(position).getMin_pow() + "W" + " - ";
            issues += issuesMaterArrayList.get(position).getPow_issue_nm() + " - ";
        }

        tvIssue.setText(issues.substring(0, issues.length() - 2));

        tvError.setText(error.substring(0, error.length() - 2));
        tvmin.setText(min.substring(0, min.length() - 2));
        tvmax.setText(max.substring(0, max.length() - 2));

        tvLocation.setText(issuesMaterArrayList.get(position).getBuilding_name() + " - " + issuesMaterArrayList.get(position).getFloor_name());
        buttontoggle = dialog.findViewById(R.id.buttontoggle);

        new getOn().execute(Url + "Monitor/Searchnhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no());
        Log.d("getOn", Url + "Monitor/Searchnhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no());
        buttontoggle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (buttontoggle.getText().equals("ON")) {
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=002");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=002");
                } else {
                    new setOn().execute(Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=001");
                    Log.d("setOn", Url + "Monitor/On_OF_nhietdo?ss_no=" + issuesMaterArrayList.get(position).getSs_no() + "&sts=001");
                }
            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (tvinput.length() == 0) {
                    tvinput.setError("Input here!");
                } else {
                    sendwarning(tvinput.getText().toString(), position);
                }
            }
        });

        dialog.show();
    }

    private void sendwarning(String noidung, int position) {
        new saveissues().execute(Url + "Monitor/Save_dt_history?id=" + issuesMaterArrayList.get(position).getId() + "&value_all=" + noidung);
        Log.d("saveissues", Url + "Monitor/Save_dt_history?id=" + issuesMaterArrayList.get(position).getId() + "&value_all=" + noidung);
    }

    private class saveissues extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String TV = jsonObject.getString("result");
                if (TV.equals("true")) {
                    dialog.cancel();
                    loaddata();
                } else {
                    AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
            }
        }

    }

    private class saveaction extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);
                String TV = jsonObject.getString("result");
                if (TV.equals("true")) {
                    dialogaction.cancel();
                    loaddata();
                } else {
                    AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
            }
        }

    }

    private class setOnAction extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("result")) {
                    if (buttontoggleaction.getText().equals("ON")) {
                        buttontoggleaction.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggleaction.setText("ON");
                    } else {
                        buttontoggleaction.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggleaction.setText("OFF");
                    }
                } else {
                    if (buttontoggleaction.getText().equals("ON")) {
                        buttontoggleaction.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggleaction.setText("OFF");
                    } else {
                        buttontoggleaction.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggleaction.setText("ON");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                if (buttontoggle.getText().equals("ON")) {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                } else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            }
        }

    }

    private class setOn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("result")) {
                    if (buttontoggle.getText().equals("ON")) {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    } else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    }
                } else {
                    if (buttontoggle.getText().equals("ON")) {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    } else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                if (buttontoggle.getText().equals("ON")) {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                } else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            }
        }

    }

    private class getOnaction extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("result").replace("[\"", "").replace("\"]", "").equals("001")) {
                    buttontoggleaction.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggleaction.setText("ON");
                } else {
                    buttontoggleaction.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggleaction.setText("OFF");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }

    private class getOn extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("result").replace("[\"", "").replace("\"]", "").equals("001")) {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                    buttontoggle.setText("ON");
                } else {
                    buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                    buttontoggle.setText("OFF");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }


    //    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu_control, menu);
//        return true;
//    }
//
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        switch (item.getItemId()) {
//            case R.id.menu_control:
//                Intent intent = new Intent(IssuesActivity.this, SensorCheckActivity.class);
//                startActivity(intent);
//                finish();
//                return true;
//
//            default:
//                return super.onOptionsItemSelected(item);
//        }
//    }
    @Override
    public boolean onMenuItemClick(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.SearchCode:
                searchView.setQueryHint("Search Code");
                oftion = "Code";
                searchView.setQuery("", true);
                return true;
            case R.id.SearchName:
                searchView.setQueryHint("Search Name");
                oftion = "Name";
                searchView.setQuery("", true);
                return true;
            case R.id.Searchbuild:
                searchView.setQueryHint("Search Building");
                oftion = "Building";
                searchView.setQuery("", true);
                return true;
            case R.id.Searchfloor:
                searchView.setQueryHint("Search Floor");
                oftion = "Floor";
                searchView.setQuery("", true);
                return true;
        }
        return false;
    }


    //hinh anh
    private void uploadBitmap(final Bitmap bitmap, String nd, int pos) {


        //getting the tag from the edittext
        //final String tags = editTextTags.getText().toString().trim();
        //our custom volley request
        String UPLOAD_URL = Url + "Monitor/Insertfile?file";
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
                                Toast.makeText(IssuesActivity.this, "Upload photo success", Toast.LENGTH_SHORT).show();
                                sendaction(nd, pos, obj.getString("tenhinh"));
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
                        AlerError.Baoloi("Could not connect to server", IssuesActivity.this);
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
                params.put("", "");
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
                params.put("file", new DataPart(filename + ".png", getFileDataFromDrawable(bitmap)));
                return params;
            }
        };

        //adding the request to volley
        Volley.newRequestQueue(IssuesActivity.this).add(volleyMultipartRequest);
    }

    // Array bitmap
    public byte[] getFileDataFromDrawable(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }


//    @Override
//    protected void onResume() {
//        new readissues().execute(Url + "Monitor/GetIssuesHistory");
//        Log.d("readhistory", Url + "Monitor/GetIssuesHistory");
//        super.onResume();
//    }
}
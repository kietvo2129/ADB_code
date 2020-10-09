package com.autonsi.databoard.AMS;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

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
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CalendarView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.Receving.ReceivingOder.ReceivingActivity;
import com.autonsi.databoard.fragment.AMSFragment;
import com.bumptech.glide.Glide;
import com.github.mikephil.charting.data.Entry;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.quickblox.sample.videochat.java.R;
import com.roughike.swipeselector.OnSwipeItemSelectedListener;
import com.roughike.swipeselector.SwipeItem;
import com.roughike.swipeselector.SwipeSelector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class ChangeMcLocationActivity extends AppCompatActivity {
    String Url = com.autonsi.databoard.Url.webUrl;
    ArrayList<CorporationMaster> corporationMasterArrayList;
    ArrayList<BuildingMaster> buildingMasterArrayList;
    ArrayList<Floormaster> floormasterArrayList;
    ArrayList<LineMaster> lineMasterArrayList;
    ArrayList<UserMaster> userMasterArrayList;
    Spinner spinCorpo, spinBuilding, spinFlor, spinLine;
    TextView confirm;
    CalendarView calendar;

    String[] key_code_confirm = {"", "", "", "", "", "", ""};// 0 cor,1 building, 2floor,3 line, 4 time,5 user,6 category.
    Long date;
    SwipeSelector swipeSelector_receive_user;
    ImageView map, img_qrcode;
    EditText edt_qrcode;
    TextView Corporation, Building, FLoor, Line;
    RelativeLayout contain;
    TextView tv_error;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_mc_location);
        getDataCorporation();
        spinCorpo = (Spinner) findViewById(R.id.spinnerCordo);
        spinBuilding = (Spinner) findViewById(R.id.spinnerBuild);
        spinFlor = (Spinner) findViewById(R.id.spinnerFloor);
        spinLine = (Spinner) findViewById(R.id.spinnerLine);
        confirm = findViewById(R.id.confirm);
        calendar = findViewById(R.id.calendar);
        swipeSelector_receive_user = findViewById(R.id.swipeSelector_receive_user);
        contain = findViewById(R.id.contain);
        Corporation = findViewById(R.id.Corporation);
        Building = findViewById(R.id.Building);
        FLoor = findViewById(R.id.FLoor);
        Line = findViewById(R.id.Line);
        img_qrcode = findViewById(R.id.img_qrcode);
        edt_qrcode = findViewById(R.id.edt_qrcode);
        map = findViewById(R.id.map);
        contain.setVisibility(View.GONE);
        calendar.setMinDate(System.currentTimeMillis() - 1000);// disable time before today
        date = calendar.getDate();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        key_code_confirm[4] = format1.format(date);
        tv_error= findViewById(R.id.tv_error);

        calendar.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
            public void onSelectedDayChange(CalendarView view, int year, int month, int dayOfMonth) {
                int month12 = month + 1;
                date = calendar.getDate();
                key_code_confirm[4] = year + "-" + month12 + "-" + dayOfMonth;
            }
        });

        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (key_code_confirm[0]==""){
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Please, choose Corporation!");
                }else  if (key_code_confirm[1]==""){
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Please, choose Building!");
                }else if (key_code_confirm[2]==""){
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Please, choose Floor!");
                }else if (key_code_confirm[3]==""){
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Please, choose Line!");
                }else if (key_code_confirm[4]==""){
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Please, choose date moving!");
                }else if (key_code_confirm[5]==""){
                    tv_error.setVisibility(View.VISIBLE);
                    tv_error.setText("Please, choose Receive User!");
                }else {
                    tv_error.setVisibility(View.GONE);
                    dialog_confirm();
                }
            }
        });

        getUser();
        //scan
        img_qrcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startQRScanner();
            }
        });
        edt_qrcode.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int i, KeyEvent keyEvent) {
                closeKeyboard();
                if (edt_qrcode.getText().toString().length() != 0) {
                    getData(edt_qrcode.getText().toString().trim());
                }
                return false;
            }
        });
    }

    public void startQRScanner() {
        new IntentIntegrator(this).initiateScan();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            if (result.getContents() == null) {
                Toast.makeText(ChangeMcLocationActivity.this, "Cancelled", Toast.LENGTH_LONG).show();
            } else {
                edt_qrcode.setText(result.getContents());
                getData(result.getContents());
            }
        }
    }

    public void closeKeyboard() {
        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputMethodManager.toggleSoftInput(InputMethodManager.HIDE_IMPLICIT_ONLY, 0);
    }

    private void getData(String code) {
        new getData().execute(Url + "AMSTPMSchedule/API_GetAllMachines?code=" + code);
    }

    private void getUser() {
        new getUser().execute(Url + "/AMSTPMSchedule/GetAllAssetManagers");
    }

    private class getUser extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            userMasterArrayList = new ArrayList<>();
            try {
                JSONArray jsonArray = new JSONArray(s);

                if (jsonArray.length() == 0) {
                    return;
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    userMasterArrayList.add(new UserMaster(object.getString("userid"), object.getString("re_mark")));
                }

                setUser();
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }
        }

    }

    private void setUser() {
        SwipeItem[] list = new SwipeItem[userMasterArrayList.size()];
        if (userMasterArrayList.size() == 0) {
            key_code_confirm[5] = "";
        } else {
            key_code_confirm[5] = userMasterArrayList.get(0).getUserid();
        }
        for (int i = 0; i < userMasterArrayList.size(); i++) {
            list[i] = new SwipeItem(i, userMasterArrayList.get(i).usernm, userMasterArrayList.get(i).userid);
        }
        swipeSelector_receive_user.setItems(list);
        swipeSelector_receive_user.setOnItemSelectedListener(new OnSwipeItemSelectedListener() {
            @Override
            public void onItemSelected(SwipeItem item) {
                key_code_confirm[5] = item.description;
            }
        });

    }


    private class getData extends AsyncTask<String, Void, String> {
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
                    AlerError.Baoloi(jsonObject.getString("message"), ChangeMcLocationActivity.this);
                    contain.setVisibility(View.GONE);
                    return;

                }
                JSONArray jsonArray = jsonObject.getJSONArray("Data");
                JSONArray jsonArray2 = jsonObject.getJSONArray("data");

                JSONObject jsonObject1 = jsonArray2.getJSONObject(0);
                setImage(jsonObject1.getString("Image"));

                for (int i = 0; i < jsonArray.length(); i++) {
                    contain.setVisibility(View.VISIBLE);
                    JSONObject object = jsonArray.getJSONObject(i);
                    Corporation.setText(object.getString("CompanyName"));
                    Building.setText(object.getString("BuildingName"));
                    FLoor.setText(object.getString("MachineLocationName"));
                    Line.setText(object.getString("LineName"));

                }


            } catch (JSONException e) {
                e.printStackTrace();
                contain.setVisibility(View.GONE);
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }
        }

    }

    private void setImage(String image) {
        Glide.with(ChangeMcLocationActivity.this)
                .load(Url + "Images/Machine/" + image)
                .error(R.drawable.errorimage)
                .into(map);

    }


    private void getDataCorporation() {
        new getDataCorporation().execute(Url + "Location/GetCompanyAllList");
    }

    private class getDataCorporation extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            corporationMasterArrayList = new ArrayList<>();
            key_code_confirm[0] = "";
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")) {
                    return;
                }
                JSONArray jsonArray = jsonObject.getJSONArray("data");

                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject object = jsonArray.getJSONObject(i);
                    corporationMasterArrayList.add(new CorporationMaster(object.getString("company_name"), object.getString("company_cd")));
                    arrayList.add(object.getString("company_name"));
                }


            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }
            setCorporation(spinCorpo, arrayList, "Corpo");
        }

    }

    private void setCorporation(Spinner spinner, ArrayList<String> arrayList, String key) {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, arrayList);
        adapter.setDropDownViewResource(android.R.layout.simple_list_item_single_choice);
        spinner.setAdapter(adapter);
        if (key.equals("Corpo")) {
            key_code_confirm[0] = "";
        } else if (key.equals("Building")) {
            key_code_confirm[1] = "";
            key_code_confirm[6] = "";
        } else if (key.equals("Floor")) {
            key_code_confirm[2] = "";
        } else if (key.equals("Line")) {
            key_code_confirm[3] = "";
        }
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                if (key.equals("Corpo")) {
                    key_code_confirm[0] = corporationMasterArrayList.get(i).company_cd;
                    new getDataBuilding().execute(Url + "Location/GetBuildingByCompanyCode?companyCode=" + key_code_confirm[0]);
                    Log.e("Corpo", Url + "Location/GetBuildingByCompanyCode?companyCode=" + key_code_confirm[0]);
                } else if (key.equals("Building")) {
                    key_code_confirm[1] = buildingMasterArrayList.get(i).Building_co;

                    if (buildingMasterArrayList.get(i).category.equals("001")) {
                        key_code_confirm[6] = "0";
                    } else {
                        key_code_confirm[6] = "1";
                    }

                    new getDataFloor().execute(Url + "Location/GetFloorOrPathDependOnLocationCategory?buildingCode=" + key_code_confirm[1] + "&lctCategory=" + buildingMasterArrayList.get(i).category);
                    Log.e("Building", Url + "Location/GetFloorOrPathDependOnLocationCategory?buildingCode=" + key_code_confirm[1] + "&lctCategory=" + buildingMasterArrayList.get(i).category);

                } else if (key.equals("Floor")) {
                    key_code_confirm[2] = floormasterArrayList.get(i).lct_cd;
                    new getDataLine().execute(Url + "Line/GetAllLinesByFloorCode?floorCode=" + key_code_confirm[2]);
                } else if (key.equals("Line")) {
                    key_code_confirm[3] = lineMasterArrayList.get(i).line_no;
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private class getDataBuilding extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            buildingMasterArrayList = new ArrayList<>();
            key_code_confirm[1] = "";
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")) {
                    setCorporation(spinBuilding, arrayList, "Building");
                    setCorporation(spinFlor, arrayList, "Floor");
                    setCorporation(spinLine, arrayList, "Line");
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        buildingMasterArrayList.add(new BuildingMaster(object.getString("lct_nm"), object.getString("lct_cd"), object.getString("category")));
                        arrayList.add(object.getString("lct_nm"));
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }
            setCorporation(spinBuilding, arrayList, "Building");

        }

    }

    private class getDataFloor extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            floormasterArrayList = new ArrayList<>();
            key_code_confirm[2] = "";
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")) {
                    setCorporation(spinFlor, arrayList, "Floor");
                    setCorporation(spinLine, arrayList, "Line");
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        floormasterArrayList.add(new Floormaster(object.getString("lct_nm"), object.getString("lct_cd")));
                        arrayList.add(object.getString("lct_nm"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }
            setCorporation(spinFlor, arrayList, "Floor");

        }

    }

    private class getDataLine extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            lineMasterArrayList = new ArrayList<>();
            key_code_confirm[3] = "";
            ArrayList<String> arrayList = new ArrayList<>();
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")) {
                    setCorporation(spinLine, arrayList, "Line");
                } else {
                    JSONArray jsonArray = jsonObject.getJSONArray("data");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        lineMasterArrayList.add(new LineMaster(object.getString("line_nm"), object.getString("line_no")));
                        arrayList.add(object.getString("line_nm"));
                    }
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }
            setCorporation(spinLine, arrayList, "Line");

        }

    }

    public void dialog_confirm() {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(ChangeMcLocationActivity.this);
        alertDialog.setCancelable(false);
        alertDialog.setTitle("Confirm!!!");
        String textconfrirm = "Moving " + edt_qrcode.getText().toString() + " to \n" +
                "Corporation: " + spinCorpo.getSelectedItem().toString() + "\n" +
                "Building: " + spinBuilding.getSelectedItem().toString() + "\n" +
                "Floor: " + spinFlor.getSelectedItem().toString() + "\n" +
                "Line: " + spinLine.getSelectedItem().toString() + "\n" +
                "on " + key_code_confirm[4] + "\n" +
                "Receive user: " + swipeSelector_receive_user.getSelectedItem().title;
        alertDialog.setMessage(textconfrirm); //"The data you entered does not exist on the server !!!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                save_confirm();

            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
            }
        });
        alertDialog.show();
    }

    private void save_confirm() {
        new save_confirm().execute(Url + "AMSTPMSchedule/InsertMovingSchedule?MachineNo="+ edt_qrcode.getText().toString()
                + "&CompanyCode=" + key_code_confirm[0] + "&BuildingCode=" + key_code_confirm[1] + "&FloorPath=" + key_code_confirm[2]
                + "&LineNo=" + key_code_confirm[3] + "&MovingDate=" + key_code_confirm[4] + "&RecUser=" + key_code_confirm[5] + "&Cate="+key_code_confirm[6]);
        Log.e("confirm", Url + "AMSTPMSchedule/InsertMovingSchedule?MachineNo="+ edt_qrcode.getText().toString()
                + "&CompanyCode=" + key_code_confirm[0] + "&BuildingCode=" + key_code_confirm[1] + "&FloorPath=" + key_code_confirm[2]
                + "&LineNo=" + key_code_confirm[3] + "&MovingDate=" + key_code_confirm[4] + "&RecUser=" + key_code_confirm[5] + "&Cate="+key_code_confirm[6]);

    }
    private class save_confirm extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")) {
                    AlerError.Baoloi("Please check data again.", ChangeMcLocationActivity.this);
                } else {
                    Toast.makeText(ChangeMcLocationActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                    startActivity(getIntent());
                }

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", ChangeMcLocationActivity.this);
            }

        }

    }

}


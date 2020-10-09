package com.autonsi.databoard.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;


import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.autonsi.databoard.AMS.ChangeMcLocationActivity;
import com.autonsi.databoard.AlerError.AlerError;
import com.autonsi.databoard.AMS.McDetailActivity;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.nightonke.boommenu.BoomButtons.BoomButton;
import com.nightonke.boommenu.BoomButtons.HamButton;
import com.nightonke.boommenu.BoomMenuButton;
import com.nightonke.boommenu.OnBoomListener;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Random;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AMSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AMSFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public AMSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AMSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AMSFragment newInstance(String param1, String param2) {
        AMSFragment fragment = new AMSFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }
    String Url = com.autonsi.databoard.Url.webUrl;
    PieChart pieChart;
    BarChart barchart;
    ArrayList<Entry> bie_qty;
    ArrayList<String> labels_bie_value;
    Random rnd = new Random();
    int[] color_pie_list;
    ArrayList<BarEntry> bar_qty;
    ArrayList<String> labels_bar_value;
    int[] color_bar_list;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_ams, container, false);
        pieChart= view.findViewById(R.id.piechart);
        barchart= view.findViewById(R.id.barchart);

        //build bombutton
        BoomMenuButton bmb4 = (BoomMenuButton) view.findViewById(R.id.bmb4);
        bmb4.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.ic_find_mt_code)
                .normalTextRes(R.string.MachineDetails)
                .subNormalTextRes(R.string.CheckInformationMachine));
        bmb4.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.ic_moving)
                .normalTextRes(R.string.ChangeLocation)
                .subNormalTextRes(R.string.movingpositionofmachinery));
        bmb4.addBuilder(new HamButton.Builder()
                .normalImageRes(R.drawable.ic_control)
                .normalTextRes(R.string.ConfirmMoving)
                .subNormalTextRes(R.string.ConfirmMoving));
        bmb4.setOnBoomListener(new OnBoomListener() {
            @Override
            public void onClicked(int index, BoomButton boomButton) {
                switch (index) {
                    case 0:
                        Intent intent = new Intent(getContext(), McDetailActivity.class);
                        startActivity(intent);
                        break;
                    case 1:
                        Intent intent1 = new Intent(getContext(), ChangeMcLocationActivity.class);
                        startActivity(intent1);
                        break;
//                    case 2:
//                        Intent intent2 = new Intent(getContext(),ASAS22.class);
//                        startActivity(intent2);
//                        break;
                }

            }

            @Override
            public void onBackgroundClick() {

            }

            @Override
            public void onBoomWillHide() {

            }

            @Override
            public void onBoomDidHide() {

            }

            @Override
            public void onBoomWillShow() {

            }

            @Override
            public void onBoomDidShow() {

            }
        });
        getData();
        return view;
    }

    private void getData() {
        new getbieChart().execute(Url+"AMSMonitoring/CountingMachineTypesCurrentDate");
        new getbarChart().execute(Url+"AMSMonitoring/ShowingPieChart");

    }
    private class getbieChart extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            bie_qty = new ArrayList<>();
            labels_bie_value = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(s);
                if (!jsonObject.getBoolean("flag")){
                    AlerError.Baoloi(jsonObject.getString("message"), getContext());
                    return;
                }
                JSONArray jsonArray =jsonObject.getJSONArray("data");
                color_pie_list = new int[jsonArray.length()];
                for (int i=0;i<jsonArray.length();i++){
                    JSONObject object = jsonArray.getJSONObject(i);
                    bie_qty.add(new Entry(object.getInt("Quantity"),i));
                    labels_bie_value.add(object.getString("MachineTypeName"));
                    color_pie_list[i] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                }

                paint_bie_chart();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getContext());
            }

        }

    }

    private void paint_bie_chart() {
        PieDataSet pieDataSet = new PieDataSet(bie_qty,"");
        PieData data = new PieData(labels_bie_value,pieDataSet);
        pieDataSet.setValueTextColor(Color.DKGRAY);
        pieDataSet.setColors(ColorTemplate.createColors(color_pie_list));
        pieChart.setData(data);
        pieChart.setDescription("");
        pieChart.invalidate();
    }

    private class getbarChart extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... strings) {
            return com.autonsi.databoard.Url.NoiDung_Tu_URL(strings[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            bar_qty = new ArrayList<>();
            labels_bar_value = new ArrayList<>();

            try{
                JSONObject jsonObject = new JSONObject(s);
//                if (!jsonObject.getBoolean("flag")){
//                    AlerError.Baoloi(jsonObject.getString("message"), getContext());
//                    return;
//                }
               // JSONArray jsonArray =jsonObject.getJSONArray("data");
               // color_bar_list = new int[5];//jsonArray.length()];
               // for (int i=0;i<jsonArray.length();i++){
                    //JSONObject object = jsonArray.getJSONObject(i);
                  //  bar_qty.add(new BarEntry(object.getInt("Quantity"),i));
                bar_qty.add(new BarEntry(jsonObject.getInt("available"),0));
                bar_qty.add(new BarEntry(jsonObject.getInt("moving"),1));
                bar_qty.add(new BarEntry(jsonObject.getInt("used"),2));
                bar_qty.add(new BarEntry(jsonObject.getInt("broken"),3));
                bar_qty.add(new BarEntry(jsonObject.getInt("mainternance"),4));
                //    color_bar_list[i] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
               // }

                paint_bar_chart();

            } catch (JSONException e) {
                e.printStackTrace();
                AlerError.Baoloi("Could not connect to server", getContext());
            }

        }

    }

    private void paint_bar_chart() {

        color_bar_list = new int[5];
        color_bar_list[0] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        color_bar_list[1] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        color_bar_list[2] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        color_bar_list[3] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
        color_bar_list[4] = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));

        labels_bar_value.add("available");
        labels_bar_value.add("moving");
        labels_bar_value.add("used");
        labels_bar_value.add("broken");
        labels_bar_value.add("mainternance");

        BarDataSet barDataSet = new BarDataSet(bar_qty,"");
        BarData data = new BarData(labels_bar_value,barDataSet);
        barDataSet.setValueTextColor(Color.DKGRAY);
        barDataSet.setColors(ColorTemplate.createColors(color_bar_list));
        barchart.setData(data);
        barchart.setDescription("");
        barchart.invalidate();
    }

}
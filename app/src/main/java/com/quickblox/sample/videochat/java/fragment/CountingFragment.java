package com.quickblox.sample.videochat.java.fragment;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CountingFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CountingFragment extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public CountingFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment CountingFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CountingFragment newInstance(String param1, String param2) {
        CountingFragment fragment = new CountingFragment();
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_counting, container, false);
        BarChart barChart = (BarChart) view.findViewById (R.id.barchart);


        // create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup1 = new ArrayList<>();
        bargroup1.add(new BarEntry(8, 0));
        bargroup1.add(new BarEntry(2, 1));
        bargroup1.add(new BarEntry(5, 2));
        bargroup1.add(new BarEntry(20, 3));
        bargroup1.add(new BarEntry(15, 4));
        bargroup1.add(new BarEntry(19, 5));

// create BarEntry for Bar Group 1
        ArrayList<BarEntry> bargroup2 = new ArrayList<>();
        bargroup2.add(new BarEntry(6, 0));
        bargroup2.add(new BarEntry(10, 1));
        bargroup2.add(new BarEntry(5, 2));
        bargroup2.add(new BarEntry(25, 3));
        bargroup2.add(new BarEntry(4, 4));
        bargroup2.add(new BarEntry(17, 5));

// creating dataset for Bar Group1
        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Bar Group 1");

//barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColor(Color.parseColor("#413123"));

// creating dataset for Bar Group 2
        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Bar Group 2");
        //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet1.setColor(Color.parseColor("#584224"));
        barChart.setDescription("");

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("2011");
        labels.add("2012");
        labels.add("2013");
        labels.add("2014");
        labels.add("2015");
        labels.add("2016");

        ArrayList<BarDataSet> dataSets = new ArrayList<>();  // combined all dataset into an arraylist
        dataSets.add(barDataSet1);
        dataSets.add(barDataSet2);

// initialize the Bardata with argument labels and dataSet
        BarData data = new BarData(labels, dataSets);
        barChart.setData(data);

        return view;
    }
}
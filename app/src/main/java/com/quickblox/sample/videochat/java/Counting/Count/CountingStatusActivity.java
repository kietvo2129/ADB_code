package com.quickblox.sample.videochat.java.Counting.Count;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;

public class CountingStatusActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_counting_status);
        BarChart barChart = (BarChart) findViewById (R.id.barchart);


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
        BarDataSet barDataSet1 = new BarDataSet(bargroup1, "Efficiency");

//barDataSet1.setColor(Color.rgb(0, 155, 0));
        barDataSet1.setColor(Color.parseColor("#92D050"));

// creating dataset for Bar Group 2
        BarDataSet barDataSet2 = new BarDataSet(bargroup2, "Defective");
        //barDataSet2.setColors(ColorTemplate.COLORFUL_COLORS);
        barDataSet2.setColor(Color.parseColor("#FF0000"));
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
    }
}
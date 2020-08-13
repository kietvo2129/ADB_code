package com.autonsi.databoard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.autonsi.databoard.DigitalData.IssuesList.IssuesActivity;
import com.autonsi.databoard.DigitalData.IssuesReport.IssuesReportActivity;
import com.autonsi.databoard.DigitalData.StatusLayout.MapBuild.MapBuildActivity;
import com.quickblox.sample.videochat.java.R;
import com.autonsi.databoard.DigitalData.SensorList.SensorCheckActivity;


public class DigitalFragment extends Fragment {
    RelativeLayout mobicall,chart,History,temper,issueslist,checksensor;
    ImageView icon_notifi;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_gallery, container, false);
        getActivity().setTitle("Digital Data");
        final RelativeLayout Mapbuild = root.findViewById(R.id.Mapbuild);
       // mobicall = root.findViewById(R.id.mobicall);
       // chart = root.findViewById(R.id.chart);
        History = root.findViewById(R.id.History);
        //temper = root.findViewById(R.id.temper);
//        icon_notifi = root.findViewById(R.id.icon_notifi);
        checksensor = root.findViewById(R.id.checksensor);
        issueslist = root.findViewById(R.id.issueslist);
        Animation animation = AnimationUtils.loadAnimation(getActivity(),R.anim.rotate);

        //icon_notifi.startAnimation(animation);

        Mapbuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), MapBuildActivity.class);
                startActivity(intent);
            }
        });

        History.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IssuesReportActivity.class);
                startActivity(intent);
            }
        });
        issueslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), IssuesActivity.class);
                startActivity(intent);
            }
        });


        checksensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SensorCheckActivity.class);
                startActivity(intent);
            }
        });

        return root;
    }

}


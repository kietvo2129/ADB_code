package com.quickblox.sample.videochat.java.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.quickblox.sample.videochat.java.AlarmData.Dashboard.AlarmDashboardActivity;
import com.quickblox.sample.videochat.java.AlarmData.IssuesList.AlarmIssuesList;
import com.quickblox.sample.videochat.java.AlarmData.StatusLayout.MapBuildAlarmActivity;
import com.quickblox.sample.videochat.java.R;


public class AlarmFragment extends Fragment {

    RelativeLayout dashboard,layout,issuesreport,issueslist;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_slideshow, container, false);
        dashboard = root.findViewById(R.id.dashboard);
        layout = root.findViewById(R.id.layout);
        issuesreport = root.findViewById(R.id.issuesreport);
        issueslist = root.findViewById(R.id.issueslist);

        dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmDashboardActivity.class);
                startActivity(intent);
            }
        });
        layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MapBuildAlarmActivity.class);
                startActivity(intent);
            }
        });


        issuesreport.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(getActivity(), SmokeAlertActivity.class);
//                startActivity(intent);
            }
        });
        issueslist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), AlarmIssuesList.class);
                startActivity(intent);
            }
        });

        return root;
    }
}

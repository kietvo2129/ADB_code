package com.autonsi.databoard.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.autonsi.databoard.Counting.Count.CountActivity;
import com.autonsi.databoard.Counting.CountList.CountListActivity;
import com.autonsi.databoard.Counting.DashBoard.CountingStatusActivity;
import com.autonsi.databoard.Counting.StatusLayout.CountingMapBuildActivity;
import com.quickblox.sample.videochat.java.R;

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

    RelativeLayout rl_statuslayout,rl_countinglist,rl_report,rl_count,rl_Dashboard;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_counting, container, false);
        rl_statuslayout = view.findViewById(R.id.rl_statuslayout);
        rl_countinglist = view.findViewById(R.id.rl_countinglist);
        rl_report = view.findViewById(R.id.rl_report);
        rl_count = view.findViewById(R.id.rl_count);
        rl_Dashboard= view.findViewById(R.id.rl_Dashboard);

        rl_Dashboard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CountingStatusActivity.class);
                startActivity(intent);
            }
        });
        rl_statuslayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CountingMapBuildActivity.class);
                startActivity(intent);
            }
        });
        rl_count.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CountActivity.class);
                startActivity(intent);
            }
        });
        rl_countinglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CountListActivity.class);
                startActivity(intent);
            }
        });

        return view;
    }
}
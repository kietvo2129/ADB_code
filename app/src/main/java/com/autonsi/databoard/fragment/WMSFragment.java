package com.autonsi.databoard.fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import com.autonsi.databoard.Receving.ReceivingActivity;
import com.autonsi.databoard.Receving.ReceivingScanActivity;
import com.autonsi.databoard.Ship.ShipActivity;
import com.quickblox.sample.videochat.java.R;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link WMSFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class WMSFragment extends Fragment  {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    RelativeLayout rl_receiving,rl_scan;
    private RelativeLayout rl_Shipping;
    public WMSFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment WMSFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static WMSFragment newInstance(String param1, String param2) {
        WMSFragment fragment = new WMSFragment();
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
        View view =  inflater.inflate(R.layout.fragment_wms, container, false);

        rl_receiving = view.findViewById(R.id.rl_receiving);
        rl_receiving.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReceivingActivity.class);
                startActivity(intent);
            }
        });

        rl_Shipping = view.findViewById(R.id.rl_Shipping);
        rl_Shipping.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ShipActivity.class);

                startActivity(intent);
            }
        });
        rl_scan  = view.findViewById(R.id.rl_scan);
        rl_scan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), ReceivingScanActivity.class);

                startActivity(intent);
            }
        });

        return view;
    }




}
package com.autonsi.databoard.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.autonsi.databoard.CCTV.CCTVList.CCTVListActivity;
import com.autonsi.databoard.CCTV.CCTVLayout.CCTVBuildActivity;
import com.quickblox.sample.videochat.java.R;

public class CCTVFragment extends Fragment {

    String Url = com.autonsi.databoard.Url.webUrl;
    RelativeLayout Mapbuild,checksensor;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_cctv, container, false);
        Mapbuild = root.findViewById(R.id.Mapbuild);
        checksensor = root.findViewById(R.id.checksensor);
        Mapbuild.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), CCTVBuildActivity.class);
                startActivity(intent);
            }
        });

        checksensor.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CCTVListActivity.class);
                startActivity(intent);
            }
        });


        return root;
    }

}

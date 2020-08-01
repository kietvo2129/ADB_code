package com.quickblox.sample.videochat.java.Counting.StatusLayout.MapSensor;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.Counting.Count.Detail.CountingDetailActivity;
import com.quickblox.sample.videochat.java.R;

import java.util.List;

public class CountingMapSensorAdapter extends RecyclerView.Adapter<CountingMapSensorAdapter.DataHolder> {

    private List<CountingMapSensorMaster> CountingMapSensorMasterList;
    Context context;
    public CountingMapSensorAdapter(List<CountingMapSensorMaster> noteList) {
        CountingMapSensorMasterList = noteList;
    }
    public static int row_index=-1;
    @NonNull
    @Override
    public CountingMapSensorAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counting_map_line,parent,false);
        context = parent.getContext();
        CountingMapSensorAdapter.DataHolder dataHolder = new CountingMapSensorAdapter.DataHolder(view);

        return dataHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountingMapSensorAdapter.DataHolder holder, int position) {
        holder.setdata(CountingMapSensorMasterList.get(position));
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                Intent intent = new Intent(context, CountingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_line", CountingMapSensorMasterList.get(position).id);
                intent.putExtras(bundle);
                context.startActivity(intent);
            }
        });
        if (row_index == position) {
            holder.linear.setBackgroundColor(Color.parseColor("#32A8A8"));


        } else {
            holder.linear.setBackgroundColor(Color.parseColor("#FAFAFA"));
        }
    }

    @Override
    public int getItemCount() {
        return CountingMapSensorMasterList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        TextView LineID,LineName,TargetQty,DefectiveQty,Efficiency,ActualQty;
        LinearLayout linear;
        public DataHolder(@NonNull View itemView) {
            super(itemView);
            LineID = itemView.findViewById(R.id.LineID);
            LineName = itemView.findViewById(R.id.LineName);
            TargetQty = itemView.findViewById(R.id.TargetQty);
            DefectiveQty = itemView.findViewById(R.id.DefectiveQty);
            Efficiency = itemView.findViewById(R.id.Efficiency);
            ActualQty = itemView.findViewById(R.id.ActualQty);
            linear= itemView.findViewById(R.id.linear);
        }

        public void setdata(CountingMapSensorMaster sensorMapMaster){
            LineID.setText(sensorMapMaster.line_no);
            LineName.setText(sensorMapMaster.line_nm);
            TargetQty.setText(sensorMapMaster.target_qty);
            DefectiveQty.setText(sensorMapMaster.defect_qty);
            ActualQty.setText(sensorMapMaster.actual_qty);
            double effi = Double.parseDouble(sensorMapMaster.getActual_qty())*100/Double.parseDouble(sensorMapMaster.getTarget_qty());
            Efficiency.setText(String.format("%.2f",effi));
        }
    }
}
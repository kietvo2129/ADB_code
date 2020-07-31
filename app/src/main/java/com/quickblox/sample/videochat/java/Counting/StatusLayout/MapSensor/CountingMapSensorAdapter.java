package com.quickblox.sample.videochat.java.Counting.StatusLayout.MapSensor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.List;

public class CountingMapSensorAdapter extends RecyclerView.Adapter<CountingMapSensorAdapter.DataHolder> {

    private List<CountingMapSensorMaster> CountingMapSensorMasterList;

    public CountingMapSensorAdapter(List<CountingMapSensorMaster> noteList) {
        CountingMapSensorMasterList = noteList;
    }

    @NonNull
    @Override
    public CountingMapSensorAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counting_map_line,parent,false);

        CountingMapSensorAdapter.DataHolder dataHolder = new CountingMapSensorAdapter.DataHolder(view);

        return dataHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull CountingMapSensorAdapter.DataHolder holder, int position) {
        holder.setdata(CountingMapSensorMasterList.get(position));

    }

    @Override
    public int getItemCount() {
        return CountingMapSensorMasterList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        TextView LineID,LineName,TargetQty,DefectiveQty,Efficiency,ActualQty;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            LineID = itemView.findViewById(R.id.LineID);
            LineName = itemView.findViewById(R.id.LineName);
            TargetQty = itemView.findViewById(R.id.TargetQty);
            DefectiveQty = itemView.findViewById(R.id.DefectiveQty);
            Efficiency = itemView.findViewById(R.id.Efficiency);
            ActualQty = itemView.findViewById(R.id.ActualQty);
        }

        public void setdata(CountingMapSensorMaster sensorMapMaster){
            LineID.setText(sensorMapMaster.line_no);
            LineName.setText(sensorMapMaster.line_nm);
            TargetQty.setText(sensorMapMaster.target_qty);
            DefectiveQty.setText(sensorMapMaster.defect_qty);
            ActualQty.setText(sensorMapMaster.actual_qty);
            double effi = Double.parseDouble(sensorMapMaster.getActual_qty())*100/Double.parseDouble(sensorMapMaster.getTarget_qty());
            Efficiency.setText(effi+"");
        }
    }
}

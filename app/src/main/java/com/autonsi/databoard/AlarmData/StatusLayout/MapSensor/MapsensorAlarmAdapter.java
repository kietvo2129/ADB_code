package com.autonsi.databoard.AlarmData.StatusLayout.MapSensor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.List;

public class MapsensorAlarmAdapter extends RecyclerView.Adapter<MapsensorAlarmAdapter.DataHolder> {

    private List<MapsensorAlarmMaster> MapsensorAlarmMasterList;

    public MapsensorAlarmAdapter(List<MapsensorAlarmMaster> noteList) {
        MapsensorAlarmMasterList = noteList;
    }

    @NonNull
    @Override
    public MapsensorAlarmAdapter.DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor_maphis,parent,false);

        MapsensorAlarmAdapter.DataHolder dataHolder = new MapsensorAlarmAdapter.DataHolder(view);

        return dataHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull MapsensorAlarmAdapter.DataHolder holder, int position) {
        holder.setdata(MapsensorAlarmMasterList.get(position));

    }

    @Override
    public int getItemCount() {
        return MapsensorAlarmMasterList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        TextView SensorID,SensorName,location,timeupdate;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            SensorID = itemView.findViewById(R.id.SensorID);
            SensorName = itemView.findViewById(R.id.SensorName);
            location = itemView.findViewById(R.id.location);
            timeupdate = itemView.findViewById(R.id.timeupdate);

        }

        public void setdata(MapsensorAlarmMaster sensorMapMaster){
            SensorID.setText(sensorMapMaster.getSs_no());
            SensorName.setText(sensorMapMaster.getSs_nm());
            location.setText(sensorMapMaster.getLct_nm());
            timeupdate.setText(sensorMapMaster.getTime_up());
        }
    }
}

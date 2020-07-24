package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.Mapsensor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.List;

public class sensor_mapAdapter extends RecyclerView.Adapter<sensor_mapAdapter.DataHolder> {

    private List<sensor_map_Master> sensor_map_masterList;

    public sensor_mapAdapter(List<sensor_map_Master> noteList) {
        sensor_map_masterList = noteList;
    }

    @NonNull
    @Override
    public DataHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sensor_map,parent,false);

        DataHolder dataHolder = new DataHolder(view);

        return dataHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull DataHolder holder, int position) {
        holder.setdata(sensor_map_masterList.get(position));

    }

    @Override
    public int getItemCount() {
        return sensor_map_masterList.size();
    }

    public class DataHolder extends RecyclerView.ViewHolder {

        TextView SensorID,SensorName,Temperature,Humidity,Power,Pressure;

        public DataHolder(@NonNull View itemView) {
            super(itemView);
            SensorID = itemView.findViewById(R.id.SensorID);
            SensorName = itemView.findViewById(R.id.SensorName);
            Temperature = itemView.findViewById(R.id.Temperature);
            Humidity = itemView.findViewById(R.id.Humidity);
            Power = itemView.findViewById(R.id.Power);
            Pressure = itemView.findViewById(R.id.Pressure);
        }

        public void setdata(sensor_map_Master sensorMapMaster){
            SensorID.setText(sensorMapMaster.SensorID);
            SensorName.setText(sensorMapMaster.SensorName);
            Temperature.setText(sensorMapMaster.Temperature);
            Humidity.setText(sensorMapMaster.Humidity);
            Power.setText(sensorMapMaster.Power);
            Pressure.setText(sensorMapMaster.Pressure);
        }
    }
}

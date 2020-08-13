package com.autonsi.databoard.AlarmData.Dashboard;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.List;


public class AlarmDashboardAdapter extends RecyclerView.Adapter<AlarmDashboardAdapter.NoteVH> {
    private AlarmDashboardAdapter.OnItemClickListener mListener;
    Context context;
    public static List<AlarmDashboardMaster> mNoteList;


    public static int row_index=-1;
    public AlarmDashboardAdapter(List<AlarmDashboardMaster> noteList) {
        mNoteList = noteList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(AlarmDashboardAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public AlarmDashboardAdapter.NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homesensorinfor,parent,false);
        AlarmDashboardAdapter.NoteVH evh = new AlarmDashboardAdapter.NoteVH(view,mListener);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull AlarmDashboardAdapter.NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
//                Intent intent = new Intent(context, IssuesToday.class);
//                context.startActivity(intent);
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
        return mNoteList != null ? mNoteList.size() : 0;
    }
    class NoteVH extends RecyclerView.ViewHolder {
        TextView SensorID,SensorName,Location,UpdateTime;
        LinearLayout linear;


        public NoteVH(View itemView, final AlarmDashboardAdapter.OnItemClickListener listener) {
            super(itemView);
            SensorID = itemView.findViewById(R.id.SensorID);
            SensorName = itemView.findViewById(R.id.SensorName);
            Location = itemView.findViewById(R.id.Location);
            UpdateTime = itemView.findViewById(R.id.UpdateTime);
            linear= itemView.findViewById(R.id.linear);

        }

        public void bindData(AlarmDashboardMaster note) {
            SensorID.setText(note.getSs_no());
            SensorName.setText(note.getSs_nm());
            Location.setText(note.getBuilding_code() + " - " + note.getFloor_code());
            UpdateTime.setText(note.getTime());
        }
    }
}
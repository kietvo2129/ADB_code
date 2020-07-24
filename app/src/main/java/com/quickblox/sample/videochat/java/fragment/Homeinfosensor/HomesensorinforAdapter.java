package com.quickblox.sample.videochat.java.fragment.Homeinfosensor;


import android.content.Context;
import android.content.Intent;
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

public class HomesensorinforAdapter extends RecyclerView.Adapter<HomesensorinforAdapter.NoteVH> {
    private OnItemClickListener mListener;
    Context context;
    public static  List<HomesensorinforMaster> mNoteList;

    public static int row_index=-1;
    public HomesensorinforAdapter(List<HomesensorinforMaster> noteList) {
        mNoteList = noteList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_homesensorinfor,parent,false);
        NoteVH evh = new NoteVH(view,mListener);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));

        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                Intent intent = new Intent(context,IssuesToday.class);
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
        return mNoteList != null ? mNoteList.size() : 0;
    }
    class NoteVH extends RecyclerView.ViewHolder {
        TextView SensorID,SensorName,Location,UpdateTime;
        LinearLayout linear;


        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            SensorID = itemView.findViewById(R.id.SensorID);
            SensorName = itemView.findViewById(R.id.SensorName);
            Location = itemView.findViewById(R.id.Location);
            UpdateTime = itemView.findViewById(R.id.UpdateTime);
            linear= itemView.findViewById(R.id.linear);

        }

        public void bindData(HomesensorinforMaster note) {
            SensorID.setText(note.getSs_no());
            SensorName.setText(note.getSs_nm());
            Location.setText(note.getBuilding_name() + " - " + note.getFloor_name());
            UpdateTime.setText(note.getTime_update());
        }
    }
}

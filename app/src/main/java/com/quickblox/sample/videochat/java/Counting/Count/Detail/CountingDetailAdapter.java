package com.quickblox.sample.videochat.java.Counting.Count.Detail;

import android.annotation.SuppressLint;
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
import com.quickblox.sample.videochat.java.fragment.Homeinfosensor.IssuesToday;

import java.util.List;


public class CountingDetailAdapter extends RecyclerView.Adapter<CountingDetailAdapter.NoteVH> {
    private CountingDetailAdapter.OnItemClickListener mListener;
    Context context;
    public static List<DetailMaster> mNoteList;

    public static int row_index=-1;
    public CountingDetailAdapter(List<DetailMaster> noteList) {
        mNoteList = noteList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CountingDetailAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public CountingDetailAdapter.NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_counting_detail,parent,false);
        CountingDetailAdapter.NoteVH evh = new CountingDetailAdapter.NoteVH(view,mListener);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountingDetailAdapter.NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));



    }


    @Override
    public int getItemCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }
    class NoteVH extends RecyclerView.ViewHolder {
        TextView UpdateTime,TargetHour,ActualHour,DefectiveHour,EfficiencyHour;

        public NoteVH(View itemView, final CountingDetailAdapter.OnItemClickListener listener) {
            super(itemView);
            TargetHour = itemView.findViewById(R.id.TargetHour);
            ActualHour = itemView.findViewById(R.id.ActualHour);
            DefectiveHour = itemView.findViewById(R.id.DefectiveHour);
            UpdateTime = itemView.findViewById(R.id.UpdateTime);
            EfficiencyHour= itemView.findViewById(R.id.EfficiencyHour);

        }

        public void bindData(DetailMaster note) {
            UpdateTime.setText(note.getStart_time().substring(0,2)+":"+note.getStart_time().substring(2,4));
            TargetHour.setText(note.getTarget_qty());
            ActualHour.setText(note.getActual_qty());
            DefectiveHour.setText(note.getDefect_qty());
            double effi = Double.parseDouble(note.getActual_qty())*100/Double.parseDouble(note.getTarget_qty());
            EfficiencyHour.setText(String.format("%.2f",effi) +"%");

            double Alarm_Range2=0,Alarm_Range1=0;
            Alarm_Range1 = Double.parseDouble(note.Alarm_Range1);
            Alarm_Range2 = Double.parseDouble(note.Alarm_Range2);
            if (effi <= 0){
                EfficiencyHour.setBackgroundResource(R.color.color_item_grey);
            }else if (effi<=Alarm_Range2){
                EfficiencyHour.setBackgroundResource(R.color.color_item_red);
            }else if  (effi>Alarm_Range2 && effi<=Alarm_Range1){
                EfficiencyHour.setBackgroundResource(R.color.color_item_yellow);
            }else {
                EfficiencyHour.setBackgroundResource(R.color.color_item_green);
            }



        }
    }
}

package com.quickblox.sample.videochat.java.Counting.DashBoard;

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
import com.quickblox.sample.videochat.java.fragment.Homeinfosensor.IssuesToday;

import java.util.List;

public class DashboardCountingAdapter extends RecyclerView.Adapter<DashboardCountingAdapter.NoteVH> {
    private DashboardCountingAdapter.OnItemClickListener mListener;
    Context context;
    public static List<DashboardCountingMaster> mNoteList;



    public static int row_index=-1;
    public DashboardCountingAdapter(List<DashboardCountingMaster> noteList) {
        mNoteList = noteList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(DashboardCountingAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public DashboardCountingAdapter.NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dashboard_counting,parent,false);
        DashboardCountingAdapter.NoteVH evh = new DashboardCountingAdapter.NoteVH(view,mListener);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull DashboardCountingAdapter.NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
        holder.linear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                row_index=position;
                notifyDataSetChanged();
                Intent intent = new Intent(context, CountingDetailActivity.class);
                Bundle bundle = new Bundle();
                bundle.putString("id_line", mNoteList.get(position).id);
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
        return mNoteList != null ? mNoteList.size() : 0;
    }
    class NoteVH extends RecyclerView.ViewHolder {
        TextView LineID,LineName,Target,TargetHour,Actual,Defective;
        LinearLayout linear;

        public NoteVH(View itemView, final DashboardCountingAdapter.OnItemClickListener listener) {
            super(itemView);
            LineID = itemView.findViewById(R.id.LineID);
            LineName = itemView.findViewById(R.id.LineName);
            Target = itemView.findViewById(R.id.Target);
            TargetHour = itemView.findViewById(R.id.TargetHour);
            Actual= itemView.findViewById(R.id.Actual);
            Defective= itemView.findViewById(R.id.Defective);
            linear= itemView.findViewById(R.id.linear);
        }

        public void bindData(DashboardCountingMaster note) {
            LineID.setText(note.getLine_no());
            LineName.setText(note.line_nm);
            Target.setText(note.target_qty);
            TargetHour.setText(note.qty_per_hour);
            Actual.setText(note.current_act_qty);
            Defective.setText(note.current_def_qty);
        }
    }
}
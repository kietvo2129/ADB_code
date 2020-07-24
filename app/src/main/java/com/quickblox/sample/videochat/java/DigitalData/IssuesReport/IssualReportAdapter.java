package com.quickblox.sample.videochat.java.DigitalData.IssuesReport;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class IssualReportAdapter extends RecyclerView.Adapter<IssualReportAdapter.IssuesReportVH> {
    private List<IssuesReport> issuesReports;

    private OnItemClickListener mListener;
    private int reId=-1;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public IssualReportAdapter(List<IssuesReport> issuesReport) {
        issuesReports = issuesReport;
    }

    @Override
    public IssuesReportVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issues_report,
                parent, false);
        IssuesReportVH evh = new IssuesReportVH(v, mListener);
        return evh;
    }

    class IssuesReportVH extends RecyclerView.ViewHolder {
        private TextView tv_sensor_id;
        private TextView tv_sensor_name;
        private TextView tv_location;
        private TextView tv_update_time;
        private LinearLayout lay_isu_repo;

        public IssuesReportVH(View itemView, final OnItemClickListener listener) {
            super(itemView);

            tv_sensor_id = itemView.findViewById(R.id.tv_sensor_id);
            tv_sensor_name = itemView.findViewById(R.id.tv_sensor_name);
            tv_location = itemView.findViewById(R.id.tv_location);
            tv_update_time = itemView.findViewById(R.id.tv_update_time);
            lay_isu_repo = itemView.findViewById(R.id.lay_isu_repo);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION && listener != null) {
                        listener.onItemClick(position);
//
//                        for (int i =0;i<issuesReports.size();i++){
//                            lay_isu_repo.setBackgroundColor(Color.TRANSPARENT);
//                        }
//                        lay_isu_repo.setBackgroundColor(Color.parseColor("#009688"));
                    }

                }
            });
        }

//        public void bindData(IssuesReport issuesReport) {
//
//            tv_sensor_id.setText(issuesReport.getSensorID());
//            tv_sensor_name.setText(issuesReport.getSensorName());
//            tv_location.setText(issuesReport.getLocation());
//            tv_update_time.setText(issuesReport.getUpdateTime());
//
//        }
    }

    @Override
    public void onBindViewHolder(@NonNull IssuesReportVH holder, int position) {
        //holder.bindData(issuesReports.get(position));
        IssuesReport curenitem = issuesReports.get(position);

        holder.tv_sensor_id.setText(curenitem.getSensorID());
        holder.tv_sensor_name.setText(curenitem.getSensorName());
        holder.tv_location.setText(curenitem.getLocation());
        holder.tv_update_time.setText(curenitem.getUpdateTime());
//        holder.lay_isu_repo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                reId = position;
//                notifyDataSetChanged();
//            }
//        });

        if (curenitem.isIdColor()) {
            holder.lay_isu_repo.setBackgroundColor(Color.parseColor("#009688"));
        } else {
            holder.lay_isu_repo.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    @Override
    public int getItemCount() {
        return issuesReports != null ? issuesReports.size() : 0;
    }

    public void filterList(ArrayList<IssuesReport> filteredList) {
        issuesReports = filteredList;
        notifyDataSetChanged();
    }
}

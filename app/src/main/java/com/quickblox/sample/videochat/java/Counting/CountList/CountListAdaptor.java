package com.quickblox.sample.videochat.java.Counting.CountList;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class CountListAdaptor extends RecyclerView.Adapter<CountListAdaptor.CountListViewHolder> {
    Context context;
    private List<CountListItem> countListItems;

    private OnItemClickListener mListener;

    //String Url = com.quickblox.sample.videochat.java.Url.webUrl;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CountListAdaptor(List<CountListItem> noteList, Context icontext) {
        this.context = icontext;
        countListItems = noteList;
    }

    @Override
    public CountListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_count_list,
                parent, false);
        CountListViewHolder evh = new CountListViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull CountListViewHolder holder, int position) {
        holder.bindData(countListItems.get(position));
    }

//    public void addNewNote(CountListItem note) {
//        if (!countListItems.contains(note)) {
//            countListItems.add(note);
//            notifyItemInserted(countListItems.size());
//        }
//    }

    @Override
    public int getItemCount() {
        return countListItems != null ? countListItems.size() : 0;
    }

    class CountListViewHolder extends RecyclerView.ViewHolder {

        TextView tv_log,tv_val_e,tv_val_d,tv_val_a,tv_val_target,tv_val_targethour;
        TextView tv_val_e_lbl;

        public CountListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            tv_log = itemView.findViewById(R.id.tv_log);
            tv_val_e = itemView.findViewById(R.id.tv_val_e);
            tv_val_e_lbl = itemView.findViewById(R.id.tv_val_e_lbl);
            tv_val_d = itemView.findViewById(R.id.tv_val_d);
            tv_val_a = itemView.findViewById(R.id.tv_val_a);
            tv_val_target = itemView.findViewById(R.id.tv_val_target);
            tv_val_targethour = itemView.findViewById(R.id.tv_val_targethour);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position);
                        }
                    }
                }
            });
        }

        public void bindData(CountListItem countListItem) {
            tv_log.setText(countListItem.getLineId()+"/"+countListItem.getLineName());
            //tv_val_e.setText(countListItem.getEfficiency() +"%");
            tv_val_a.setText(countListItem.getActual());
            tv_val_d.setText(countListItem.getDefective());
            tv_val_target.setText(countListItem.getTarget());
            tv_val_targethour.setText(countListItem.getTargetHour());


            double val = Double.parseDouble(countListItem.getEfficiency());
            double min = Double.parseDouble(countListItem.getAlrm_2());
            double max = Double.parseDouble(countListItem.getAlrm_1());

            tv_val_e.setText(String.format("%.0f",val) +"%");

            if (val == 0)
            {
                tv_val_e.setTextColor(context.getResources().getColor(R.color.color_item_grey));
                tv_val_e_lbl.setTextColor(context.getResources().getColor(R.color.color_item_grey));
            } else if (val < min){
                tv_val_e.setTextColor(context.getResources().getColor(R.color.color_item_red));
                tv_val_e_lbl.setTextColor(context.getResources().getColor(R.color.color_item_red));
            } else if (val > min && val < max){
                tv_val_e.setTextColor(context.getResources().getColor(R.color.color_item_yellow));
                tv_val_e_lbl.setTextColor(context.getResources().getColor(R.color.color_item_yellow));
            } else if (val >= max) {
                tv_val_e.setTextColor(context.getResources().getColor(R.color.color_item_green));
                tv_val_e_lbl.setTextColor(context.getResources().getColor(R.color.color_item_green));
            } else {
                tv_val_e.setTextColor(context.getResources().getColor(R.color.red));
                tv_val_e_lbl.setTextColor(context.getResources().getColor(R.color.red));
            }

        }
    }

    public void filterList(ArrayList<CountListItem> filteredList) {
        countListItems = filteredList;
        notifyDataSetChanged();
    }
}

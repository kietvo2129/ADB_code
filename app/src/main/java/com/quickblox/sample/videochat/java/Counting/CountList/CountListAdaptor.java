package com.quickblox.sample.videochat.java.Counting.CountList;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class CountListAdaptor extends RecyclerView.Adapter<CountListAdaptor.CountListViewHolder> {

    private List<CountListItem> countListItems;

    private OnItemClickListener mListener;

    //String Url = com.quickblox.sample.videochat.java.Url.webUrl;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public CountListAdaptor(List<CountListItem> noteList) {
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

        public CountListViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            tv_log = itemView.findViewById(R.id.tv_log);
            tv_val_e = itemView.findViewById(R.id.tv_val_e);
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
            tv_log.setText(countListItem.getLineName());
            tv_val_e.setText(countListItem.getEfficiency());
            tv_val_a.setText(countListItem.getActual());
            tv_val_d.setText(countListItem.getDefective());
            tv_val_target.setText(countListItem.getTarget());
            tv_val_targethour.setText(countListItem.getTargetHour());
        }
    }

    public void filterList(ArrayList<CountListItem> filteredList) {
        countListItems = filteredList;
        notifyDataSetChanged();
    }
}

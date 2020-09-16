package com.autonsi.databoard.Ship;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;

public class PickManualAdapter extends RecyclerView.Adapter<PickManualAdapter.PickManualViewHolder> {

    private ArrayList<PickManualItem> recManItems;
    private OnItemClickListener mListener;

    public PickManualAdapter(ArrayList<PickManualItem> reMaItem) {
        this.recManItems = reMaItem;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);

        void onItemCheck(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public static class PickManualViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout layout_ship;
        public TextView i_mrno, i_mlno, i_mtnm, i_type, i_qty, i_status,i_confirm, i_location, i_word, i_mtno ,i_mrdno;
public CheckBox check;
        public PickManualViewHolder(View itemView, final OnItemClickListener listener) {
            super(itemView);

            // anh xa view holder
            i_mrno = itemView.findViewById(R.id.i_mrno);
            i_mlno = itemView.findViewById(R.id.i_mlno);
            i_mtnm = itemView.findViewById(R.id.i_mtnm);
            i_type = itemView.findViewById(R.id.i_type);
            i_qty = itemView.findViewById(R.id.i_qty);
            i_status = itemView.findViewById(R.id.i_status);
            i_location = itemView.findViewById(R.id.i_location);
            i_confirm = itemView.findViewById(R.id.i_confirm);
            i_word = itemView.findViewById(R.id.i_word);
            i_mtno = itemView.findViewById(R.id.i_mtno);
            i_mrdno = itemView.findViewById(R.id.i_mrdno);

            check = itemView.findViewById(R.id.i_check);
            layout_ship = itemView.findViewById(R.id.layout_ship);
            check.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemCheck(position);
                        }
                    }
                }
            });
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
    }

    @Override
    public PickManualViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pick_manual,
                parent, false);
        PickManualViewHolder evh = new PickManualViewHolder(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(PickManualViewHolder holder, int position) {
        PickManualItem shippingManualItem = recManItems.get(position);

        if (shippingManualItem.isPickstatus() ) { // pick
            holder.layout_ship.setBackgroundColor(Color.parseColor("#00574B"));
        } else if (shippingManualItem.isCheck()) {
            holder.layout_ship.setBackgroundColor(Color.parseColor("#3177BC"));
        } else {
            holder.layout_ship.setBackgroundColor(Color.TRANSPARENT);
        }

        holder.i_mrno.setText(shippingManualItem.getMr_no());
        holder.i_mlno.setText(shippingManualItem.getMl_no());
        holder.i_mtnm.setText(shippingManualItem.getMt_nm());
        holder.i_type.setText(shippingManualItem.getMt_type());
        holder.i_qty.setText(shippingManualItem.getLot_qty());
        holder.i_status.setText(shippingManualItem.getMt_sts_cd());
        holder.i_location.setText(shippingManualItem.getLct_cd());
        holder.i_confirm.setText(shippingManualItem.getWorker());
        holder.i_word.setText(shippingManualItem.getWork_dt());
        holder.i_mtno.setText(shippingManualItem.getMt_cd());
        holder.i_mrdno.setText(shippingManualItem.getMrd_no());
    }

    @Override
    public int getItemCount() {
        return recManItems.size();
    }
}

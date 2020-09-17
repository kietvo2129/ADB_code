package com.autonsi.databoard.Receving.ReceivingOder.OderInformation;

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

public class OrderInformationAdapter extends RecyclerView.Adapter<OrderInformationAdapter.NoteVH> {
    String Url = com.autonsi.databoard.Url.webUrl;

    private List<OrderInformationMaster> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public OrderInformationAdapter(List<OrderInformationMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_order_infor,
                parent, false);
        NoteVH evh = new NoteVH(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }


    @Override
    public int getItemCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }

    class NoteVH extends RecyclerView.ViewHolder {

        public TextView tvvid, tvname, tvType,tvOrderBundle,tvOrderQty,tvBundleQty,tvUnitPrice,tvprice,tvQC;

        LinearLayout lnear;
        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
            tvvid = itemView.findViewById(R.id.tvvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvprice = itemView.findViewById(R.id.tvprice);
            tvType = itemView.findViewById(R.id.tvType);
            tvOrderBundle = itemView.findViewById(R.id.tvOrderBundle);
            tvOrderQty = itemView.findViewById(R.id.tvOrderQty);
            tvBundleQty = itemView.findViewById(R.id.tvBundleQty);
            tvUnitPrice = itemView.findViewById(R.id.tvUnitPrice);
            tvQC = itemView.findViewById(R.id.tvQC);



            lnear= itemView.findViewById(R.id.lnear);
            lnear.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onwarningClick(position);
                        }
                    }
                }
            });


        }


        public void bindData(OrderInformationMaster note) {
            tvvid.setText(note.getMt_cd());
            tvname.setText(note.getMt_nm());
            tvType.setText(note.getType_name());
            tvOrderBundle.setText(note.getQty_bundle()+" "+ note.getUnit_name());
            tvOrderQty.setText(note.getStorage_qty());
            tvBundleQty.setText(note.getQty_bundle());
            tvUnitPrice.setText(note.getUnit_price()+ " " + note.getCurrency_name());
            tvprice.setText(note.getBundle_price()+ " " + note.getCurrency_name());
            tvQC.setText(note.getQc_name()+ " - " + note.getQc_range()+ "%");

            if (!note.getStatus_name().equals("Order")){
                lnear.setBackgroundColor(Color.parseColor("#E3D9BC"));
            }else {
                lnear.setBackgroundColor(Color.parseColor("#FFFFFF"));
            }

        }
    }
    public void filterList(ArrayList<OrderInformationMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

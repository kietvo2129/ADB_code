package com.autonsi.databoard.Receving.ReceivingOder;

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

public class ReceivingAdapter extends RecyclerView.Adapter<ReceivingAdapter.NoteVH> {
    String Url = com.autonsi.databoard.Url.webUrl;
    public static String idsend, ss_nmsend, temp_issue_nmsend;

    private List<ReceivingMaster> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ReceivingAdapter(List<ReceivingMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_receiving_list,
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

        public TextView tvvid, tvname, tvprice;
        public LinearLayout lnear;
        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
            tvvid = itemView.findViewById(R.id.tvvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvprice = itemView.findViewById(R.id.tvprice);
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


        public void bindData(ReceivingMaster note) {
            tvvid.setText(note.getMt_cd());
            tvname.setText(note.getMt_nm());
            tvprice.setText(note.getBundle_price() + " "+ note.getCurrency_name());
        }
    }
    public void filterList(ArrayList<ReceivingMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

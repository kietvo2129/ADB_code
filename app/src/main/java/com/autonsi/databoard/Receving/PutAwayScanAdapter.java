package com.autonsi.databoard.Receving;

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

public class PutAwayScanAdapter extends RecyclerView.Adapter<PutAwayScanAdapter.NoteVH> {
    String Url = com.autonsi.databoard.Url.webUrl;
    public static String idsend, ss_nmsend, temp_issue_nmsend;

    private List<PutAwayScanMaster> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public PutAwayScanAdapter(List<PutAwayScanMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_putaway_scan,
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

        public TextView tvvid, tvname, tvlocation;
        public LinearLayout lnear;
        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
            tvvid = itemView.findViewById(R.id.tvvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvlocation = itemView.findViewById(R.id.tvlocation);
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


        public void bindData(PutAwayScanMaster note) {
            tvvid.setText(note.getMt_lot_cd());
            tvname.setText(note.getMt_nm());
            tvlocation.setText(note.getBin_name());
        }
    }
    public void filterList(ArrayList<PutAwayScanMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

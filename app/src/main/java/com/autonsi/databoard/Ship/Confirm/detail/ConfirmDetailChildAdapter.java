package com.autonsi.databoard.Ship.Confirm.detail;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class ConfirmDetailChildAdapter extends RecyclerView.Adapter<ConfirmDetailChildAdapter.NoteVH> {
    String Url = com.autonsi.databoard.Url.webUrl;
    public static String idsend, ss_nmsend, temp_issue_nmsend;

    
    private List<ConfirmdetailChildMaster> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ConfirmDetailChildAdapter(List<ConfirmdetailChildMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_child_detail_list,
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

        public TextView MLno, ReqQty, timrequest;

        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
            MLno = itemView.findViewById(R.id.MLno);
            ReqQty = itemView.findViewById(R.id.ReqQty);
            timrequest = itemView.findViewById(R.id.timrequest);
        }


        public void bindData(ConfirmdetailChildMaster note) {
            MLno.setText(note.getMt_lot_cd());
            ReqQty.setText(note.getLot_qty());
            timrequest.setText(note.getExpiry_date());
        }
    }
    public void filterList(ArrayList<ConfirmdetailChildMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

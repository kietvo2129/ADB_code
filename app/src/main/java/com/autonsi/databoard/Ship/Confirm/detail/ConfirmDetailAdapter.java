package com.autonsi.databoard.Ship.Confirm.detail;

import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class ConfirmDetailAdapter extends RecyclerView.Adapter<ConfirmDetailAdapter.NoteVH> {
    String Url = com.autonsi.databoard.Url.webUrl;
    public static String idsend, ss_nmsend, temp_issue_nmsend;


    private List<ConfirmdetailMaster> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position, RecyclerView recyclerView);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ConfirmDetailAdapter(List<ConfirmdetailMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_detail_list,
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

        public TextView mrdno, mt_no, mt_nm, req_bundle_qty, req_qty, req_bundle_qty1, req_qty1;
        ImageView image;
        RelativeLayout rl;
        RecyclerView rycviewchild;

        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.image);
            mrdno = itemView.findViewById(R.id.mrdno);
            mt_no = itemView.findViewById(R.id.mt_no);
            mt_nm = itemView.findViewById(R.id.mt_nm);
            req_bundle_qty = itemView.findViewById(R.id.req_bundle_qty);
            req_qty = itemView.findViewById(R.id.req_qty);
            req_bundle_qty1 = itemView.findViewById(R.id.req_bundle_qty1);
            req_qty1 = itemView.findViewById(R.id.req_qty1);
            rycviewchild = itemView.findViewById(R.id.rycviewchild);
            rl = itemView.findViewById(R.id.rl);
            rl.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onwarningClick(position, rycviewchild);
                            if (rycviewchild.getVisibility() == View.VISIBLE) {
                                rycviewchild.setVisibility(View.GONE);
                                image.setBackgroundResource(R.drawable.ic_right);
                            } else {
                                rycviewchild.setVisibility(View.VISIBLE);
                                image.setBackgroundResource(R.drawable.ic_down);
                            }
                        }
                    }
                }
            });
        }


        public void bindData(ConfirmdetailMaster note) {
            rycviewchild.setVisibility(View.GONE);
            image.setBackgroundResource(R.drawable.ic_right);
            mrdno.setText(note.getMrd_no());
            mt_no.setText(note.getMt_no());
            mt_nm.setText(note.getMt_nm());
            req_bundle_qty.setText(note.getReq_bundle_qty());
            req_qty.setText(note.getReq_qty());
            req_bundle_qty1.setText(note.getReq_bundle_qty1());
            req_qty1.setText(note.getReq_qty1());
        }
    }

    public void filterList(ArrayList<ConfirmdetailMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

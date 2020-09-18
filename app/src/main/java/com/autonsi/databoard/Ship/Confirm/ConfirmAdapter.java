package com.autonsi.databoard.Ship.Confirm;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class ConfirmAdapter extends RecyclerView.Adapter<ConfirmAdapter.NoteVH> {
    String Url = com.autonsi.databoard.Url.webUrl;
    public static String idsend, ss_nmsend, temp_issue_nmsend;

    private List<ConfirmMaster> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position);
    }


    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }


    public ConfirmAdapter(List<ConfirmMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_confirm_list,
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

        public TextView MRno, qty, destination,timrequest;
        public RelativeLayout rl;
        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);
            //title = (TextView) itemView.findViewById(R.id.title);
            MRno = itemView.findViewById(R.id.MRno);
            qty = itemView.findViewById(R.id.qty);
            destination = itemView.findViewById(R.id.destination);
            timrequest = itemView.findViewById(R.id.timrequest);

            rl= itemView.findViewById(R.id.rl);
            rl.setOnClickListener(new View.OnClickListener() {
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


        public void bindData(ConfirmMaster note) {
            MRno.setText(note.getMr_no());
            qty.setText(note.getMt_qty());
            destination.setText(note.getLct_nm());
            timrequest.setText(note.getReq_rec_dt());
        }
    }
    public void filterList(ArrayList<ConfirmMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

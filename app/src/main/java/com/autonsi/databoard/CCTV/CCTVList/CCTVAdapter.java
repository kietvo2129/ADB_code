package com.autonsi.databoard.CCTV.CCTVList;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;


public class CCTVAdapter extends RecyclerView.Adapter<CCTVAdapter.NoteVH> {


    private List<CCTVMaster> mNoteList;
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(CCTVAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public CCTVAdapter(List<CCTVMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cctv_list,
                parent, false);
        NoteVH evh = new NoteVH(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }

    public void addNewNote(CCTVMaster note) {
        if (!mNoteList.contains(note)) {
            mNoteList.add(note);
            notifyItemInserted(mNoteList.size());
        }
    }

    @Override
    public int getItemCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }

    class NoteVH extends RecyclerView.ViewHolder {

        public TextView tvid, tvname, tvlocation, tvpress;

        public NoteVH(View itemView, final CCTVAdapter.OnItemClickListener listener) {
            super(itemView);

            tvid = itemView.findViewById(R.id.tvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvlocation = itemView.findViewById(R.id.tvlocation);

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

        public void bindData(CCTVMaster note) {
            tvid.setText(note.getCamera_no());
            tvname.setText(note.getCamera_nm());
            tvlocation.setText(note.getbuilding_code() + "-" + note.getFloor_code());


        }
    }
    public void filterList(ArrayList<CCTVMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }





}

package com.autonsi.databoard.AlarmData.IssuesList;

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

public class DoorHistoryAdapter extends RecyclerView.Adapter<DoorHistoryAdapter.NoteVH> {
    private List<DoorHistoryMaster> mNoteList;
    private OnItemClickListener mListener;
    String Url = com.autonsi.databoard.Url.webUrl;
    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public DoorHistoryAdapter(List<DoorHistoryMaster> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_door_history,
                parent, false);
        NoteVH evh = new NoteVH(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }

    public void addNewNote(DoorHistoryMaster note) {
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

        TextView location,idsensor,nmsensor,time,tvRFID;
        ImageView image;

        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);

            location = itemView.findViewById(R.id.location);
            idsensor = itemView.findViewById(R.id.idsensor);
            nmsensor = itemView.findViewById(R.id.nmsensor);
            time = itemView.findViewById(R.id.time);
            image = itemView.findViewById(R.id.image);
            tvRFID= itemView.findViewById(R.id.tvRFID);

            image.setOnClickListener(new View.OnClickListener() {
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

        public void bindData(DoorHistoryMaster note) {
            idsensor.setText(note.getSs_no());
            nmsensor.setText(note.getSs_nm());
            time.setText(note.getTime());
            tvRFID.setText(note.getRfKey());
            location.setText(note.getBuilding_code()+ " - " + note.getFloor_code());
            Glide.with(itemView.getContext())
                    .load(Url + "Images/MQTT/"+ note.getImg())
                    .error(R.drawable.errorimage)
                    .into(image);
            Log.d("image",Url + "Images/MQTT/"+ note.getImg());

        }
    }
    public void filterList(ArrayList<DoorHistoryMaster> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

package com.quickblox.sample.videochat.java.DigitalData.StatusLayout.MapFloor;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.R;

import java.util.List;

public class MapFloorAdapter extends RecyclerView.Adapter<MapFloorAdapter.NoteVH> {
    private List<MapFloorMatter> mNoteList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(MapFloorAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public MapFloorAdapter(List<MapFloorMatter> noteList) {
        mNoteList = noteList;
    }

    @Override
    public MapFloorAdapter.NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_floor,
                parent, false);
        NoteVH evh = new NoteVH(v, mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }

    public void addNewNote(MapFloorMatter note) {
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

        public TextView no_number, tv_floor;

        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);

            no_number = itemView.findViewById(R.id.no_number);
            tv_floor = itemView.findViewById(R.id.tv_floor);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(v,position);
                        }
                    }
                }
            });
        }

        public void bindData(MapFloorMatter note) {
            no_number.setText(note.getNo());
            tv_floor.setText(note.getFloor_name());
        }
    }

}

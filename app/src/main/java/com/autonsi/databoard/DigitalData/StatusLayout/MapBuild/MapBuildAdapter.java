package com.autonsi.databoard.DigitalData.StatusLayout.MapBuild;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.quickblox.sample.videochat.java.R;

import java.util.List;

public class MapBuildAdapter extends RecyclerView.Adapter<MapBuildAdapter.NoteVH> {
    private List<MapBuildMatter> mNoteList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(View view,int position);
    }

    public void setOnItemClickListener(MapBuildAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    public MapBuildAdapter(List<MapBuildMatter> noteList) {
        mNoteList = noteList;
    }

    @Override
    public MapBuildAdapter.NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mapbuild,
                parent, false);
        NoteVH evh = new NoteVH(v, mListener);
        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }

    public void addNewNote(MapBuildMatter note) {
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

        public TextView building_name, city_code;

        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);

            building_name = itemView.findViewById(R.id.building_name);
            city_code = itemView.findViewById(R.id.city_code);

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

        public void bindData(MapBuildMatter note) {
            building_name.setText(note.getBuilding_name());
            city_code.setText(note.getCity_code());
        }
    }

}

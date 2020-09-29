package com.autonsi.databoard.Counting.Count.QCCheck;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.quickblox.sample.videochat.java.R;

import java.text.DecimalFormat;
import java.util.List;


public class QCcheckAdapter extends RecyclerView.Adapter<QCcheckAdapter.NoteVH> {
    private QCcheckAdapter.OnItemClickListener mListener;
    Context context;
    public static List<QCcheckMaster> mNoteList;

    public static int row_index=-1;
    public QCcheckAdapter(List<QCcheckMaster> noteList) {
        mNoteList = noteList;
    }
    public interface OnItemClickListener {
        void onItemClick(int position);
        void onItemEditText(int position);
        void onItemButtonUp(int position,TextView edittext);
        void onItemButtonDown(int position,TextView edittext);
    }

    public void setOnItemClickListener(QCcheckAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public QCcheckAdapter.NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.popup_qc_check,parent,false);
        QCcheckAdapter.NoteVH evh = new QCcheckAdapter.NoteVH(view,mListener);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull QCcheckAdapter.NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }


    @Override
    public int getItemCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }
    class NoteVH extends RecyclerView.ViewHolder {

        TextView tv_titel,tv_sub,tv_tong;
        ImageButton btUp,btDown;

        public NoteVH(View itemView, final QCcheckAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_titel = itemView.findViewById(R.id.tv_titel);
            tv_sub = itemView.findViewById(R.id.tv_sub);
            tv_tong = itemView.findViewById(R.id.textView_food_1);

            btUp = itemView.findViewById(R.id.imgButton_up1);
            btDown = itemView.findViewById(R.id.imgButton_down1);

            //Edittext
            tv_tong.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemEditText(position);
                        }
                    }
                }
            });

            // +++++++++++
            btUp.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemButtonUp(position,tv_tong);
                        }
                    }
                }
            });

            // ----------
            btDown.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemButtonDown(position,tv_tong);
                        }
                    }
                }
            });

        }

        public void bindData(QCcheckMaster note) {
            tv_titel.setText(note.qc_check_subject_name);
            tv_sub.setText(note.qc_check_detail_name);
            tv_tong.setText(note.tong+"");
        }
    }
}

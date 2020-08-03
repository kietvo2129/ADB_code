package com.quickblox.sample.videochat.java.DigitalData.IssuesList;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.quickblox.sample.videochat.java.DigitalData.IssuesReport.IssuesReport;
import com.quickblox.sample.videochat.java.R;

import java.util.ArrayList;
import java.util.List;

public class IssuesAdapter extends RecyclerView.Adapter<IssuesAdapter.NoteVH> {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    public static String idsend, ss_nmsend, temp_issue_nmsend;
    private List<IssuesMater> mNoteList;
    private OnItemClickListener mListener;
    //private OnCheckedChangeListener mListener2;

    public interface OnItemClickListener {
        void onwarningClick(int position);
        void onsendClick(int position);
    }

//    public interface OnCheckedChangeListener {
//        void onbuttonClick(int position, boolean b);
//    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

//    public void setOnCheckedChangeListener(OnCheckedChangeListener listener) {
//        mListener2 = listener;
//    }

    public IssuesAdapter(List<IssuesMater> noteList) {
        mNoteList = noteList;
    }

    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_issues,
                parent, false);
        NoteVH evh = new NoteVH(v, mListener);
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }

    public void addNewNote(IssuesMater note) {
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

        public TextView tvid, tvname, tvtemp, tvhum, tvlocation, tvtime, tvpow, tvpress, info_warning, sts_send, tvtimeend;
        private LinearLayout lnear;
        private ImageView icon_temp, icon_hum, icon_press, icon_pow;
        //Button buttontoggle;
        private RelativeLayout rlpow,rlpress,rltemp,rlhum;
        public NoteVH(View itemView, final OnItemClickListener listener) {
            super(itemView);

            //title = (TextView) itemView.findViewById(R.id.title);
            tvid = itemView.findViewById(R.id.tvid);
            tvname = itemView.findViewById(R.id.tvname);
            tvtemp = itemView.findViewById(R.id.tvtemp);
            tvhum = itemView.findViewById(R.id.tvhum);
            tvpress = itemView.findViewById(R.id.tvpress);
            tvpow = itemView.findViewById(R.id.tvpow);
            tvlocation = itemView.findViewById(R.id.tvlocation);
            tvtime = itemView.findViewById(R.id.tvtime);
            icon_temp = itemView.findViewById(R.id.icon_temp);
            icon_hum = itemView.findViewById(R.id.icon_hum);
            icon_press = itemView.findViewById(R.id.icon_press);
            icon_pow = itemView.findViewById(R.id.icon_pow);
            lnear = itemView.findViewById(R.id.lnear);
            //buttontoggle = itemView.findViewById(R.id.buttontoggle);

            rlpow = itemView.findViewById(R.id.rlpow);
            rlpress = itemView.findViewById(R.id.rlpress);
            rltemp = itemView.findViewById(R.id.rltemp);
            rlhum = itemView.findViewById(R.id.rlhum);

            info_warning = itemView.findViewById(R.id.info_warning);
            sts_send = itemView.findViewById(R.id.sts_send);
            tvtimeend = itemView.findViewById(R.id.tvtimeend);


            info_warning.setOnClickListener(new View.OnClickListener() {
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
            sts_send.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onsendClick(position);
                        }
                    }
                }
            });



//            info_warning.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (mNoteList.get(position).getInfo_warning().equals("null")) {
//                        idsend = mNoteList.get(position).getId();
//                        ss_nmsend = mNoteList.get(position).getSs_nm();
//                        temp_issue_nmsend = mNoteList.get(position).getTemp_issue_nm();
//                        Intent intent = new Intent(view.getContext(), IssuesDetailActivity.class);
//                        view.getContext().startActivity(intent);
//                    } else {
//                        idsend = mNoteList.get(position).getId();
//                        Intent intent = new Intent(view.getContext(), IssuesDetailnullActivity.class);
//                        view.getContext().startActivity(intent);
//                    }
//
//                }
//            });
//
//
//
//
//            sts_send.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    int position = getAdapterPosition();
//                    if (mNoteList.get(position).getSts_send().equals("Pending")) {
//                        idsend = mNoteList.get(position).getId();
//                        ss_nmsend = mNoteList.get(position).getSs_nm();
//                        temp_issue_nmsend = mNoteList.get(position).getTemp_issue_nm();
//                        Intent intent = new Intent(view.getContext(), IssuesActionActivity.class);
//                        view.getContext().startActivity(intent);
//                    }
//                }
//            });

//            buttontoggle.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onbuttonClick(position,buttontoggle);
//                        }
//                    }
//                }
//            });



        }



        public void bindData(IssuesMater note) {

            tvid.setText(note.getSs_no());
            tvname.setText(note.getSs_nm());
            tvlocation.setText(note.getBuilding_name() + " - " + note.getFloor_name());
            tvtime.setText(note.getTime_update());

            if (note.getInfo_warning().equals("null")) {
                info_warning.setText("Write inform");
                info_warning.setBackgroundColor(Color.parseColor("#E74C3C"));
            } else {
                info_warning.setText(note.getInfo_warning());
                info_warning.setBackgroundColor(Color.parseColor("#0000FF"));
            }

            if (note.getSts_send().equals("null")) {
                sts_send.setText("Not Informed");
                sts_send.setBackgroundColor(Color.parseColor("#FF9800"));
            } else {
                if (note.getSts_send().equals("Pending")) {
                    sts_send.setBackgroundColor(Color.parseColor("#008000"));
                    sts_send.setText(note.getSts_send());
                } else {
                    sts_send.setBackgroundColor(Color.parseColor("#FF9800"));
                    sts_send.setText(note.getSts_send());
                }

            }

            if (note.getTime_finish().equals("null")) {
                tvtimeend.setText("");
            } else {
                tvtimeend.setText(note.getTime_finish());
            }

            if (!note.getCurrent_temp().equals("")){
                rltemp.setVisibility(View.VISIBLE);
                tvtemp.setText(note.getCurrent_temp() + "°C");
                if (note.getTemp_issue().equals("003")) {
                    icon_temp.setBackgroundResource(R.drawable.ic_temgreen);
                } else {
                    icon_temp.setBackgroundResource(R.drawable.ic_temred);
                }
            }else {
                rltemp.setVisibility(View.GONE);
            }

            if (!note.getCurrent_humi().equals("")){
                rlhum.setVisibility(View.VISIBLE);
                tvhum.setText(note.getCurrent_humi() + "%");
                if (note.getHumi_issue().equals("006")) {
                    icon_hum.setBackgroundResource(R.drawable.ic_humgreen);
                } else {
                    icon_hum.setBackgroundResource(R.drawable.ic_humred);
                }
            }else {
                rlhum.setVisibility(View.GONE);
            }

            if (!note.getCurrent_press().equals("")){
                rlpress.setVisibility(View.VISIBLE);
                tvpress.setText(note.getCurrent_press() + "Pa");
                if (note.getPress_issue().equals("009")) {
                    icon_press.setBackgroundResource(R.drawable.ic_pressgreen);
                } else {
                    icon_press.setBackgroundResource(R.drawable.ic_pressred);
                }
            }else {
                rlpress.setVisibility(View.GONE);
            }

            if (!note.getCurrent_pow().equals("")){
                rlpow.setVisibility(View.VISIBLE);
                tvpow.setText(note.getCurrent_pow() + "Pa");
                if (note.getPow_issue().equals("012")) {
                    icon_pow.setBackgroundResource(R.drawable.ic_powgreen);
                } else {
                    icon_pow.setBackgroundResource(R.drawable.ic_powred);
                }
            }else {
                rlpow.setVisibility(View.GONE);
            }

        }
    }
    public void filterList(ArrayList<IssuesMater> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }
}

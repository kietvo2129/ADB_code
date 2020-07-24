package com.quickblox.sample.videochat.java.DigitalData.SensorList;

import android.graphics.Color;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.quickblox.sample.videochat.java.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SensorAdapter extends RecyclerView.Adapter<SensorAdapter.NoteVH> {
    String Url = com.quickblox.sample.videochat.java.Url.webUrl;
    private List<SensorMatter> mNoteList;
    private OnItemClickListener mListener;
    public interface OnItemClickListener {
        void onItemClick(int position,Button button);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    public SensorAdapter(List<SensorMatter> noteList) {
        mNoteList = noteList;
    }
    @Override
    public NoteVH onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_history,
                parent, false);

        NoteVH evh = new NoteVH(v, mListener);

        return evh;
    }
    @Override
    public void onBindViewHolder(@NonNull NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }

    public void addNewNote(SensorMatter note) {
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

        public TextView tvid, tvname, tvtemp, tvhum, tvlocation, tvtime,tvpow,tvpress;
        private LinearLayout lnear;
        private ImageView icon_temp, icon_hum,icon_press,icon_pow;
        private RelativeLayout rlpow,rlpress,rltemp,rlhum;
        Button buttontoggle;
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
            rlpow = itemView.findViewById(R.id.rlpow);
            rlpress = itemView.findViewById(R.id.rlpress);
            rltemp = itemView.findViewById(R.id.rltemp);
            rlhum = itemView.findViewById(R.id.rlhum);
            buttontoggle = itemView.findViewById(R.id.buttontoggle);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    if (listener != null) {
//                        int position = getAdapterPosition();
//                        if (position != RecyclerView.NO_POSITION) {
//                            listener.onItemClick(position);
//                        }
//                    }
//                }
//            });
            buttontoggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (listener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            listener.onItemClick(position,buttontoggle);
                        }
                    }
                }
            });
        }
        private class getOn extends AsyncTask<String, Void, String> {
            @Override
            protected String doInBackground(String... strings) {
                return com.quickblox.sample.videochat.java.Url.NoiDung_Tu_URL(strings[0]);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                try {
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("result").replace("[\"", "").replace("\"]", "").equals("001")) {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_on);
                        buttontoggle.setText("ON");
                    } else {
                        buttontoggle.setBackgroundResource(R.drawable.toggle_state_off);
                        buttontoggle.setText("OFF");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }

        public void bindData(SensorMatter note) {
            new NoteVH.getOn().execute(Url + "Monitor/Searchnhietdo?ss_no=" + note.getSs_no());
            Log.d("getOn", Url + "Monitor/Searchnhietdo?ss_no=" + note.getSs_no());
            tvid.setText(note.getSs_no());
            tvname.setText(note.getSs_nm());
            if (note.getSs_no().equals("SS-18")){
                buttontoggle.setVisibility(View.GONE);
            }

            tvpow.setText(note.getCurrent_pow() + "W");

            tvlocation.setText(note.getBuilding_name() + " - "+ note.getFloor_name());
            tvtime.setText(note.getTime_update());

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
                tvpow.setText(note.getCurrent_pow() + "W");
                if (note.getPow_issue().equals("012")) {
                    icon_pow.setBackgroundResource(R.drawable.ic_powgreen);
                } else {
                    icon_pow.setBackgroundResource(R.drawable.ic_powred);
                }
            }else {
                rlpow.setVisibility(View.GONE);
            }

            if (note.getTemp_issue().equals("003") && note.getHumi_issue().equals("006")
                    &&note.getPress_issue().equals("009")&&note.getPow_issue().equals("012")) {
                lnear.setBackgroundColor(Color.parseColor("#4F01BD"));
            } else {
                lnear.setBackgroundColor(Color.parseColor("#FF9800"));
            }
        }
    }


    public void filterList(ArrayList<SensorMatter> filteredList) {
        mNoteList = filteredList;
        notifyDataSetChanged();
    }

}

package com.autonsi.databoard.Barcode;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.ThumbnailUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.quickblox.sample.videochat.java.R;

import java.text.DecimalFormat;
import java.util.List;


public class BarcodeAdapter extends RecyclerView.Adapter<BarcodeAdapter.NoteVH> {
    private BarcodeAdapter.OnItemClickListener mListener;
    Context context;
    public static List<BarcodeMaster> mNoteList;


    public static int row_index = -1;

    public BarcodeAdapter(List<BarcodeMaster> noteList) {
        mNoteList = noteList;
    }

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(BarcodeAdapter.OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public BarcodeAdapter.NoteVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_custom_barcode, parent, false);
        BarcodeAdapter.NoteVH evh = new BarcodeAdapter.NoteVH(view, mListener);
        context = parent.getContext();
        return evh;
    }

    @Override
    public void onBindViewHolder(@NonNull BarcodeAdapter.NoteVH holder, int position) {
        holder.bindData(mNoteList.get(position));
    }


    @Override
    public int getItemCount() {
        return mNoteList != null ? mNoteList.size() : 0;
    }

    class NoteVH extends RecyclerView.ViewHolder {
        TextView tv_barcode,no;
        ImageView img_barcode;
        CheckBox checkbox;

        public NoteVH(View itemView, final BarcodeAdapter.OnItemClickListener listener) {
            super(itemView);
            tv_barcode = itemView.findViewById(R.id.tv_barcode);
            img_barcode = itemView.findViewById(R.id.img_barcode);
            no =  itemView.findViewById(R.id.no);
            checkbox=  itemView.findViewById(R.id.checkbox);

        }

        public void bindData(BarcodeMaster note) {
            tv_barcode.setText(note.getBarcode());
            no.setText(note.getNo());
            img_barcode.setImageBitmap(ima_barcode(note.getBarcode()));
            checkbox.setChecked(note.checked);
            checkbox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    boolean newState = !note.isChecked();
                    note.checked = newState;
                }
            });
        }
    }

    public static Bitmap ima_barcode(String val) {
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = null;
        try {
            bitMatrix = qrCodeWriter.encode(val, BarcodeFormat.QR_CODE, 200, 200);
        } catch (WriterException e) {
            e.printStackTrace();
        }

        int height = bitMatrix.getHeight();
        int width = bitMatrix.getWidth();
        final Bitmap bmp = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_4444);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                bmp.setPixel(x, y, bitMatrix.get(x, y) ? Color.BLACK : Color.WHITE);
            }
        }
        return bmp;
    }

}

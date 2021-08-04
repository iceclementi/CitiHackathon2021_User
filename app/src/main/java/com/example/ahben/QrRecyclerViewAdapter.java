package com.example.ahben;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class QrRecyclerViewAdapter extends RecyclerView.Adapter<QrRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "QrRVA";

    private ArrayList<MyVoucher> vouchers = new ArrayList<>();
    private Context context;
    private String parentActivity;

    private Dialog popupDialog;

    public QrRecyclerViewAdapter(Context context, String parentActivity) {
        this.context = context;
        this.parentActivity = parentActivity;

        popupDialog = new Dialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.qr_voucher_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull QrRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        Log.d(TAG, holder.voucherTitle.toString());
        holder.voucherTitle.setText(vouchers.get(position).getTitle());
        holder.voucherDetails.setText(vouchers.get(position).getDetails());
        holder.voucherExpiry.setText("Expires on " + vouchers.get(position).getExpiryDate());
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public void setVouchers(ArrayList<MyVoucher> vouchers) {
        this.vouchers = vouchers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView voucherTitle;
        private TextView voucherDetails;
        private TextView voucherExpiry;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            voucherTitle = itemView.findViewById(R.id.qrVoucherItemTitle);
            voucherDetails = itemView.findViewById(R.id.qrVoucherItemDetails);
            voucherExpiry = itemView.findViewById(R.id.qrVoucherItemExpiry);

            setListeners();
        }

        private void setListeners() {
            parent.setOnClickListener(v -> {
                MyVoucher voucher = vouchers.get(getAdapterPosition());

                ImageView qrCode;
                TextView popupTitle;
                TextView popupDetails;
                TextView popupExpiry;
                Button cancelButton;

                popupDialog.setContentView(R.layout.qr_code_popup);

                qrCode = popupDialog.findViewById(R.id.qrCodePopupCode);
                popupTitle = popupDialog.findViewById(R.id.qrCodePopupTitle);
                popupDetails = popupDialog.findViewById(R.id.qrCodePopupDetails);
                popupExpiry = popupDialog.findViewById(R.id.qrCodePopupExpiry);
                cancelButton = popupDialog.findViewById(R.id.qrCodePopupCancelButton);

                QRGEncoder encoder = new QRGEncoder(voucher.toString(), null, QRGContents.Type.TEXT, 128);
                qrCode.setImageBitmap(encoder.getBitmap());
                popupTitle.setText(voucher.getTitle());
                popupDetails.setText(voucher.getDetails());
                popupExpiry.setText("Expires on " + voucher.getExpiryDate());

                cancelButton.setOnClickListener(l -> popupDialog.dismiss());

                popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupDialog.show();
            });
        }
    }
}

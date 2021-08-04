package com.example.savelah;

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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class VoucherRecyclerViewAdapter extends RecyclerView.Adapter<VoucherRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "VoucherRVA";

    private ArrayList<Voucher> vouchers = new ArrayList<>();
    private Context context;
    private String parentActivity;

    private Dialog popupDialog;

    public VoucherRecyclerViewAdapter(Context context, String parentActivity) {
        this.context = context;
        this.parentActivity = parentActivity;

        popupDialog = new Dialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VoucherRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.voucherTitle.setText(vouchers.get(position).getTitle());
        holder.voucherDetails.setText(vouchers.get(position).getDetails());
        holder.voucherCost.setText("$" + vouchers.get(position).getCost());
    }

    @Override
    public int getItemCount() {
        return vouchers.size();
    }

    public void setVouchers(ArrayList<Voucher> vouchers) {
        this.vouchers = vouchers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private CardView parent;
        private TextView voucherTitle;
        private TextView voucherDetails;
        private TextView voucherCost;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            parent = itemView.findViewById(R.id.parent);
            voucherTitle = itemView.findViewById(R.id.voucherItemTitle);
            voucherDetails = itemView.findViewById(R.id.voucherItemDetails);
            voucherCost = itemView.findViewById(R.id.voucherItemCost);

            setListeners();
        }

        private void setListeners() {
            parent.setOnClickListener(v -> {
                Voucher voucher = vouchers.get(getAdapterPosition());

                TextView popupTitle;
                TextView popupDetails;
                TextView popupCost;
                TextView popupLoyalty;
                TextView popupExpiry;
                Button purchaseButton;
                Button cancelButton;

                popupDialog.setContentView(R.layout.voucher_popup);
                popupTitle = popupDialog.findViewById(R.id.voucherPopupTitle);
                popupDetails = popupDialog.findViewById(R.id.voucherPopupDetails);
                popupCost = popupDialog.findViewById(R.id.voucherPopupCost);
                popupLoyalty = popupDialog.findViewById(R.id.voucherPopupLoyalty);
                popupExpiry = popupDialog.findViewById(R.id.voucherPopupExpiry);
                purchaseButton = popupDialog.findViewById(R.id.voucherPopupPurchaseButton);
                cancelButton = popupDialog.findViewById(R.id.voucherPopupCancelButton);

                popupTitle.setText(voucher.getTitle());
                popupDetails.setText(voucher.getDetails());
                popupCost.setText("$" + voucher.getCost());
                popupLoyalty.setText(voucher.getLoyaltyBonus() + "LP");
                popupExpiry.setText(voucher.getValidity() + " DAYS");

                purchaseButton.setOnClickListener(l -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to purchase the " + voucher.getTitle() + " voucher?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        if (Database.getInstance(context).addToMyVouchers(new MyVoucher(voucher))) {
                            Database.getInstance(context).addLP(voucher.getLoyaltyBonus());
                            Toast.makeText(context, "Voucher Purchased", Toast.LENGTH_SHORT).show();
                            notifyDataSetChanged();
                        } else {
                            Toast.makeText(context, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                        }

                        popupDialog.dismiss();
                    });
                    builder.setNegativeButton("No", (dialog, which) -> popupDialog.dismiss());
                    builder.create().show();
                });

                cancelButton.setOnClickListener(l -> popupDialog.dismiss());

                popupDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                popupDialog.show();
            });
        }
    }
}

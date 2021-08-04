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

public class LoyaltyVoucherRecyclerViewAdapter extends RecyclerView.Adapter<LoyaltyVoucherRecyclerViewAdapter.ViewHolder> {
    private static final String TAG = "LoyaltyVoucherRVA";

    private ArrayList<Voucher> vouchers = new ArrayList<>();
    private Context context;
    private TextView loyaltyPoints;

    private Dialog popupDialog;

    public LoyaltyVoucherRecyclerViewAdapter(Context context, TextView loyaltyPoints) {
        this.context = context;
        this.loyaltyPoints = loyaltyPoints;

        popupDialog = new Dialog(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.voucher_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull LoyaltyVoucherRecyclerViewAdapter.ViewHolder holder, int position) {
        Log.d(TAG, "onBindViewHolder: Called");
        holder.voucherTitle.setText(vouchers.get(position).getTitle());
        holder.voucherDetails.setText(vouchers.get(position).getDetails());
        holder.voucherCost.setText(vouchers.get(position).getCost() + "LP");
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
                TextView popupExpiry;
                Button purchaseButton;
                Button cancelButton;

                popupDialog.setContentView(R.layout.loyalty_voucher_popup);
                popupTitle = popupDialog.findViewById(R.id.voucherPopupTitle);
                popupDetails = popupDialog.findViewById(R.id.voucherPopupDetails);
                popupCost = popupDialog.findViewById(R.id.voucherPopupCost);
                popupExpiry = popupDialog.findViewById(R.id.voucherPopupExpiry);
                purchaseButton = popupDialog.findViewById(R.id.voucherPopupPurchaseButton);
                cancelButton = popupDialog.findViewById(R.id.voucherPopupCancelButton);

                popupTitle.setText(voucher.getTitle());
                popupDetails.setText(voucher.getDetails());
                popupCost.setText(voucher.getCost() + "LP");
                popupExpiry.setText(voucher.getValidity() + " DAYS");

                purchaseButton.setOnClickListener(l -> {
                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to purchase the " + voucher.getTitle() + " voucher?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {

                        if (Database.getInstance(context).removeLP(voucher.getCost())) {
                            if (Database.getInstance(context).addToMyVouchers(new MyVoucher(voucher))) {
                                loyaltyPoints.setText(String.valueOf(Database.getInstance(context).getMyLP()));
                                Toast.makeText(context, "Voucher Purchased", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(context, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(context, "Oh no! You don't have enough Loyalty Points!", Toast.LENGTH_SHORT).show();
                        }

                        popupDialog.dismiss();
                        notifyDataSetChanged();
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

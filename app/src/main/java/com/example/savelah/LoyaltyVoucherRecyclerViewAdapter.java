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
import android.widget.EditText;
import android.widget.ImageView;
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

                EditText inputQuantity;
                ImageView decrementButton;
                ImageView incrementButton;

                popupDialog.setContentView(R.layout.loyalty_voucher_popup);
                popupTitle = popupDialog.findViewById(R.id.voucherPopupTitle);
                popupDetails = popupDialog.findViewById(R.id.voucherPopupDetails);
                popupCost = popupDialog.findViewById(R.id.voucherPopupCost);
                popupExpiry = popupDialog.findViewById(R.id.voucherPopupExpiry);
                purchaseButton = popupDialog.findViewById(R.id.voucherPopupPurchaseButton);
                cancelButton = popupDialog.findViewById(R.id.voucherPopupCancelButton);

                inputQuantity = popupDialog.findViewById(R.id.inputQuantity);
                decrementButton = popupDialog.findViewById(R.id.decrementButton);
                incrementButton = popupDialog.findViewById(R.id.incrementButton);

                popupTitle.setText(voucher.getTitle());
                popupDetails.setText(voucher.getDetails());
                popupCost.setText(voucher.getCost() + "LP");
                popupExpiry.setText(voucher.getValidity() + " DAYS");

                decrementButton.setOnClickListener(l -> {
                    String updatedQuantity = decrement(inputQuantity.getText().toString());
                    inputQuantity.setText(updatedQuantity);
                });
                incrementButton.setOnClickListener(l -> {
                    String updatedQuantity = increment(inputQuantity.getText().toString());
                    inputQuantity.setText(updatedQuantity);
                });

                purchaseButton.setOnClickListener(l -> {
                    int quantity = getQuantity(inputQuantity.getText().toString());
                    if (quantity < 1) {
                        Toast.makeText(context, "Invalid Quantity", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    AlertDialog.Builder builder = new AlertDialog.Builder(context);
                    builder.setMessage("Are you sure you want to purchase the " + quantity + " " +  voucher.getTitle() + " voucher(s)?");
                    builder.setPositiveButton("Yes", (dialog, which) -> {
                        if (Database.getInstance(context).removeLP(voucher.getCost() * quantity)) {
                            for (int i = 0; i < quantity; ++i) {
                                if (Database.getInstance(context).addToMyVouchers(new MyVoucher(voucher))) {
                                    loyaltyPoints.setText(String.valueOf(Database.getInstance(context).getMyLP()));
                                    Toast.makeText(context, "Voucher(s) Purchased", Toast.LENGTH_SHORT).show();
                                } else {
                                    Toast.makeText(context, "Something wrong happened, try again", Toast.LENGTH_SHORT).show();
                                    break;
                                }
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

        private int getQuantity(String valueString) {
            try {
                int value = Integer.parseInt(valueString);
                return value >= 1 && value <= 99 ? value : 0;
            } catch (NumberFormatException e) {
                return 0;
            }
        }

        private String increment(String valueString) {
            try {
                int value = Integer.parseInt(valueString);
                if (value < 99) {
                    value += 1;
                }
                return String.valueOf(value);
            } catch (NumberFormatException e) {
                return "1";
            }
        }

        private String decrement(String valueString) {
            try {
                int value = Integer.parseInt(valueString);
                if (value > 1) {
                    value -= 1;
                }
                return String.valueOf(value);
            } catch (NumberFormatException e) {
                return "1";
            }
        }
    }
}

package com.example.ahben.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahben.Database;
import com.example.ahben.Voucher;
import com.example.ahben.VoucherRecyclerViewAdapter;
import com.example.ahben.databinding.FragmentShopBinding;

import java.util.ArrayList;

public class ShopFragment extends Fragment {

    private ShopViewModel shopViewModel;
    private FragmentShopBinding binding;

    private RecyclerView voucherRecyclerView;
    private VoucherRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shopViewModel =
                new ViewModelProvider(this).get(ShopViewModel.class);

        binding = FragmentShopBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

//        final TextView textView = binding.textShop;
//        shopViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
//            public void onChanged(@Nullable String s) {
//                textView.setText(s);
//            }
//        });

        voucherRecyclerView = binding.shopRecyclerView;

        adapter = new VoucherRecyclerViewAdapter(getContext(), "Shop");
        voucherRecyclerView.setAdapter(adapter);
        voucherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setVouchers(Database.getInstance(getContext()).getStoreVouchers());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
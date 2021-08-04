package com.example.savelah.ui.shop;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savelah.Database;
import com.example.savelah.VoucherRecyclerViewAdapter;
import com.example.savelah.databinding.FragmentShopBinding;

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
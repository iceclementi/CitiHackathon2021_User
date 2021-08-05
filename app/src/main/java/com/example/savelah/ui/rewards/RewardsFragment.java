package com.example.savelah.ui.rewards;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.savelah.Database;
import com.example.savelah.LoyaltyVoucherRecyclerViewAdapter;
import com.example.savelah.databinding.FragmentRewardsBinding;

public class RewardsFragment extends Fragment {

    private RewardsViewModel rewardsViewModel;
    private FragmentRewardsBinding binding;

    private RecyclerView voucherRecyclerView;
    private LoyaltyVoucherRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        rewardsViewModel =
                new ViewModelProvider(this).get(RewardsViewModel.class);

        binding = FragmentRewardsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView loyaltyPoints = binding.loyaltyPoints;
        loyaltyPoints.setText(String.valueOf(Database.getInstance(getContext()).getMyLP()));

        voucherRecyclerView = binding.profileRecyclerView;

        adapter = new LoyaltyVoucherRecyclerViewAdapter(getContext(), loyaltyPoints);

        voucherRecyclerView.setAdapter(adapter);
        voucherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setVouchers(Database.getInstance(getContext()).getLoyaltyVouchers());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
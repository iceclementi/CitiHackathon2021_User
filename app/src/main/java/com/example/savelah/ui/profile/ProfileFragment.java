package com.example.savelah.ui.profile;

import android.content.SharedPreferences;
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
import com.example.savelah.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel notificationsViewModel;
    private FragmentProfileBinding binding;

    private RecyclerView voucherRecyclerView;
    private LoyaltyVoucherRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        notificationsViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
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

    private void setLister() {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
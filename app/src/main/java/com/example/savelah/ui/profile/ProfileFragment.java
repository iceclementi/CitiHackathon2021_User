package com.example.savelah.ui.profile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.savelah.Database;
import com.example.savelah.LoginActivity;
import com.example.savelah.MainActivity;
import com.example.savelah.databinding.FragmentProfileBinding;

public class ProfileFragment extends Fragment {

    private ProfileViewModel profileViewModel;
    private FragmentProfileBinding binding;

    private ImageView walletButton;
    private TextView wallet;
    private TextView loyaltyPoints;
    private Button logoutButton;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        walletButton = binding.walletButton;
        wallet = binding.wallet;
        loyaltyPoints = binding.loyaltyPoints;
        logoutButton = binding.logoutButton;

        wallet.setText(String.valueOf(Database.getInstance(getContext()).getWallet()));
        loyaltyPoints.setText(String.valueOf(Database.getInstance(getContext()).getMyLP()));

        setListeners();

        return root;
    }

    private void setListeners() {
        walletButton.setOnClickListener(v -> {
            Database.getInstance(getContext()).addToWallet(20);
            wallet.setText(String.valueOf(Database.getInstance(getContext()).getWallet()));
        });

        logoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
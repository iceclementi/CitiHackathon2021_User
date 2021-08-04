package com.example.ahben.ui.home;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ahben.Database;
import com.example.ahben.QrRecyclerViewAdapter;
import com.example.ahben.databinding.FragmentHomeBinding;

import androidmads.library.qrgenearator.QRGContents;
import androidmads.library.qrgenearator.QRGEncoder;

public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private FragmentHomeBinding binding;

    private RecyclerView qrVoucherRecyclerView;
    private QrRecyclerViewAdapter adapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                new ViewModelProvider(this).get(HomeViewModel.class);

        binding = FragmentHomeBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        qrVoucherRecyclerView = binding.homeRecyclerView;

        adapter = new QrRecyclerViewAdapter(getContext(), "Home");
        qrVoucherRecyclerView.setAdapter(adapter);
        qrVoucherRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        adapter.setVouchers(Database.getInstance(getContext()).getMyVouchers());

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
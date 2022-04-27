package com.example.sep4android;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class NewRoom extends Fragment {
    UserViewModel viewModel;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        System.out.println("New Room");
        viewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects -> System.out.println("Wolo " + listObjects.get(0).getRoomId()));
        return inflater.inflate(R.layout.fragment_new_room, container, false);
    }
}
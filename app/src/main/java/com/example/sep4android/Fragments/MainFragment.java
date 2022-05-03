package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.R;
import com.example.sep4android.RoomAdapter;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class MainFragment extends Fragment implements RoomAdapter.OnListItemClickListener {
    RoomViewModel viewModel;
    View view;
    FloatingActionButton fab;
    RecyclerView roomsRV;
    RoomAdapter roomAdapter;


    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("MainView");
        view = inflater.inflate(R.layout.main_layout, container, false);
        findViews(view);
        setListenersToButtons();
        roomsRV.hasFixedSize();
        roomsRV.setLayoutManager(new LinearLayoutManager(getContext()));
        viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
        roomAdapter = new RoomAdapter(this);
        viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects ->   roomAdapter.update(listObjects));
        roomsRV.setAdapter(roomAdapter);
        return view;
    }

    private void findViews(View view) {
        fab = view.findViewById(R.id.fab);
        roomsRV = view.findViewById(R.id.room_rv);
    }

    private void setListenersToButtons() {
        fab.setOnClickListener(view -> {
                    NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
                    navController.navigate(R.id.CreateRoom);
                }
        );
    }

    @Override
    public void onListItemClick(RoomObject clickedItemIndex) {
        Toast.makeText(getContext(), "Room: " + clickedItemIndex.getRoomId(), Toast.LENGTH_SHORT).show();
    }
}

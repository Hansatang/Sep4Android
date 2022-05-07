package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapters.MeasurementAdapter;
import com.example.sep4android.Objects.MeasurementObject;
import com.example.sep4android.R;
import com.example.sep4android.Adapters.RoomAdapter;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.ViewModels.MeasurementViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;

public class MainFragment extends Fragment implements RoomAdapter.OnListItemClickListener {
  MeasurementViewModel measurementViewModel;
  RoomViewModel viewModel;
  View view;
  FloatingActionButton fab;
  RecyclerView roomsRV;
  MeasurementAdapter measurementAdapter;
  TextView textView;
  RoomAdapter roomAdapter;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    System.out.println("MainView");
    view = inflater.inflate(R.layout.main_layout, container, false);
    findViews(view);
    setListenersToButtons();
    roomsRV.hasFixedSize();
    roomsRV.setLayoutManager(new LinearLayoutManager(getContext()));

//    measurementAdapter = new MeasurementAdapter(this);
//    measurementViewModel = new ViewModelProvider(requireActivity()).get(MeasurementViewModel.class);
//    measurementViewModel.getLatestMeasurementsFromRepo(FirebaseAuth.getInstance().getCurrentUser().getUid());
//    measurementViewModel.getMeasurements().observe(getViewLifecycleOwner(), listObjects -> setRooms(listObjects));
//    roomsRV.setAdapter(measurementAdapter);


    viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
    roomAdapter = new RoomAdapter(this);
    viewModel.getRoomsFromRepo(FirebaseAuth.getInstance().getCurrentUser().getUid());
    viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects -> setRooms(listObjects));
    roomsRV.setAdapter(roomAdapter);

    return view;
  }

  private void findViews(View view) {
    fab = view.findViewById(R.id.fab);
    roomsRV = view.findViewById(R.id.room_rv);
    textView = view.findViewById(R.id.textView2);
  }

  private void setListenersToButtons() {
    fab.setOnClickListener(view -> {
          NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
          navController.navigate(R.id.CreateRoom);
        }
    );
  }

  @Override
  public void onListItemClick(Room clickedItemIndex) {
    Toast.makeText(getContext(), "Room: " + clickedItemIndex.getRoomId(), Toast.LENGTH_SHORT).show();
  }

  private void setMeasurements(List<MeasurementObject> listObjects) {
    textView.setText("Active Rooms: " + listObjects.size());
    measurementAdapter.update(listObjects);
  }

  private void setRooms(List<Room> listObjects) {
    textView.setText("Active Rooms: " + listObjects.size());
    roomAdapter.update(listObjects);
  }
}

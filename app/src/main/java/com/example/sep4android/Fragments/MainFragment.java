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

import com.example.sep4android.Adapters.RoomAdapter;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

//Fragment for viewing Current Measurement Cards
public class MainFragment extends Fragment implements RoomAdapter.OnListItemClickListener {
  RoomViewModel viewModel;
  View view;
  FloatingActionButton fabCreateRoom;
  RecyclerView roomsRV;
  TextView activeRoomCount;
  RoomAdapter roomAdapter;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    System.out.println("MainView");
    view = inflater.inflate(R.layout.main_layout, container, false);
    viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
    // TODO: 24.05.2022 change this hard code back 
    viewModel.getRoomsFromRepo("682xEWmvched6FKYq9Fi2CPs7D73").observe(getViewLifecycleOwner(), this::setRooms);
    findViews(view);
    setListenersToButtons();
    roomsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    roomAdapter = new RoomAdapter(this);
    roomsRV.setAdapter(roomAdapter);
    return view;
  }

  private void findViews(View view) {
    fabCreateRoom = view.findViewById(R.id.fabCreateRoom);
    roomsRV = view.findViewById(R.id.room_rv);
    activeRoomCount = view.findViewById(R.id.activeRoomCount);
  }

  private void setListenersToButtons() {
    fabCreateRoom.setOnClickListener(view -> {
          NavController navController = Navigation.findNavController(getActivity(), R.id.fragmentContainerView);
          navController.navigate(R.id.CreateRoom);
        }
    );
  }

  @Override
  public void onListItemClick(RoomObject clickedItemIndex) {
    Toast.makeText(getContext(), "Room: " + clickedItemIndex.getRoomId(), Toast.LENGTH_SHORT).show();
  }


  //TODO CHANGE string to resource string everywhere
  private void setRooms(List<RoomObject> listObjects) {
    if (listObjects != null) {
      activeRoomCount.setText(getContext().getString(R.string.bind_holder_room_count_online, listObjects.size()));
      roomAdapter.updateListAndNotify(listObjects);
    } else {
      //viewModel.getRoomsLocal().observe(getViewLifecycleOwner(), this::setRoomsLocally);
    }
  }

  private void setRoomsLocally(List<RoomObject> roomObjects) {
    if (roomObjects != null) {
      System.out.println("LOL");
      activeRoomCount.setText(getContext().getString(R.string.bind_holder_room_count_offline, roomObjects.size()));
      roomAdapter.updateListAndNotify(roomObjects);
    }
  }
}

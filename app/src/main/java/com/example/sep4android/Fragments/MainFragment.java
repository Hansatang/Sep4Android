package com.example.sep4android.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sep4android.Adapters.RoomAdapter;
import com.example.sep4android.Objects.HumidityThresholdObject;
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
    viewModel.getRooms().observe(getViewLifecycleOwner(), this::setRooms);
    viewModel.getRoomsFromRepo("682xEWmvched6FKYq9Fi2CPs7D73");
    findViews(view);
    setListenersToButtons();
    roomsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    roomAdapter = new RoomAdapter(this);
    roomsRV.setAdapter(roomAdapter);
    setUpItemTouchHelper();
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
    }
  }

  private void setUpItemTouchHelper() {
    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int position = viewHolder.getAbsoluteAdapterPosition();
        System.out.println("Pos" +position);
        createPopUp(position);

      }
    };

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
    itemTouchHelper.attachToRecyclerView(roomsRV);
  }

  private void createPopUp(int position) {
    RoomObject roomObject = roomAdapter.getRoomObjectList().get(position);
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getLayoutInflater();
    View dialogView = inflater.inflate(R.layout.room_dialog, null);
    TextView titleTextView = dialogView.findViewById(R.id.titleTextView);
    EditText newName = dialogView.findViewById(R.id.nameText);
    Button changeNameButton = dialogView.findViewById(R.id.changeNameButton);
    Button resetButton = dialogView.findViewById(R.id.resetButton);
    Button deleteButton = dialogView.findViewById(R.id.deleteButton);
    Button cancelButton = dialogView.findViewById(R.id.cancelButton);
    titleTextView.setText("What you want to do with: " + roomObject.getName());
    builder.setView(dialogView);


    AlertDialog alertDialog = builder.create();
    changeNameButton.setOnClickListener(view -> {
      System.out.println("Change "+newName.getText());
      System.out.println(roomObject.getName());
      viewModel.changeName(roomObject.getRoomId(), newName.getText().toString());
      undoSwipe(position);
      alertDialog.dismiss();
    });

    resetButton.setOnClickListener(view -> {
      System.out.println("reset");
      viewModel.resetMeasurements(roomObject.getRoomId());
      undoSwipe(position);
      alertDialog.dismiss();
    });

    deleteButton.setOnClickListener(view -> {
      System.out.println("delete");
      viewModel.deleteRoom(roomObject.getRoomId());
      undoSwipe(position);
      alertDialog.dismiss();
    });

    cancelButton.setOnClickListener(view -> {
      System.out.println("Cancel");
      undoSwipe(position);
      alertDialog.dismiss();
    });

    DialogInterface.OnCancelListener cancelListener = (dialog) -> {
      undoSwipe(position);
      Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
    };
    alertDialog.setOnCancelListener(cancelListener);
    alertDialog.setCanceledOnTouchOutside(true);

    alertDialog.show();
  }

  @SuppressLint("NotifyDataSetChanged")
  private void undoSwipe(int position) {
    System.out.println("PosUndo "+position);
    roomAdapter.notifyItemChanged(position);
    roomsRV.scrollToPosition(position);
  }

}

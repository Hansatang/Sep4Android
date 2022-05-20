package com.example.sep4android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import com.example.sep4android.Adapters.InsideAdapter;
import com.example.sep4android.Adapters.MeasurementAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.MeasurementViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class MeasurementsFragment extends Fragment implements AdapterView.OnItemSelectedListener, MeasurementAdapter.OnListItemClickListener {
  View view;
  RoomViewModel viewModel;
  RecyclerView measurementsRV;
  MeasurementViewModel measurementViewModel;
  MeasurementAdapter measurementAdapter;
  Spinner spinner;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_measurements_list, container, false);
    viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
    measurementViewModel = new ViewModelProvider(requireActivity()).get(MeasurementViewModel.class);

    findViews();
    measurementsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    measurementAdapter = new MeasurementAdapter(this);
    viewModel.getRooms().observe(getViewLifecycleOwner(), this::initList);

    measurementsRV.setAdapter(measurementAdapter);

    //setUpItemTouchHelper();
    return view;
  }

  private void findViews() {
    measurementsRV = view.findViewById(R.id.measurement_rv);
  }

  private void initList(List<Room> listObjects) {
    System.out.println("Amounts " + listObjects.size());
    spinner = view.findViewById(R.id.sp);

    SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
    spinner.setAdapter(spinnerAdapter);
    spinner.setOnItemSelectedListener(this);

    ArrayList<LocalDateTime> weekNames = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    weekNames.add(now);
    for (int i = 1; i < 7; i++) {
      weekNames.add(now.plusDays(i));
    }
    setRooms(weekNames);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    Room room = (Room) adapterView.getItemAtPosition(i);
    ArrayList<LocalDateTime> weekNames = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    weekNames.add(now);
    for (int j = 1; j < 7; j++) {
      weekNames.add(now.plusDays(j));
    }
    setRooms(weekNames);
    measurementViewModel.getMeasurementsRoom(room.getRoomId());
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
    //Do nothing
  }

  private void setRooms(ArrayList<LocalDateTime> listObjects) {
    measurementAdapter.update(listObjects);
  }


  @Override
  public void onListItemClick(LocalDateTime clickedItem, InsideAdapter insideAdapter) {
    Room room = (Room) spinner.getSelectedItem();
    System.out.println("Room "+room.getName());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd E");
    System.out.println("Time "+dtf.format(clickedItem));
    measurementViewModel.getMeasurements().observe(getViewLifecycleOwner(), list -> {
      insideAdapter.update(list);
    });
  }

//  private void setUpItemTouchHelper() {
//    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//
//      @Override
//      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
//        return false;
//      }
//
//      @Override
//      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
//        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
//          int position = viewHolder.getAbsoluteAdapterPosition();
//
//          switch (which) {
//            case DialogInterface.BUTTON_POSITIVE:
//              Toast.makeText(getActivity(), "Card deleted", Toast.LENGTH_SHORT).show();
//              break;
//
//            case DialogInterface.BUTTON_NEGATIVE:
//              undoSwipe(position);
//              Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
//              break;
//          }
//        };
//
//        DialogInterface.OnCancelListener cancelListener = (dialog) -> {
//          int position = viewHolder.getAbsoluteAdapterPosition();
//          undoSwipe(position);
//          Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
//        };
//
//        AlertDialog dialog = new AlertDialog.Builder(getActivity())
//            .setMessage("Are you sure you want to delete?")
//            .setPositiveButton("Yes", dialogClickListener)
//            .setNegativeButton("No", dialogClickListener).setOnCancelListener(cancelListener).create();
//        dialog.setCanceledOnTouchOutside(true);
//        dialog.show();
//      }
//    };
//
//    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
//    itemTouchHelper.attachToRecyclerView(measurementsRV);
//  }
//
//  @SuppressLint("NotifyDataSetChanged")
//  private void undoSwipe(int position) {
//    measurementAdapter.notifyDataSetChanged();
//    measurementsRV.scrollToPosition(position);
//  }
}
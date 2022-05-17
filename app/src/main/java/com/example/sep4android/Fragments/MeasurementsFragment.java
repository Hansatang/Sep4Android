package com.example.sep4android.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sep4android.Adapters.MeasurementAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.MeasurementViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class MeasurementsFragment extends Fragment implements AdapterView.OnItemSelectedListener, MeasurementAdapter.OnListItemClickListener {
  View view;
  RoomViewModel viewModel;
  RecyclerView measurementsRV;
  MeasurementViewModel measurementViewModel;
  MeasurementAdapter measurementAdapter;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_measurements_list, container, false);
    findViews();
    measurementsRV.hasFixedSize();
    measurementsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    measurementAdapter = new MeasurementAdapter(this);
    viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
    viewModel.getRooms().observe(getViewLifecycleOwner(), this::initList);
    measurementViewModel = new ViewModelProvider(requireActivity()).get(MeasurementViewModel.class);

    measurementsRV.setAdapter(measurementAdapter);
    setUpItemTouchHelper();
    return view;
  }

  private void findViews() {
    measurementsRV = view.findViewById(R.id.measurement_rv);
  }


  private void initList(List<Room> listObjects) {
    System.out.println("Amounts " + listObjects.size());
    Spinner spinner = view.findViewById(R.id.sp);
    SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    measurementViewModel.getMeasurementsRoom(listObjects.get(0).getRoomId());
    measurementViewModel.getMeasurements().observe(getViewLifecycleOwner(), this::setRooms);
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    Room room = (Room) adapterView.getItemAtPosition(i);
    measurementViewModel.getMeasurementsRoom(room.getRoomId());
  }

  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {

  }

  private void setRooms(List<MeasurementsObject> listObjects) {
    measurementAdapter.update(listObjects);
  }

  @Override
  public void onListItemClick(MeasurementsObject clickedItemIndex) {
    System.out.println("HEYO");
  }

  private void setUpItemTouchHelper() {
    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
          int position = viewHolder.getAbsoluteAdapterPosition();
          MeasurementsObject card = measurementAdapter.getMeasurements().get(position);
          switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
              Toast.makeText(getActivity(), "Card deleted", Toast.LENGTH_SHORT).show();
              break;

            case DialogInterface.BUTTON_NEGATIVE:
              undoSwipe(position);
              Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
              break;
          }
        };

        DialogInterface.OnCancelListener cancelListener = (dialog) -> {
          int position = viewHolder.getAbsoluteAdapterPosition();
          MeasurementsObject card = measurementAdapter.getMeasurements().get(position);
              undoSwipe(position);
              Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
        };

        AlertDialog dialog = new AlertDialog.Builder(getActivity())
            .setMessage("Are you sure you want to delete?")
            .setPositiveButton("Yes", dialogClickListener)
            .setNegativeButton("No", dialogClickListener).setOnCancelListener(cancelListener).create();
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
      }
    };

    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
    itemTouchHelper.attachToRecyclerView(measurementsRV);
  }

  @SuppressLint("NotifyDataSetChanged")
  private void undoSwipe(int position) {
    measurementAdapter.notifyDataSetChanged();
    measurementsRV.scrollToPosition(position);
  }
}
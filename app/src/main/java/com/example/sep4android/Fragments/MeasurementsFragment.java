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

import com.example.sep4android.Adapters.MeasurementAdapter;
import com.example.sep4android.Adapters.RoomAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.MeasurementViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class MeasurementsFragment extends Fragment implements AdapterView.OnItemSelectedListener,MeasurementAdapter.OnListItemClickListener {
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
    viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects -> initList(listObjects));
    measurementViewModel = new ViewModelProvider(requireActivity()).get(MeasurementViewModel.class);

    measurementsRV.setAdapter(measurementAdapter);
    return view;
  }

  private void findViews() {
    measurementsRV = view.findViewById(R.id.measurement_rv);
  }


  private void initList(List<Room> listObjects) {
    System.out.println("Amounts " + listObjects.size());
    Spinner spinner = view.findViewById(R.id.sp);
    SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
   // adapter.setDropDownViewResource(R.layout.spin_item_dropdown);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    measurementViewModel.getMeasurementsRoom(listObjects.get(0).getRoomId());
    measurementViewModel.getMeasurements().observe(getViewLifecycleOwner(), measurementObjects -> setRooms(measurementObjects));
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
}
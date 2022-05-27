package com.example.sep4android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sep4android.Adapters.ChildMeasurementAdapter;
import com.example.sep4android.Adapters.ParentMeasurementAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ArchiveViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment for viewing archived measurements
 */
public class ArchiveFragment extends Fragment implements ParentMeasurementAdapter.OnListItemClickListener {
  private final String TAG = "ArchiveFragment";
  private View view;
  private RecyclerView measurementsRV;
  private ArchiveViewModel archiveVM;
  private ParentMeasurementAdapter parentMeasurementAdapter;
  private TextView statusTextView;
  private Spinner spinner;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.i(TAG, "Create Archive View");
    view = inflater.inflate(R.layout.fragment_measurements_list, container, false);
    createViewModels();
    findViews();
    archiveVM.getStatus().observe(getViewLifecycleOwner(), this::setStatus);
    measurementsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    parentMeasurementAdapter = new ParentMeasurementAdapter(this);
    archiveVM.getMeasurementsAllRoom(FirebaseAuth.getInstance().getCurrentUser().getUid());
    archiveVM.getRoomsLocal().observe(getViewLifecycleOwner(), this::initList);
    measurementsRV.setAdapter(parentMeasurementAdapter);
    return view;
  }

  /**
   * set statusTextView text based on result
   * @param result of connection check
   */
  private void setStatus(String result) {
    statusTextView.setText(result);
  }

  /**
   * initialize spinner with listObjects allowing for choosing desired room
   * set OnItemSelectedListener on spinner to reset parent data
   * @param listObjects list of roomObject from local database
   */
  private void initList(List<RoomObject> listObjects) {
    if (listObjects != null) {
      Log.i(TAG, "Initialize Parent list");
      SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
      spinner.setAdapter(spinnerAdapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          setDateTimesForParentMeasurementAdapter();
        }
        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
          //Do nothing
        }
      });
      setDateTimesForParentMeasurementAdapter();
    }
  }

  /**
   * Create dates for 7 previous days
   */
  private void setDateTimesForParentMeasurementAdapter() {
    ArrayList<LocalDateTime> weekNames = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    for (int i = 7; i > 1; i--) {
      weekNames.add(now.plusDays(-i));
    }
    weekNames.add(now);
    setRooms(weekNames);
  }


  /**
   * populate parentAdapter with dates object
   * @param listObjects list of 7 previous days dates
   */
  private void setRooms(ArrayList<LocalDateTime> listObjects) {
    parentMeasurementAdapter.updateListAndNotify(listObjects);
  }


  /**
   * populated childMeasurementAdapter with measurementObjects from local database based on selected room and date
   * @param clickedItem interacted item from parent Recycler View
   * @param childMeasurementAdapter child adapter from interacted ViewHolder
   */
  @Override
  public void onListItemClick(LocalDateTime clickedItem, ChildMeasurementAdapter childMeasurementAdapter) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd E");
    Log.i(TAG, "Click on " + dtf.format(clickedItem) + " in Archive");
    RoomObject roomObject = (RoomObject) spinner.getSelectedItem();
    archiveVM.getMeasurementsLocal(clickedItem, roomObject.getRoomId()).observe(getViewLifecycleOwner(), childMeasurementAdapter::updateListAndNotify);
  }

  /**
   * create all needed ViewModels in this fragment
   */
  private void createViewModels() {
    archiveVM = new ViewModelProvider(requireActivity()).get(ArchiveViewModel.class);
  }

  /**
   * assign all needed Views in this fragment
   */
  private void findViews() {
    measurementsRV = view.findViewById(R.id.measurement_rv);
    statusTextView = view.findViewById(R.id.statusTextView);
    spinner = view.findViewById(R.id.sp);
  }
}
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
import android.widget.TextView;

import com.example.sep4android.Adapters.ChildMeasurementAdapter;
import com.example.sep4android.Adapters.ParentMeasurementAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ArchiveViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.firebase.auth.FirebaseAuth;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

//Fragment for viewing archived measurements
public class ArchiveFragment extends Fragment implements ParentMeasurementAdapter.OnListItemClickListener {
  View view;
  RoomViewModel viewModel;
  RecyclerView measurementsRV;
  ArchiveViewModel archiveViewModel;
  ParentMeasurementAdapter parentMeasurementAdapter;
  TextView statusTextView;
  Spinner spinner;

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    view = inflater.inflate(R.layout.fragment_measurements_list, container, false);
    createViewModels();
    findViews();
    archiveViewModel.getStatus().observe(getViewLifecycleOwner(), this::setStatus);
    measurementsRV.setLayoutManager(new LinearLayoutManager(getContext()));
    parentMeasurementAdapter = new ParentMeasurementAdapter(this);
    archiveViewModel.getMeasurementsAllRoom(FirebaseAuth.getInstance().getCurrentUser().getUid());
    viewModel.getRooms().observe(getViewLifecycleOwner(), this::initList);
    measurementsRV.setAdapter(parentMeasurementAdapter);
    return view;
  }

  private void createViewModels() {
    viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
    archiveViewModel = new ViewModelProvider(requireActivity()).get(ArchiveViewModel.class);
  }

  private void setStatus(String s) {
    statusTextView.setText(s);
  }

  private void findViews() {
    measurementsRV = view.findViewById(R.id.measurement_rv);
    statusTextView = view.findViewById(R.id.statusTextView);
  }

  private void initList(List<RoomObject> listObjects) {
    if (listObjects != null) {
      System.out.println("Amounts " + listObjects.size());
      spinner = view.findViewById(R.id.sp);

      SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
      spinner.setAdapter(spinnerAdapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          System.out.println("Selected");
          setDateTimesForParentMeasurementAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
          //Do nothing
        }
      });

      setDateTimesForParentMeasurementAdapter();
    } else {
      viewModel.getRoomsLocal().observe(getViewLifecycleOwner(), this::initListLocal);
    }
  }

  private void initListLocal(List<RoomObject> listObjects) {
    if (listObjects != null) {
      System.out.println("Amounts " + listObjects.size());
      spinner = view.findViewById(R.id.sp);

      SpinnerAdapter spinnerAdapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
      spinner.setAdapter(spinnerAdapter);
      spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
          System.out.println("Selected");
          setDateTimesForParentMeasurementAdapter();
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
          //Do nothing
        }
      });

      setDateTimesForParentMeasurementAdapter();
    } else {
      viewModel.getRooms().observe(getViewLifecycleOwner(), this::initList);
    }
  }

  private void setDateTimesForParentMeasurementAdapter() {
    ArrayList<LocalDateTime> weekNames = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();

    for (int i = 7; i > 1; i--) {
      weekNames.add(now.plusDays(-i));
    }
    weekNames.add(now);
    setRooms(weekNames);
  }


  private void setRooms(ArrayList<LocalDateTime> listObjects) {
    parentMeasurementAdapter.updateListAndNotify(listObjects);
  }


  @Override
  public void onListItemClick(LocalDateTime clickedItem, ChildMeasurementAdapter childMeasurementAdapter) {
    RoomObject roomObject = (RoomObject) spinner.getSelectedItem();
    System.out.println("Room " + roomObject.getName());
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd E");
    System.out.println("Time " + dtf.format(clickedItem));
    archiveViewModel.getMeasurementsByDate(clickedItem, roomObject.getRoomId()).observe(getViewLifecycleOwner(), objects -> updateChildrenWithMeasurements(objects, childMeasurementAdapter, clickedItem));
  }


  private void updateChildrenWithMeasurements(List<MeasurementsObject> objects, ChildMeasurementAdapter childMeasurementAdapter, LocalDateTime clickedItem) {
    if (objects != null) {
      childMeasurementAdapter.updateListAndNotify(objects);
    } else {
      System.out.println("Get Local Meas");
      viewModel.getMeasurementsLocal(clickedItem, ((RoomObject) spinner.getSelectedItem()).getRoomId()).observe(getViewLifecycleOwner(), resultObjects -> setRoomsLocally(resultObjects, childMeasurementAdapter));
    }

  }

  private void setRoomsLocally(List<MeasurementsObject> roomObjects, ChildMeasurementAdapter childMeasurementAdapter) {
    childMeasurementAdapter.updateListAndNotify(roomObjects);
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
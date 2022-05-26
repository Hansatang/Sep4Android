package com.example.sep4android.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.sep4android.Adapters.HumidityThresholdAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.HumidityThresholdViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
// TODO: 26/05/2022  check comments two update list method
/**
 * Fragment for manipulating humility Thresholds
 */
public class HumidityThresholdFragment extends Fragment implements AdapterView.OnItemSelectedListener {
  private final String TAG = "HumidityThresholdFragment";
  private View view;
  private RoomViewModel roomViewModel;
  private HumidityThresholdViewModel humidityThresholdViewModel;
  private RecyclerView humidityThresholdList;
  private HumidityThresholdAdapter humidityThresholdAdapter;
  private Spinner spinner;
  private FloatingActionButton fab;
  private Button startTime, endTime;
  private NumberPicker startValue, endValue;
  private int hour, minute;

  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.i(TAG, "Create HumidityThreshold View");
    super.onCreate(savedInstanceState);
    view = inflater.inflate(R.layout.fragment_humidity_threshold_list, container, false);
    createViewModels();
    findViews();
    roomViewModel.getRooms().observe(getViewLifecycleOwner(), this::initList);
    humidityThresholdList.hasFixedSize();
    humidityThresholdList.setLayoutManager(new LinearLayoutManager(this.getContext()));
    humidityThresholdAdapter = new HumidityThresholdAdapter();
    humidityThresholdList.setAdapter(humidityThresholdAdapter);
    setUpItemTouchHelper();
    setListenersToButtons();
    humidityThresholdViewModel.getStatus().observe(getViewLifecycleOwner(), this::prepareResult);

    return view;
  }

  private void setListenersToButtons() {
    fab.setOnClickListener(view -> onButtonShowPopupWindowClick());
  }

  /**
   * assign all needed Views in this fragment
   */
  private void findViews() {
    humidityThresholdList = view.findViewById(R.id.humidity_threshold_rv);
    spinner = view.findViewById(R.id.sp_humidity);
    fab = view.findViewById(R.id.fab_add_new_threshold_humidity);
  }

  /**
   * create all needed ViewModels in this fragment
   */
  private void createViewModels() {
    humidityThresholdViewModel = new ViewModelProvider(requireActivity()).get(HumidityThresholdViewModel.class);
    roomViewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
  }

  /**
   * initialize spinner with listObjects allowing for choosing desired room
   * @param listObjects list of roomObject from local database
   */
  private void initList(List<RoomObject> listObjects) {
    System.out.println("Aleo "+listObjects.size());
    SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
    adapter.setDropDownViewResource(R.layout.spin_item_dropdown);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    humidityThresholdViewModel.getThresholdFromRepo(listObjects.get(0).getRoomId());
    humidityThresholdViewModel.getThresholds().observe(getViewLifecycleOwner(), this::updateListWithThresholds);
  }


  private void prepareResult(String result) {
    if (result != null) {
      if (result.equals("Complete")) {
        Toast.makeText(getContext(), "Complete", Toast.LENGTH_SHORT).show();
        updateList();
      } else if (result.equals("Wrong Threshold")) {
        Toast.makeText(getContext(), "Wrong Threshold", Toast.LENGTH_SHORT).show();
      }
      humidityThresholdViewModel.setResult();
    }
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    System.out.println(adapterView.getItemAtPosition(i).toString());
    // TODO: 20.05.2022 uncomment and change null to object id
    //humidityThresholdViewModel.getThresholdFromRepo(null);
  }


  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
    //Do Nothing
  }

  /**
   * populate humidityThresholdAdapter with humidityThresholdObjects
   * @param humidityThresholdObjects from repository to populate adapter with
   */
  private void updateListWithThresholds(List<HumidityThresholdObject> humidityThresholdObjects) {
    System.out.println("Aleo 2 "+ humidityThresholdObjects.size());
    humidityThresholdAdapter.updateHumidityThresholdsAndNotify(humidityThresholdObjects);
  }

  public void onButtonShowPopupWindowClick() {

    LayoutInflater inflater = (LayoutInflater)
        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View popupView = inflater.inflate(R.layout.fragment_add_new_threshold, null);

    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    boolean focusable = true;
    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

    popupView.setOnTouchListener(new View.OnTouchListener() {
      @Override
      public boolean onTouch(View v, MotionEvent event) {
        popupWindow.dismiss();
        return true;
      }
    });

    findPopUpViews(popupView);

    popupView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        System.out.println("**********************");
        System.out.println("adding");
        humidityThresholdViewModel.addThresholdToDatabase(((RoomObject) spinner.getSelectedItem()).getRoomId(),
            startTime.getText().toString(), endTime.getText().toString(), endValue.getValue(), startValue.getValue());
        System.out.println("**********************");

        popupWindow.dismiss();
      }
    });

    startTime.setOnClickListener(view -> popTimePicker(view, startTime));

    endTime.setOnClickListener(view -> popTimePicker(view, endTime));
  }

  private void findPopUpViews(View popupView) {
    startTime = popupView.findViewById(R.id.select_start_time);
    endTime = popupView.findViewById(R.id.select_end_time);
    startValue = popupView.findViewById(R.id.select_start_value);
    startValue.setMinValue(0);
    startValue.setMaxValue(35);
    endValue = popupView.findViewById(R.id.select_end_value);
    endValue.setMinValue(0);
    endValue.setMaxValue(35);
  }

  public void popTimePicker(View view, Button button) {
    TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
      @Override
      public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
        hour = selectedHour;
        minute = selectedMinute;
        button.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
      }
    };

    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hour, minute, true);

    timePickerDialog.setTitle("Select Time");
    timePickerDialog.show();
  }


  private void setUpItemTouchHelper() {
    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {

      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
        DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
          int position = viewHolder.getAbsoluteAdapterPosition();
          HumidityThresholdObject humidityThresholdObject = humidityThresholdAdapter.getThresholds().get(position);

          switch (which) {
            case DialogInterface.BUTTON_POSITIVE:
              Toast.makeText(getActivity(), "Threshold deleted", Toast.LENGTH_SHORT).show();
              humidityThresholdViewModel.deleteThreshold(humidityThresholdObject.getThresholdHumidityId());
              updateList();
              break;

            case DialogInterface.BUTTON_NEGATIVE:
              undoSwipe(position);
              Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
              break;
          }
        };

        DialogInterface.OnCancelListener cancelListener = (dialog) -> {
          int position = viewHolder.getAbsoluteAdapterPosition();
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
    itemTouchHelper.attachToRecyclerView(humidityThresholdList);
  }

  @SuppressLint("NotifyDataSetChanged")
  private void undoSwipe(int position) {
    humidityThresholdAdapter.notifyDataSetChanged();
    humidityThresholdList.scrollToPosition(position);
  }

  private void updateList() {
    System.out.println("**********************");
    System.out.println("update list");
    humidityThresholdViewModel.getThresholdFromRepo(((RoomObject) spinner.getSelectedItem()).getRoomId());
    System.out.println("**********************");
  }
}
package com.example.sep4android.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
  private RoomViewModel roomVM;
  private HumidityThresholdViewModel humidityThresholdVM;
  private RecyclerView humidityThresholdRV;
  private HumidityThresholdAdapter humidityThresholdAdapter;
  private Spinner spinner;
  private TextView title;
  private FloatingActionButton fab;
  private Button startTime, endTime;
  private NumberPicker startValue, endValue;
  private int hour, minute;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createViewModels();
  }


  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    Log.i(TAG, "Create HumidityThreshold View");
    super.onCreate(savedInstanceState);
    view = inflater.inflate(R.layout.fragment_humidity_threshold_list, container, false);
    findViews();
    roomVM.getRoomsLiveData().observe(getViewLifecycleOwner(), this::initList);
    humidityThresholdRV.setLayoutManager(new LinearLayoutManager(this.getContext()));
    humidityThresholdAdapter = new HumidityThresholdAdapter();
    humidityThresholdRV.setAdapter(humidityThresholdAdapter);
    setUpItemTouchHelper();
    setListenersToButtons();
    humidityThresholdVM.getStatusLiveData().observe(getViewLifecycleOwner(), this::prepareResult);
    return view;
  }

  /**
   * Adding functionality to buttons in this view
   */
  private void setListenersToButtons() {
    fab.setOnClickListener(view -> onButtonShowPopupWindowClick(null, -1));
  }

  /**
   * assign all needed Views in this fragment
   */
  private void findViews() {
    humidityThresholdRV = view.findViewById(R.id.humidity_threshold_rv);
    spinner = view.findViewById(R.id.sp_humidity);
    fab = view.findViewById(R.id.fab_add_new_threshold_humidity);
  }

  /**
   * create all needed ViewModels in this fragment
   */
  private void createViewModels() {
    humidityThresholdVM = new ViewModelProvider(requireActivity()).get(HumidityThresholdViewModel.class);
    roomVM = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
  }

  /**
   * initialize spinner with listObjects allowing for choosing desired room
   *
   * @param listObjects list of roomObject from local database
   */
  private void initList(List<RoomObject> listObjects) {
    SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spinner_layout, new ArrayList<>(listObjects));
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);
    humidityThresholdVM.getHumidityThresholds(listObjects.get(0).getRoomId());
    humidityThresholdVM.getThresholdsLiveData().observe(getViewLifecycleOwner(), this::updateListWithThresholds);
  }

  /**
   * Updates list if manipulation was successful
   *
   * @param result of humidity threshold manipulation
   */
  private void prepareResult(String result) {
    Log.i(TAG, "Creating result messages to user");
    if (result != null) {
      if (result.equals("Complete")) {
        Toast.makeText(getContext(), "Complete", Toast.LENGTH_SHORT).show();
        updateList();
      } else if (result.equals("Wrong Threshold")) {
        Toast.makeText(getContext(), "Wrong Threshold", Toast.LENGTH_SHORT).show();
      }
      humidityThresholdVM.setResult();
    }
  }

  @Override
  public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
    RoomObject item = (RoomObject) adapterView.getItemAtPosition(i);
    humidityThresholdVM.getHumidityThresholds(item.getRoomId());
  }


  @Override
  public void onNothingSelected(AdapterView<?> adapterView) {
    //Do Nothing
  }

  /**
   * populate humidityThresholdAdapter with humidityThresholdObjects
   *
   * @param humidityThresholdObjects from repository to populate adapter with
   */
  private void updateListWithThresholds(List<HumidityThresholdObject> humidityThresholdObjects) {
    humidityThresholdAdapter.updateHumidityThresholdsAndNotify(humidityThresholdObjects);
  }

  /**
   * Opens a pop-up window for creating new humidity threshold object
   */
  public void onButtonShowPopupWindowClick(HumidityThresholdObject humidityThresholdObject, int position) {


    Log.i(TAG, "Opening up the pop-up window");

    LayoutInflater inflater = (LayoutInflater)
        getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    View popupView = inflater.inflate(R.layout.fragment_add_new_threshold, null);

    findPopUpViews(popupView);

    int width = LinearLayout.LayoutParams.WRAP_CONTENT;
    int height = LinearLayout.LayoutParams.WRAP_CONTENT;
    final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);

    if (humidityThresholdObject != null) {
      title.setText(getString(R.string.edit_threshold));
      startTime.setText(humidityThresholdObject.getStartTime());
      endTime.setText(humidityThresholdObject.getEndTime());
      endValue.setValue((int) humidityThresholdObject.getMaxValue());
      startValue.setValue((int) humidityThresholdObject.getMinValue());
      String[] timeS = humidityThresholdObject.getStartTime().split(":");
      String[] timeE = humidityThresholdObject.getEndTime().split(":");
      startTime.setOnClickListener(view -> popTimePicker("Select start time",Integer.parseInt(timeS[0]),Integer.parseInt(timeS[1]), startTime));
      endTime.setOnClickListener(view -> popTimePicker("Select end time",Integer.parseInt(timeE[0]),Integer.parseInt(timeE[1]), endTime));
      popupView.findViewById(R.id.add_button).setOnClickListener(view -> {
        humidityThresholdVM.updateHumidityThreshold(humidityThresholdObject.getThresholdHumidityId(),((RoomObject) spinner.getSelectedItem()).getRoomId(),
            startTime.getText().toString(), endTime.getText().toString(), endValue.getValue(), startValue.getValue());
        popupWindow.dismiss();
      });
    }
    else{
      title.setText(getString(R.string.add_new_threshold));
      startTime.setOnClickListener(view -> popTimePicker("Select start time",0, 0, startTime));
      endTime.setOnClickListener(view -> popTimePicker("Select end time",0, 0, endTime));
      popupView.findViewById(R.id.add_button).setOnClickListener(view -> {
        humidityThresholdVM.addHumidityThreshold(((RoomObject) spinner.getSelectedItem()).getRoomId(),
            startTime.getText().toString(), endTime.getText().toString(), endValue.getValue(), startValue.getValue());
        popupWindow.dismiss();
      });
    }
    popupWindow.setOnDismissListener(() -> undoSwipe(position));
    popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
  }


  /**
   * Assigns all needed Views in this fragment
   *
   * @param popupView pop-up window view
   */
  private void findPopUpViews(View popupView) {
    title =popupView.findViewById(R.id.pop_up_title);
    startTime = popupView.findViewById(R.id.select_start_time);
    endTime = popupView.findViewById(R.id.select_end_time);
    startValue = popupView.findViewById(R.id.select_start_value);
    startValue.setMinValue(0);
    startValue.setMaxValue(100);
    endValue = popupView.findViewById(R.id.select_end_value);
    endValue.setMinValue(0);
    endValue.setMaxValue(100);
  }

  public void popTimePicker(String title,int hourVal, int minuteVal, Button button) {
    TimePickerDialog.OnTimeSetListener onTimeSetListener = (timePicker, selectedHour, selectedMinute) -> {
      hour = selectedHour;
      minute = selectedMinute;
      button.setText(String.format(Locale.getDefault(), "%02d:%02d", hour, minute));
    };

    TimePickerDialog timePickerDialog = new TimePickerDialog(getContext(), onTimeSetListener, hourVal, minuteVal, true);
    timePickerDialog.setTitle(title);
    timePickerDialog.show();
  }

  /**
   * Adds functionality to recycle view: deleting thresholds from the list with a swipe
   */
  private void setUpItemTouchHelper() {
    ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {

      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
        int position = viewHolder.getAbsoluteAdapterPosition();
        HumidityThresholdObject humidityThresholdObject = humidityThresholdAdapter.getThresholds().get(position);

        if (swipeDir == ItemTouchHelper.LEFT) {
          DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
            switch (which) {
              case DialogInterface.BUTTON_POSITIVE:
                Toast.makeText(getActivity(), "Threshold deleted", Toast.LENGTH_SHORT).show();
                humidityThresholdVM.deleteHumidityThreshold(humidityThresholdObject.getThresholdHumidityId());
                Log.i(TAG, "Deleting threshold with a swipe");
                updateList();
                break;
              case DialogInterface.BUTTON_NEGATIVE:
                undoSwipe(position);
                Toast.makeText(getActivity(), "Canceled", Toast.LENGTH_SHORT).show();
                break;
            }
          };

          DialogInterface.OnCancelListener cancelListener = (dialog) -> {
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
        if (swipeDir == ItemTouchHelper.RIGHT) {

          onButtonShowPopupWindowClick(humidityThresholdObject,position);
        }
      }
    };
    ItemTouchHelper itemTouchHelper = new ItemTouchHelper(swipeCallback);
    itemTouchHelper.attachToRecyclerView(humidityThresholdRV);
  }

  /**
   * Resets swipe action
   *
   * @param position of item that is being reset
   */
  @SuppressLint("NotifyDataSetChanged")
  private void undoSwipe(int position) {
    humidityThresholdAdapter.notifyDataSetChanged();
    humidityThresholdRV.scrollToPosition(position);
  }

  private void updateList() {
    humidityThresholdVM.getHumidityThresholds(((RoomObject) spinner.getSelectedItem()).getRoomId());
  }
}
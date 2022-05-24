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

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
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
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.HumidityThresholdViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class HumidityThresholdFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private RoomViewModel viewModel;
    private HumidityThresholdViewModel humidityThresholdViewModel;
    private ArrayList<String> mCountryList;
    private RecyclerView humidityThresholdList;
    private HumidityThresholdAdapter humidityThresholdAdapter;
    private Context context;
    private Spinner spinner;
    private FloatingActionButton fab;
    private Button startTime, endTime;
    private NumberPicker startValue, endValue;

    int hour, minute;


    public HumidityThresholdFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        humidityThresholdViewModel = new ViewModelProvider(requireActivity()).get(HumidityThresholdViewModel.class);

        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_humidity_threshold_list, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
        viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects -> initList(listObjects));

        humidityThresholdList = view.findViewById(R.id.humidity_threshold_rv);
        humidityThresholdList.hasFixedSize();
        humidityThresholdList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        humidityThresholdAdapter = new HumidityThresholdAdapter();
        humidityThresholdList.setAdapter(humidityThresholdAdapter);

        setUpItemTouchHelper();

        return view;
    }

    private void initList(List<Room> listObjects) {
        mCountryList = new ArrayList<>();
        for (Room object : listObjects) {
            mCountryList.add(object.getRoomId());
        }
        spinner = view.findViewById(R.id.sp_humidity);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spin_item, mCountryList);
        SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
        adapter.setDropDownViewResource(R.layout.spin_item_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        humidityThresholdViewModel.getThresholdFromRepo(listObjects.get(0).getRoomId());
        humidityThresholdViewModel.getThresholds().observe(getViewLifecycleOwner(), this::updateList);

        fab = view.findViewById(R.id.fab_add_new_threshold_humidity);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick();
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(adapterView.getItemAtPosition(i).toString());
        // TODO: 20.05.2022 uncomment and change null to object id 
        //humidityThresholdViewModel.getThresholdFromRepo(null);
    }

    private void updateList(List<HumidityThresholdObject> humidityThresholdObjects) {
        humidityThresholdAdapter.update(humidityThresholdObjects);
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onButtonShowPopupWindowClick() {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

        findViews(popupView);

        popupView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 19.05.2022 Call method from view model with the 4 fields and room id from spinner
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });

        startTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view, startTime);
            }
        });

        endTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popTimePicker(view, endTime);
            }
        });
    }

    private void findViews(View popupView) {
        startTime = popupView.findViewById(R.id.select_start_time);
        endTime = popupView.findViewById(R.id.select_end_time);
        startValue = popupView.findViewById(R.id.select_start_value);
        startValue.setMinValue(0);
        startValue.setMaxValue(35);
        endValue = popupView.findViewById(R.id.select_end_value);
        endValue.setMinValue(0);
        endValue.setMaxValue(35);
    }

    public void popTimePicker(View view, Button button)
    {
        TimePickerDialog.OnTimeSetListener onTimeSetListener = new TimePickerDialog.OnTimeSetListener()
        {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute)
            {
                hour = selectedHour;
                minute = selectedMinute;
                button.setText(String.format(Locale.getDefault(), "%02d:%02d",hour, minute));
            }
        };

        TimePickerDialog timePickerDialog = new TimePickerDialog(context, onTimeSetListener, hour, minute, true);

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
                            Toast.makeText(getActivity(), "Card deleted", Toast.LENGTH_SHORT).show();
                            humidityThresholdViewModel.deleteThreshold(humidityThresholdObject.getThresholdHumidityId());
                            humidityThresholdViewModel.getThresholdFromRepo(((Room)spinner.getSelectedItem()).getRoomId());
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
}
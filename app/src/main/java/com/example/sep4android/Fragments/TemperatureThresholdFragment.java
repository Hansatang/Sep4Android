package com.example.sep4android.Fragments;

import android.content.Context;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Adapters.TemperatureThresholdAdapter;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.RoomViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class TemperatureThresholdFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private RoomViewModel viewModel;
    private ArrayList<String> mCountryList;
    private RecyclerView temperatureThresholdList;
    private TemperatureThresholdAdapter temperatureThresholdAdapter;
    private Context context;
    private Spinner spinner;
    private FloatingActionButton fab;
    private EditText startTime, endTime, startValue, endValue;


    public TemperatureThresholdFragment() {
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        context = container.getContext();
        view = inflater.inflate(R.layout.fragment_temperature_threshold_list, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
        viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects -> initList(listObjects));

        temperatureThresholdList = view.findViewById(R.id.temperature_threshold_rv);
        temperatureThresholdList.hasFixedSize();
        temperatureThresholdList.setLayoutManager(new LinearLayoutManager(this.getContext()));

        temperatureThresholdAdapter = new TemperatureThresholdAdapter();
        temperatureThresholdList.setAdapter(temperatureThresholdAdapter);

        fab = view.findViewById(R.id.fab_add_new_threshold_temperature);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onButtonShowPopupWindowClick();
            }
        });

        return view;
    }

    private void initList(List<Room> listObjects) {
        mCountryList = new ArrayList<>();
        for (Room object : listObjects) {
            mCountryList.add(object.getRoomId());
        }
        spinner = view.findViewById(R.id.sp_temperature);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spin_item, mCountryList);
        SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
        adapter.setDropDownViewResource(R.layout.spin_item_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
    }

    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        System.out.println(adapterView.getItemAtPosition(i).toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    public void onButtonShowPopupWindowClick() {

        LayoutInflater inflater = (LayoutInflater)
                context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.fragment_add_new_threshold_temperature, null);

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
        
        findViews();

        popupView.findViewById(R.id.add_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // TODO: 19.05.2022 Call method from view model with the 4 fields and room id from spinner 
                Toast.makeText(getContext(), "Hello", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void findViews() {
        startTime = view.findViewById(R.id.select_start_time);
        endTime = view.findViewById(R.id.select_end_time);
        startValue = view.findViewById(R.id.select_start_value);
        endValue = view.findViewById(R.id.select_end_value);
    }
}

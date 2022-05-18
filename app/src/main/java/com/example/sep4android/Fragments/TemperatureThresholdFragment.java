package com.example.sep4android.Fragments;

import android.content.Context;
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

import com.example.sep4android.Adapters.HumidityThresholdAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Adapters.TemperatureThresholdAdapter;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class TemperatureThresholdFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private RoomViewModel viewModel;
    private ArrayList<String> mCountryList;
    private RecyclerView temperatureThresholdList;
    private TemperatureThresholdAdapter temperatureThresholdAdapter;
    private Context context;


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

        return view;
    }

    private void initList(List<Room> listObjects) {
        mCountryList = new ArrayList<>();
        for (Room object : listObjects) {
            mCountryList.add(object.getRoomId());
        }
        Spinner spinner = view.findViewById(R.id.sp_temperature);
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
}
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
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sep4android.Adapters.HumidityThresholdAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.HumidityThresholdViewModel;
import com.example.sep4android.ViewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class HumidityThresholdFragment extends Fragment implements AdapterView.OnItemSelectedListener {

    private View view;
    private RoomViewModel viewModel;
    private HumidityThresholdViewModel humidityThresholdViewModel;
    private ArrayList<String> mCountryList;
    private RecyclerView humidityThresholdList;
    private HumidityThresholdAdapter humidityThresholdAdapter;
    private Context context;


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

        return view;
    }

    private void initList(List<Room> listObjects) {
        mCountryList = new ArrayList<>();
        for (Room object : listObjects) {
            mCountryList.add(object.getRoomId());
        }
        Spinner spinner = view.findViewById(R.id.sp_humidity);
       // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spin_item, mCountryList);
        SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
        adapter.setDropDownViewResource(R.layout.spin_item_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        humidityThresholdViewModel.getThresholdFromRepo(((Room)spinner.getSelectedItem()).getRoomId());
        humidityThresholdViewModel.getThresholds().observe(getViewLifecycleOwner(), this::updateList);
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
}
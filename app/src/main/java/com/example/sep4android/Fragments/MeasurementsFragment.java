package com.example.sep4android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.RoomViewModel;

import java.util.ArrayList;
import java.util.List;

public class MeasurementsFragment extends Fragment implements AdapterView.OnItemSelectedListener {
    View view;
    RoomViewModel viewModel;
    private ArrayList<Integer> mCountryList;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_measurements_list, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(RoomViewModel.class);
        viewModel.getRooms().observe(getViewLifecycleOwner(), listObjects -> initList(listObjects));
        return view;
    }


    private void initList(List<RoomObject> listObjects) {
        mCountryList = new ArrayList<>();
        for (RoomObject object : listObjects) {
            mCountryList.add(object.getRoomId());
        }
        Spinner spinner = view.findViewById(R.id.sp);
        ArrayAdapter<Integer> adapter = new ArrayAdapter<Integer>(this.getActivity(), R.layout.spin_item, mCountryList);
        // SpinnerRoomAdapter adapter = new SpinnerRoomAdapter(requireActivity(),android.R.layout.simple_spinner_item, mCountryList);
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
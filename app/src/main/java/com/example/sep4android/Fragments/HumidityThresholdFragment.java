package com.example.sep4android.Fragments;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.sep4android.Adapters.HumidityThresholdAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.RoomObject;
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

    private void initList(List<RoomObject> listObjects) {
        mCountryList = new ArrayList<>();
        for (RoomObject object : listObjects) {
            mCountryList.add(object.getRoomId());
        }
        Spinner spinner = view.findViewById(R.id.sp_humidity);
        // ArrayAdapter<String> adapter = new ArrayAdapter<String>(this.getActivity(), R.layout.spin_item, mCountryList);
        SpinnerAdapter adapter = new SpinnerAdapter(requireActivity(), R.layout.spin_item, new ArrayList<>(listObjects));
        adapter.setDropDownViewResource(R.layout.spin_item_dropdown);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        humidityThresholdViewModel.getThresholdFromRepo(listObjects.get(0).getRoomId());
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

    // TODO: 24.05.2022 test

    private void setUpItemTouchHelper() {
        ItemTouchHelper.SimpleCallback swipeCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int swipeDir) {
                DialogInterface.OnClickListener dialogClickListener = (dialog, which) -> {
                    int position = viewHolder.getAbsoluteAdapterPosition();

                    switch (which) {
                        case DialogInterface.BUTTON_POSITIVE:
                            Toast.makeText(getActivity(), "Card deleted", Toast.LENGTH_SHORT).show();
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
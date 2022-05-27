package com.example.sep4android.Fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.sep4android.Adapters.ParentMeasurementAdapter;
import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ArchiveViewModel;
import com.example.sep4android.ViewModels.StatisticsViewModel;
import com.google.firebase.auth.FirebaseAuth;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.charts.ValueLineChart;
import org.eazegraph.lib.models.BarModel;
import org.eazegraph.lib.models.ValueLinePoint;
import org.eazegraph.lib.models.ValueLineSeries;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;


public class StatisticsFragment extends Fragment {
    private final String TAG = "StatisticsFragment";
    private TextView tvR, tvPython, tvCPP, tvJava;
    private PieChart pieChart;
    private View view;
    private ArchiveViewModel archiveVM;
    private StatisticsViewModel statisticsVM;
    private Spinner spinner;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println("Test");
        view = inflater.inflate(R.layout.fragment_test, container, false);
        findViews();
        createViewModels();
        findViews();
        archiveVM.getRoomsLocal().observe(getViewLifecycleOwner(), this::initList);
        return view;
    }

    /**
     * create all needed ViewModels in this fragment
     */
    private void createViewModels() {
        archiveVM = new ViewModelProvider(requireActivity()).get(ArchiveViewModel.class);
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

                }
                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {
                    //Do nothing
                }
            });
            setData();
         //   statisticsVM.getStatisticsFromRepo(listObjects.get(0).getRoomId());
           // statisticsVM.getStatistics().observe(getViewLifecycleOwner(), this::updateGraphs);
        }
    }

//    private void updateGraphs(Statistics statistics) {
//        setData();
//    }

    private void findViews() {
        spinner = view.findViewById(R.id.sp);
        tvR = view.findViewById(R.id.deviceNameText);
        tvPython = view.findViewById(R.id.tv0510);
        tvCPP = view.findViewById(R.id.tv1015);
        tvJava = view.findViewById(R.id.tv1520);
        pieChart = view.findViewById(R.id.piechart);
    }

    private void setData() {
        BarChart mBarChart = (BarChart) view.findViewById(R.id.barchart);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd E");

        ArrayList<LocalDateTime> weekNames = new ArrayList<>();
        LocalDateTime now = LocalDateTime.now();
        for (int i = 7; i > 1; i--) {
            weekNames.add(now.plusDays(-i));
        }
        weekNames.add(now);

        mBarChart.clearChart();
        List<BarModel> list = new ArrayList<>();
        list.add(new BarModel(   dtf.format(weekNames.get(0)),1, 0xFF123456));
        list.add(new BarModel(dtf.format(weekNames.get(1)),2,  0xFF343456));
        list.add(new BarModel(dtf.format(weekNames.get(2)),3, 0xFF563456));
        list.add(new BarModel(dtf.format(weekNames.get(3)),4, 0xFF873F56));
        list.add(new BarModel(dtf.format(weekNames.get(4)),5, 0xFF873F56));
        list.add(new BarModel(dtf.format(weekNames.get(5)),6, 0xFF873F56));
        list.add(new BarModel(dtf.format(weekNames.get(6)),7, 0xFF873F56));
        mBarChart.addBarList(list);
        mBarChart.startAnimation();
    }

}
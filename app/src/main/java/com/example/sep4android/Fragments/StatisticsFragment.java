package com.example.sep4android.Fragments;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Spinner;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.sep4android.Adapters.SpinnerAdapter;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.R;
import com.example.sep4android.ViewModels.ArchiveViewModel;
import com.example.sep4android.ViewModels.StatisticsViewModel;

import org.eazegraph.lib.charts.BarChart;
import org.eazegraph.lib.models.BarModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * Fragment fro Statistic page
 */
public class StatisticsFragment extends Fragment {
  private final String TAG = "StatisticsFragment";
  private View view;
  private BarChart tempBarChart;
  private BarChart humBarChart;
  private BarChart co2BarChart;
  private ArchiveViewModel archiveVM;
  private StatisticsViewModel statisticsVM;
  private Spinner spinner;

  @Override
  public void onCreate(@Nullable Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    createViewModels();
  }

  @Override
  public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    System.out.println("Test");
    view = inflater.inflate(R.layout.fragment_test, container, false);
    findViews();
    findViews();
    archiveVM.getRoomsLocalLiveData().observe(getViewLifecycleOwner(), this::initList);
    archiveVM.getRoomsLocal();
    statisticsVM.getTempStats().observe(getViewLifecycleOwner(), list -> setChart(list, tempBarChart));
    statisticsVM.getHumStats().observe(getViewLifecycleOwner(), doubles -> setChart(doubles, humBarChart));
    statisticsVM.getCo2Stats().observe(getViewLifecycleOwner(), doubles -> setChart(doubles, co2BarChart));
    return view;
  }

  private void setChart(List<Double> doubles, BarChart BarChart) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("E MM/dd");
    ArrayList<LocalDateTime> weekNames = new ArrayList<>();
    LocalDateTime now = LocalDateTime.now();
    for (int i = 7; i > 1; i--) {
      weekNames.add(now.plusDays(-i));
    }
    weekNames.add(now);

    BarChart.clearChart();
    List<BarModel> list = new ArrayList<>();
    for (int i = 0; i < 7; i++) {
      if (doubles.get(i) != null) {
        list.add(new BarModel(dtf.format(weekNames.get(6 - i)), doubles.get(i).floatValue(), (getResources().getIntArray(R.array.chartColors))[i]));
      }
    }
    BarChart.addBarList(list);
    BarChart.startAnimation();

  }

  /**
   * create all needed ViewModels in this fragment
   */
  private void createViewModels() {
    archiveVM = new ViewModelProvider(requireActivity()).get(ArchiveViewModel.class);
    statisticsVM = new ViewModelProvider(requireActivity()).get(StatisticsViewModel.class);
  }

  /**
   * initialize spinner with listObjects allowing for choosing desired room
   * set OnItemSelectedListener on spinner to reset parent data
   *
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
          RoomObject selectedRoom = (RoomObject) adapterView.getSelectedItem();
          statisticsVM.getTempStatsFromRepo(selectedRoom.getRoomId());
          statisticsVM.getHumStatsFromRepo(selectedRoom.getRoomId());
          statisticsVM.getCo2StatsFromRepo(selectedRoom.getRoomId());
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {
          //Do nothing
        }
      });
    }
  }

  /**
   * Assigns all needed Views in this fragment
   */
  private void findViews() {
    spinner = view.findViewById(R.id.sp);
    tempBarChart = view.findViewById(R.id.tempBarchart);
    humBarChart = view.findViewById(R.id.humidityBarchart);
    co2BarChart = view.findViewById(R.id.co2Barchart);
  }

}

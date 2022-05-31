package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.Repositories.StatisticsRepository;

import java.util.List;

/**
 * View Model class for statistic
 */
public class StatisticsViewModel extends AndroidViewModel {
  private final StatisticsRepository statisticsRepository;
  private final MediatorLiveData<List<Double>> tempAverageWeek;
  private final MediatorLiveData<List<Double>> humAverageWeek;
  private final MediatorLiveData<List<Double>> co2AverageWeek;

  public StatisticsViewModel(Application appl) {
    super(appl);
    statisticsRepository = StatisticsRepository.getInstance();
    this.tempAverageWeek = new MediatorLiveData<>();
    this.humAverageWeek = new MediatorLiveData<>();
    this.co2AverageWeek = new MediatorLiveData<>();
  }

  public LiveData<List<Double>> getTempAverageLiveData() {
    return tempAverageWeek;
  }

  public LiveData<List<Double>> getHumAverageLiveData() {
    return humAverageWeek;
  }

  public LiveData<List<Double>> getCo2AverageLiveData() {
    return co2AverageWeek;
  }

  public void getTempStats(String roomId) {
    tempAverageWeek.addSource(statisticsRepository.getTempStats(roomId), tempAverageWeek::setValue);
  }

  public void getHumStats(String roomId) {
    humAverageWeek.addSource(statisticsRepository.getHumStats(roomId), humAverageWeek::setValue);
  }

  public void getCo2Stats(String roomId) {
    co2AverageWeek.addSource(statisticsRepository.getCo2Stats(roomId), co2AverageWeek::setValue);
  }

}

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
  private final MediatorLiveData<List<Double>> tempAverageWeekLiveData;
  private final MediatorLiveData<List<Double>> humAverageWeekLiveData;
  private final MediatorLiveData<List<Double>> co2AverageWeekLiveData;

  public StatisticsViewModel(Application app) {
    super(app);
    statisticsRepository = StatisticsRepository.getInstance();
    this.tempAverageWeekLiveData = new MediatorLiveData<>();
    this.humAverageWeekLiveData = new MediatorLiveData<>();
    this.co2AverageWeekLiveData = new MediatorLiveData<>();
  }

  public LiveData<List<Double>> getTempAverageLiveData() {
    return tempAverageWeekLiveData;
  }

  public LiveData<List<Double>> getHumAverageLiveData() {
    return humAverageWeekLiveData;
  }

  public LiveData<List<Double>> getCo2AverageLiveData() {
    return co2AverageWeekLiveData;
  }

  public void getTempStats(String roomId) {
    tempAverageWeekLiveData.addSource(statisticsRepository.getTempStats(roomId), tempAverageWeekLiveData::setValue);
  }

  public void getHumStats(String roomId) {
    humAverageWeekLiveData.addSource(statisticsRepository.getHumStats(roomId), humAverageWeekLiveData::setValue);
  }

  public void getCo2Stats(String roomId) {
    co2AverageWeekLiveData.addSource(statisticsRepository.getCo2Stats(roomId), co2AverageWeekLiveData::setValue);
  }

}

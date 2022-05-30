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

  public StatisticsViewModel(Application application) {
    super(application);
    statisticsRepository = StatisticsRepository.getInstance();
    this.tempAverageWeek = new MediatorLiveData<>();
    this.humAverageWeek = new MediatorLiveData<>();
    this.co2AverageWeek = new MediatorLiveData<>();
  }

  public LiveData<List<Double>> getTempStats() {
    return tempAverageWeek;
  }

  public LiveData<List<Double>> getHumStats() {
    return humAverageWeek;
  }

  public LiveData<List<Double>> getCo2Stats() {
    return co2AverageWeek;
  }

  public void getTempStatsFromRepo(String roomId) {
    tempAverageWeek.addSource(statisticsRepository.getTempStats(roomId), tempAverageWeek::setValue);
  }

  public void getHumStatsFromRepo(String roomId) {
    humAverageWeek.addSource(statisticsRepository.getHumStats(roomId), humAverageWeek::setValue);
  }

  public void getCo2StatsFromRepo(String roomId) {
    co2AverageWeek.addSource(statisticsRepository.getCo2Stats(roomId), co2AverageWeek::setValue);
  }

}

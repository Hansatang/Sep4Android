package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Repositories.RoomRepository;
import com.example.sep4android.Repositories.StatisticsRepository;

import java.util.List;

public class StatisticsViewModel extends AndroidViewModel {
  private final StatisticsRepository statisticsRepository;

  public StatisticsViewModel(Application application) {
    super(application);
    statisticsRepository = StatisticsRepository.getInstance(application);
  }


  public LiveData<List<Double>> getTempStats() {
    return statisticsRepository.getTempAverageWeek();
  }

  public LiveData<List<Double>> getHumStats() {
    return statisticsRepository.getHumAverageWeek();
  }

  public LiveData<List<Double>> getCo2Stats() {
    return statisticsRepository.getCo2AverageWeek();
  }

  public void getTempStatsFromRepo(String roomId) {
    statisticsRepository.getTempStats(roomId);
  }

  public void getHumStatsFromRepo(String roomId) {
    statisticsRepository.getHumStats(roomId);
  }

  public void getCo2StatsFromRepo(String roomId) {
    statisticsRepository.getCo2Stats(roomId);
  }

}

package com.example.sep4android.RepositoryIntefaces;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface StatisticsRepositoryInterface {

  LiveData<List<Double>> getTempStats(String roomId) ;

  LiveData<List<Double>> getHumStats(String roomId);

  LiveData<List<Double>> getCo2Stats(String roomId) ;

}

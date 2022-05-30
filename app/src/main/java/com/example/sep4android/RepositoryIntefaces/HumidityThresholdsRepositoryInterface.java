package com.example.sep4android.RepositoryIntefaces;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;

import java.util.List;

public interface HumidityThresholdsRepositoryInterface {
  LiveData<String> setResult();

  LiveData<List<HumidityThresholdObject>> getHumidityThresholds(String roomId);

  LiveData<String> addHumidityThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue);

  LiveData<String> deleteHumidityThreshold(int thresholdHumidityId);

}

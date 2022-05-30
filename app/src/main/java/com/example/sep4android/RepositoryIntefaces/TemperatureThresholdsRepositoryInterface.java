package com.example.sep4android.RepositoryIntefaces;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.TemperatureThresholdObject;

import java.util.List;

public interface TemperatureThresholdsRepositoryInterface {

  LiveData<String> setResult();

  LiveData<List<TemperatureThresholdObject>> getTemperatureThresholds(String roomId);

  LiveData<String> addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue);

  LiveData<String> deleteTemperatureThreshold(int thresholdHumidityId);
}

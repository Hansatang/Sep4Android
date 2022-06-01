package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.Objects.TemperatureThresholdObject;
import com.example.sep4android.Repositories.TemperatureThresholdRepository;

import java.util.List;

/**
 * View Model class for temperature threshold
 */
public class TemperatureThresholdViewModel extends AndroidViewModel {
  private final TemperatureThresholdRepository temperatureThresholdRepository;
  private final MediatorLiveData<List<TemperatureThresholdObject>> temperatureThresholdsLiveData;
  private final MediatorLiveData<String> statusLiveData;


  public TemperatureThresholdViewModel(@NonNull Application app) {
    super(app);
    temperatureThresholdRepository = TemperatureThresholdRepository.getInstance();
    temperatureThresholdsLiveData = new MediatorLiveData<>();
    statusLiveData = new MediatorLiveData<>();
  }

  public LiveData<List<TemperatureThresholdObject>> getTempThresholdsLiveData() {
    return temperatureThresholdsLiveData;
  }

  public LiveData<String> getStatusLiveData() {
    return statusLiveData;
  }

  public void getTemperatureThresholds(String roomId) {
    temperatureThresholdsLiveData.addSource(temperatureThresholdRepository.getTemperatureThresholds(roomId), temperatureThresholdsLiveData::setValue);
  }

  public void addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    statusLiveData.addSource(temperatureThresholdRepository.addTemperatureThreshold(roomId, startTime, endTime, maxValue, minValue), statusLiveData::setValue);
  }


  public void deleteTemperatureThreshold(int thresholdId) {
    statusLiveData.addSource(temperatureThresholdRepository.deleteTemperatureThreshold(thresholdId), statusLiveData::setValue);
  }

  public void setResult() {
    statusLiveData.addSource(temperatureThresholdRepository.setResult(), statusLiveData::setValue);
  }

  public void updateTemperatureThreshold(TemperatureThresholdObject temperatureThresholdObject) {
    statusLiveData.addSource(temperatureThresholdRepository.updateHumidityThreshold(temperatureThresholdObject), statusLiveData::setValue);
  }
}

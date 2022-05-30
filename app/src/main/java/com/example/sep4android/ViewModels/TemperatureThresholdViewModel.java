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
  private final MediatorLiveData<List<TemperatureThresholdObject>> temperatureThresholds;
  private final MediatorLiveData<String> status;


  public TemperatureThresholdViewModel(@NonNull Application application) {
    super(application);
    temperatureThresholdRepository = TemperatureThresholdRepository.getInstance();
    temperatureThresholds = new MediatorLiveData<>();
    status = new MediatorLiveData<>();
  }

  public LiveData<List<TemperatureThresholdObject>> getThresholds() {
    return temperatureThresholds;
  }

  public LiveData<String> getStatus() {
    return status;
  }

  public void getThresholdFromRepo(String roomId) {
    temperatureThresholds.addSource(temperatureThresholdRepository.getTemperatureThresholds(roomId), temperatureThresholds::setValue);
  }

  public void addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    status.addSource(temperatureThresholdRepository.addTemperatureThreshold(roomId, startTime, endTime, maxValue, minValue), status::setValue);
  }


  public void deleteTemperatureThreshold(int thresholdId) {
    status.addSource(temperatureThresholdRepository.deleteTemperatureThreshold(thresholdId), status::setValue);
  }

  public void setResult() {
    status.addSource(temperatureThresholdRepository.setResult(), status::setValue);
  }
}

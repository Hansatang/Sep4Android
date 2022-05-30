package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Objects.TemperatureThresholdObject;
import com.example.sep4android.Repositories.TemperatureThresholdRepositories;

import java.util.List;

/**
 * View Model class for temperature threshold
 */
public class TemperatureThresholdViewModel extends AndroidViewModel {
  private final TemperatureThresholdRepositories repository;


  public TemperatureThresholdViewModel(@NonNull Application application) {
    super(application);
    repository = TemperatureThresholdRepositories.getInstance();
  }

  public LiveData<List<TemperatureThresholdObject>> getThresholds() {
    return repository.getTemperatureThresholds();
  }

  public LiveData<String> getStatus() {
    return repository.getStatus();
  }

  public void addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    repository.addTemperatureThreshold(roomId, startTime, endTime, maxValue, minValue);
  }

  public void deleteTemperatureThreshold(int id) {
    repository.deleteTemperatureThreshold(id);
  }

  public void getThresholdFromRepo(String roomId) {
    repository.getTemperatureThresholds(roomId);
  }

  public void setResult() {
    repository.setResult();
  }
}

package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.util.List;

public class ArchiveViewModel extends AndroidViewModel {
  private MeasurementRepository repository;

  public ArchiveViewModel(Application app) {
    super(app);
    repository = MeasurementRepository.getInstance(app);
  }

  public LiveData<List<MeasurementsObject>> getMeasurements() {
    return repository.getMeasurements();
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsByDate(LocalDateTime clickedItem, String roomId) {
    return repository.getMeasurementsByDate(clickedItem,roomId);
  }

  public LiveData<List<MeasurementsObject>> filter() {
    return repository.filter(null);
  }



  public LiveData<String> getStatus(){
    return repository.getStatus();
  }

  public void getMeasurementsRoom(String roomId) {
    repository.getMeasurementRoom(roomId);
  }
}
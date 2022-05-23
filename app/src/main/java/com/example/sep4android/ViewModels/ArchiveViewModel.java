package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Repositories.MeasurementRepository;

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

  public LiveData<String> getStatus(){
    return repository.getStatus();
  }

  public void getMeasurementsRoom(String roomId) {
    repository.getMeasurementRoom(roomId);
  }
}
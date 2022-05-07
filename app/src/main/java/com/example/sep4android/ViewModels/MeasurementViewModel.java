package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.MeasurementObject;
import com.example.sep4android.Repositories.MeasurementRepository;

import java.util.List;

public class MeasurementViewModel extends AndroidViewModel {
  private MeasurementRepository repository;

  public MeasurementViewModel(Application app) {
    super(app);
    repository = MeasurementRepository.getInstance(app);
  }

  public LiveData<List<MeasurementObject>> getMeasurements() {
    return repository.getMeasurements();
  }

  public void getLatestMeasurementsFromRepo(String userId) {
    repository.getLatest(userId);
  }
}
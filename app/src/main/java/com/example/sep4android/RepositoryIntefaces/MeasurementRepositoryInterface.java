package com.example.sep4android.RepositoryIntefaces;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.MeasurementsObject;

import java.util.List;

public interface MeasurementRepositoryInterface {

  public LiveData<List<MeasurementsObject>> getMeasurementsAllRooms(String userId);
}

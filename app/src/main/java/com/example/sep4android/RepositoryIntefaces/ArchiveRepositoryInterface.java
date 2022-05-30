package com.example.sep4android.RepositoryIntefaces;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;

import java.util.List;

public interface ArchiveRepositoryInterface {

  void insertAllMeasurements(MeasurementsObject[] measurementsObjects);

  void insertAllRooms(RoomObject[] roomObjects);

  LiveData<List<RoomObject>> getRooms();

  LiveData<List<MeasurementsObject>> getMeasurementByID(String id, String date);
}

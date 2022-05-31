package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.MeasurementRepository;
import com.example.sep4android.Util.DateFormatter;

import java.time.LocalDateTime;
import java.util.List;

/**
 * View Model class for archive
 */
public class ArchiveViewModel extends AndroidViewModel {
  private final String TAG = "ArchiveViewModel";
  private final MeasurementRepository measurementRepository;
  private final ArchiveRepository archiveRepository;
  private final MediatorLiveData<List<MeasurementsObject>> roomMeasurementsLiveData;
  private final MediatorLiveData<List<RoomObject>> roomsLiveData;
  private final MediatorLiveData<List<MeasurementsObject>> measurementsByDateLiveData;

  public ArchiveViewModel(Application app) {
    super(app);
    measurementRepository = MeasurementRepository.getInstance();
    archiveRepository = ArchiveRepository.getInstance(app);
    roomMeasurementsLiveData = new MediatorLiveData<>();
    measurementsByDateLiveData = new MediatorLiveData<>();
    roomsLiveData = new MediatorLiveData<>();
  }

  public LiveData<List<RoomObject>> getRoomsLocalLiveData() {
    return roomsLiveData;
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsLocalLiveData() {
    return measurementsByDateLiveData;
  }

  public void getRoomsLocal() {
    roomsLiveData.addSource(archiveRepository.getRooms(), roomsLiveData::setValue);
  }

  public void getMeasurementsLocal(LocalDateTime clickedItem, String roomId) {
    measurementsByDateLiveData.addSource(archiveRepository.getMeasurementByID(roomId, DateFormatter.getFormattedDateForArchive(clickedItem))
        , measurementsByDateLiveData::setValue);

  }

  public void getMeasurementsFromAllRooms(String userId) {
    roomMeasurementsLiveData.addSource(measurementRepository.getMeasurementsFromAllRooms(userId),
        measurementsObjects -> archiveRepository.insertAllMeasurements(measurementsObjects.toArray(new MeasurementsObject[0])));
  }
}
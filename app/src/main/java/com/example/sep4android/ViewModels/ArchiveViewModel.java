package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.MeasurementRepository;
import com.example.sep4android.Util.DateFormatter;

import java.time.LocalDateTime;
import java.util.List;

public class ArchiveViewModel extends AndroidViewModel {
  private final String TAG = "ArchiveViewModel";
  private final MeasurementRepository measurementRepository;
  private final ArchiveRepository archiveRepository;

  public ArchiveViewModel(Application app) {
    super(app);
    measurementRepository = MeasurementRepository.getInstance(app);
    archiveRepository = ArchiveRepository.getInstance(app);
  }

  public LiveData<List<RoomObject>> getRoomsLocal() {
    return archiveRepository.getRooms();
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsByDate() {
    return measurementRepository.getMeasurementsByDateLiveData();
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsLocal(LocalDateTime clickedItem, String roomId) {
    return archiveRepository.getMeasurementByID(roomId, DateFormatter.getFormattedDateForArchive(clickedItem));
  }

  public LiveData<String> getStatus(){
    return measurementRepository.getStatusLiveData();
  }

  public void getMeasurementsAllRoom(String userId) {
    measurementRepository.getMeasurementsAllRooms(userId);
  }
}
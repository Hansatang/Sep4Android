package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.MeasurementRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

  public LiveData<List<MeasurementsObject>> getMeasurements() {
    return measurementRepository.getRoomMeasurementsLiveData();
  }

  public void getMeasurementsByDate(LocalDateTime clickedItem, String roomId) {
    measurementRepository.getMeasurementsByDate(clickedItem,roomId);
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsByDate() {
    return measurementRepository.getMeasurementsByDateLiveData();
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsLocal(LocalDateTime clickedItem, String roomId) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    System.out.println("Time " + dtf.format(clickedItem));
    return archiveRepository.getMeasurementByID(roomId, dtf.format(clickedItem));
  }


  public LiveData<String> getStatus(){
    return measurementRepository.getStatusLiveData();
  }

  public void getMeasurementsRoom(String roomId) {
    measurementRepository.getMeasurements(roomId);
  }

  public void getMeasurementsAllRoom(String userId) {
    measurementRepository.getMeasurementsAllRooms(userId);
  }
}
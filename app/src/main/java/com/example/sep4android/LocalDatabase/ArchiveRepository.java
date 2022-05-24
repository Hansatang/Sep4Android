package com.example.sep4android.LocalDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ArchiveRepository {
  private static ArchiveRepository instance;
  private final RoomDao roomDao;
  private final LiveData<List<MeasurementsObject>> listLiveData;
  private final ExecutorService executorService;

  private ArchiveRepository(Application application) {
    ArchiveDatabase database = ArchiveDatabase.getInstance(application);
    roomDao = database.roomDao();
    listLiveData = roomDao.getAllArchive();
    executorService = Executors.newFixedThreadPool(2);
  }

  public static synchronized ArchiveRepository getInstance(Application application) {
    if (instance == null)
      instance = new ArchiveRepository(application);

    return instance;
  }

  public LiveData<List<MeasurementsObject>> getListLiveData() {
    return listLiveData;
  }

  public void insertAllMeasurements(MeasurementsObject[] measurementsObjects) {
    System.out.println("DelCreMeasurement");
    executorService.execute(() -> roomDao.deleteAndCreateMeasurements(measurementsObjects));
  }

  public void insertAllRooms(RoomObject[] roomObjects) {
    System.out.println("DelCreRooms");
    executorService.execute(() -> roomDao.deleteAndCreateRooms(roomObjects));
  }

  public void deleteAllMeasurement() {
    executorService.execute(roomDao::deleteAllMeasurement);
  }
  public LiveData<List<MeasurementsObject>> getMeasurementByID(String id, String date){
    return roomDao.getArchiveById(id, date);
  }

  public void deleteAll() {
    executorService.execute(roomDao::deleteAll);
  }
}

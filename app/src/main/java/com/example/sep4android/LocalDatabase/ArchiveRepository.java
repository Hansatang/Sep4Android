package com.example.sep4android.LocalDatabase;

import android.app.Application;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository for accessing local database
 */
public class ArchiveRepository {
  private static ArchiveRepository instance;
  private final RoomDao roomDao;
  private final ExecutorService executorService;

  private ArchiveRepository(Application application) {
    ArchiveDatabase database = ArchiveDatabase.getInstance(application);
    roomDao = database.roomDao();
    executorService = Executors.newFixedThreadPool(2);
  }

  public static synchronized ArchiveRepository getInstance(Application application) {
    if (instance == null)
      instance = new ArchiveRepository(application);
    return instance;
  }

  /**
   * Inserting all measurement objects into the local database
   *
   * @param measurementsObjects array of measurement object
   */
  public void insertAllMeasurements(MeasurementsObject[] measurementsObjects) {
    executorService.execute(() -> roomDao.deleteAndCreateMeasurements(measurementsObjects));
  }

  /**
   * Inserting all rooms into the local database
   *
   * @param roomObjects array of rooms
   */
  public void insertAllRooms(RoomObject[] roomObjects) {
    executorService.execute(() -> roomDao.deleteAndCreateRooms(roomObjects));
  }


  /**
   * Getting all the rooms from the local database
   *
   * @return returns a list of rooms
   */
  public LiveData<List<RoomObject>> getRooms() {
    return roomDao.getAllArchiveRooms();
  }

  /**
   * Getting measurement objects from the local database by id and date
   *
   * @param id   room id that the measurement is assigned to
   * @param date date within the measurement was taken
   * @return return a list of measurement object
   */
  public LiveData<List<MeasurementsObject>> getMeasurementByID(String id, String date) {
    return roomDao.getArchiveById(id, date);
  }
}

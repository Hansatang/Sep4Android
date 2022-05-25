package com.example.sep4android.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;

import java.util.List;

@Dao
public abstract class RoomDao {

  @Transaction
  public void deleteAndCreateMeasurements(MeasurementsObject... measurementsObjects) {
    deleteAllMeasurement();
    insertAllMeasurement(measurementsObjects);
  }

  @Transaction
  public void deleteAndCreateRooms(RoomObject... roomObjects) {
    deleteAllRooms();
    insertAllRooms(roomObjects);
  }

  @Query("DELETE FROM archivedMeasurements")
  public abstract void deleteAllMeasurement();

  @Query("DELETE FROM archivedRooms")
  public abstract void deleteAllRooms();

  @Query("SELECT * FROM archivedMeasurements ORDER BY date DESC")
  public abstract LiveData<List<MeasurementsObject>> getAllArchiveMeasurements();

  @Query("SELECT * FROM archivedRooms")
  public abstract LiveData<List<RoomObject>> getAllArchiveRooms();

  @Query("SELECT * FROM archivedRooms")
  public abstract List<RoomObject> getAllArchiveRoomsA();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertAllMeasurement(MeasurementsObject... measurementsObjects);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertAllRooms(RoomObject... measurementsObjects);

  @Query("SELECT * FROM archivedMeasurements WHERE roomId = :id AND date LIKE '%' || :date || '%'")
  public abstract LiveData<List<MeasurementsObject>> getArchiveById(String id, String date);

  @Query("SELECT * FROM archivedRooms WHERE roomId = :roomId")
  public abstract LiveData<RoomObject> getRoomById(String roomId);

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertAll(MeasurementsObject... measurementsObjects);

}

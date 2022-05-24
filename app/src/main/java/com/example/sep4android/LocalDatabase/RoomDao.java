package com.example.sep4android.LocalDatabase;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Transaction;

import com.example.sep4android.Objects.MeasurementsObject;

import java.util.List;

@Dao
public abstract class RoomDao {

  @Transaction
  public void deleteAndCreate(MeasurementsObject... measurementsObjects) {
    deleteAll();
    insertAll(measurementsObjects);
  }

  @Query("DELETE FROM archivedMeasurements")
  public abstract void deleteAll();

  @Query("SELECT * FROM archivedMeasurements ORDER BY date DESC")
  public abstract LiveData<List<MeasurementsObject>> getAllArchive();

  @Insert(onConflict = OnConflictStrategy.REPLACE)
  public abstract void insertAll(MeasurementsObject... measurementsObjects);

}

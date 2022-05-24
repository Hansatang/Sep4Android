package com.example.sep4android.LocalDatabase;

import android.content.Context;

import androidx.room.Database;

import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Objects.MeasurementsObject;

@Database(entities = {MeasurementsObject.class, RoomObject.class}, version = 1)
public abstract class ArchiveDatabase extends RoomDatabase {

  private static ArchiveDatabase instance;

  public abstract RoomDao roomDao();

  public static synchronized ArchiveDatabase getInstance(Context context) {
    if (instance == null) {
      instance = Room.databaseBuilder(context,
          ArchiveDatabase.class, "archive")
          .fallbackToDestructiveMigration()
          .build();
    }
    return instance;
  }
}

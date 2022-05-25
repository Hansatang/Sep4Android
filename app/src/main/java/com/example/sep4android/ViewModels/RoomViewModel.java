package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.RoomRepository;
import com.example.sep4android.Repositories.TokenRepository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class RoomViewModel extends AndroidViewModel {

  private final RoomRepository roomRepository;
  private final TokenRepository tokenRepository;
  private final ArchiveRepository archiveRepository;

  public RoomViewModel(Application app) {
    super(app);
    archiveRepository = ArchiveRepository.getInstance(app);
    roomRepository = RoomRepository.getInstance(app);
    tokenRepository = TokenRepository.getInstance(app);
  }

  public LiveData<List<RoomObject>> getRooms() {
    return roomRepository.getRooms();
  }

  public LiveData<List<RoomObject>> getRoomsFromRepo(String uid) {
    return roomRepository.getDatabaseRooms(uid);
  }

  public LiveData<Boolean> addRoomToDatabase(String roomId, String name, String userUID) {
    return roomRepository.addRoomToDatabase(roomId, name, userUID);
  }

  public void deleteToken(String userUID) {
    tokenRepository.deleteToken(userUID);
  }

  public void setResult() {
    roomRepository.setResult();
  }

  public LiveData<List<RoomObject>> getRoomsLocal() {
    return archiveRepository.getRooms();
  }

  public LiveData<List<MeasurementsObject>> getMeasurementsLocal(LocalDateTime clickedItem, String roomId) {
    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    System.out.println("Time " + dtf.format(clickedItem));
    return archiveRepository.getMeasurementByID(roomId,dtf.format(clickedItem));
  }
}


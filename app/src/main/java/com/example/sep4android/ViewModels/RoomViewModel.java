package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.RoomRepository;
import com.example.sep4android.Repositories.TokenRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
  private final RoomRepository roomRepository;
  private final TokenRepository tokenRepository;
  private final ArchiveRepository archiveRepository;

  public RoomViewModel(Application app) {
    super(app);
    archiveRepository = ArchiveRepository.getInstance(app);
    roomRepository = RoomRepository.getInstance(app);
    tokenRepository = TokenRepository.getInstance();
  }

  public LiveData<List<RoomObject>> getRooms() {
    return roomRepository.getRoomsLiveData();
  }

  public LiveData<Integer> getCreationResult() {
    return roomRepository.getCreationResult();
  }

  public void getRoomsFromRepo(String uid) {
    roomRepository.getDatabaseRooms(uid);
  }

  public void addRoomToDatabase(String roomId, String name, String userUID) {
    roomRepository.addRoomToDatabase(roomId, name, userUID);
  }

  public void deleteToken(String userUID) {
    tokenRepository.deleteToken(userUID);
  }

  public void setResult() {
    roomRepository.setResult();
  }

  public void changeName(RoomObject roomObject) {
    roomRepository.changeName( roomObject);
  }

  public void deleteRoom(String roomId) {
    roomRepository.deleteRoom(roomId);
  }

  public void resetMeasurements(String roomId) {
    roomRepository.resetMeasurements(roomId);
  }
}


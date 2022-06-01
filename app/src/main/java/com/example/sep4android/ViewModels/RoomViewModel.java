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

  public RoomViewModel(Application app) {
    super(app);
    this.roomRepository = RoomRepository.getInstance(app);
  }

  public LiveData<List<RoomObject>> getRoomsLiveData() {
    return roomRepository.getRoomsLiveData();
  }

  public LiveData<Integer> getCreationResultLiveData() {
    return roomRepository.getCreationResult();
  }

  public void getRoomsFromRepo(String uid) {
    roomRepository.getDatabaseRooms(uid);
  }

  public void addRoom(String roomId, String name, String userUID) {
    roomRepository.addRoomToDatabase(roomId, name, userUID);
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


package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.Room;
import com.example.sep4android.Repositories.RoomRepository;
import com.example.sep4android.Repositories.TokenRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {

  private RoomRepository roomRepository;
  private TokenRepository tokenRepository;

  public RoomViewModel(Application app) {
    super(app);
    roomRepository = RoomRepository.getInstance(app);
    tokenRepository = TokenRepository.getInstance(app);
  }

  public LiveData<List<Room>> getRooms() {
    return roomRepository.getRooms();
  }

  public LiveData<List<Room>> getRoomsFromRepo(String uid) {
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
}


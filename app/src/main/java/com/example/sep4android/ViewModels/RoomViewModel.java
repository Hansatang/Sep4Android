package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.RoomRepository;
import com.example.sep4android.Repositories.TokenRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
  private final RoomRepository roomRepository;
  private final TokenRepository tokenRepository;
  private final ArchiveRepository archiveRepository;
  private final MediatorLiveData<List<RoomObject>> roomsLiveData;
  private final MediatorLiveData<Integer> creationResult;

  public RoomViewModel(Application app) {
    super(app);
    roomRepository = RoomRepository.getInstance();
    roomsLiveData = new MediatorLiveData<>();
    creationResult = new MediatorLiveData<>();
    archiveRepository = ArchiveRepository.getInstance(app);
    tokenRepository = TokenRepository.getInstance();
  }

  public LiveData<List<RoomObject>> getRooms() {
    return roomsLiveData;
  }

  public LiveData<Integer> getCreationResult() {
    return creationResult;
  }

  public void getRoomsFromRepo(String uid) {
    roomsLiveData.addSource(roomRepository.getDatabaseRooms(uid), roomObjects -> {
      archiveRepository.insertAllRooms(roomObjects.toArray(new RoomObject[0]));
      roomsLiveData.setValue(roomObjects);
    });
  }

  public void addRoomToDatabase(String roomId, String name, String userUID) {
    creationResult.addSource(roomRepository.addRoomToDatabase(roomId, name, userUID), creationResult::setValue);
  }

  public void deleteToken(String userUID) {
    tokenRepository.deleteToken(userUID);
  }

  public void setResult() {
    creationResult.addSource(roomRepository.setResult(), creationResult::setValue);
  }

  public void changeName(RoomObject roomObject) {
    creationResult.addSource(roomRepository.changeName(roomObject), creationResult::setValue);

  }

  public void deleteRoom(String roomId) {
    creationResult.addSource(roomRepository.deleteRoom(roomId), creationResult::setValue);
  }

  public void resetMeasurements(String roomId) {
    creationResult.addSource(roomRepository.resetMeasurements(roomId), creationResult::setValue);
  }
}


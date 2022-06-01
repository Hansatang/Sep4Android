package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.RoomRepository;

import java.util.List;

/**
 * View Model class for room
 */
public class RoomViewModel extends AndroidViewModel {
  private final RoomRepository roomRepository;
  private final ArchiveRepository archiveRepository;
  private final MediatorLiveData<List<RoomObject>> roomsLiveData;
  private final MediatorLiveData<Integer> creationResultLiveData;

  public RoomViewModel(Application app) {
    super(app);
    roomRepository = RoomRepository.getInstance();
    roomsLiveData = new MediatorLiveData<>();
    creationResultLiveData = new MediatorLiveData<>();
    archiveRepository = ArchiveRepository.getInstance(app);
  }

  public LiveData<List<RoomObject>> getRoomsLiveData() {
    return roomsLiveData;
  }

  public LiveData<Integer> getCreationResultLiveData() {
    return creationResultLiveData;
  }

  public void getRoomsFromRepo(String uid) {
    roomsLiveData.addSource(roomRepository.getDatabaseRooms(uid), roomObjects -> {
      System.out.println("Insert");
      archiveRepository.insertAllRooms(roomObjects.toArray(new RoomObject[0]));
      roomsLiveData.setValue(roomObjects);
    });
  }

  public void addRoom(String roomId, String name, String userUID) {
    creationResultLiveData.addSource(roomRepository.addRoom(roomId, name, userUID), creationResultLiveData::setValue);
  }

  public void setResult() {
    creationResultLiveData.addSource(roomRepository.setResult(), creationResultLiveData::setValue);
  }

  public void changeRoomName(RoomObject roomObject) {
    creationResultLiveData.addSource(roomRepository.changeRoomName(roomObject), creationResultLiveData::setValue);

  }

  public void deleteRoom(String roomId) {
    creationResultLiveData.addSource(roomRepository.deleteRoom(roomId), creationResultLiveData::setValue);
  }

  public void resetRoomMeasurements(String roomId) {
    creationResultLiveData.addSource(roomRepository.resetRoomMeasurements(roomId), creationResultLiveData::setValue);
  }
}


package com.example.sep4android.RepositoryIntefaces;

import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.RoomObject;

import java.util.List;

public interface RoomRepositoryInterface {

  LiveData<Integer>  setResult() ;

  LiveData<List<RoomObject>> getDatabaseRooms(String uid);

  LiveData<Integer>  addRoomToDatabase(String roomId, String name, String userUID) ;

  LiveData<Integer>  changeName(RoomObject roomObject) ;

  LiveData<Integer>  deleteRoom(String roomId) ;

  LiveData<Integer>  resetMeasurements(String roomId) ;
}

package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.RoomObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Repository for room
 */
public class RoomRepository {
  private final DatabaseApi databaseApi;
  private final String TAG = "RoomRepository";
  private static RoomRepository instance;


  /**
   * Simple constructor initializing room objects in a new list
   */
  private RoomRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  public static synchronized RoomRepository getInstance() {
    if (instance == null)
      instance = new RoomRepository();
    return instance;
  }

  public LiveData<Integer> setResult() {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    liveData.setValue(0);
    return liveData;
  }

  /**
   * Getting the rooms for a specific user
   *
   * @param uid user id
   */
  public LiveData<List<RoomObject>> getDatabaseRooms(String uid) {
    final MutableLiveData<List<RoomObject>> liveData = new MutableLiveData<>();
    Call<List<RoomObject>> call = databaseApi.getRoomByUserId(uid);
    call.enqueue(new Callback<List<RoomObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<RoomObject>> call, Response<List<RoomObject>> response) {
                     if (response.isSuccessful()) {
                       Log.i(TAG, "Room Get Call response: " + response);
                       List<RoomObject> rs = response.body();
                       liveData.setValue(rs);
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<RoomObject>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went wrong get rooms");
                   }
                 }
    );
    return liveData;
  }

  /**
   * Adding a room to the database
   *
   * @param roomId  an id for the room object
   * @param name    name of the room object
   * @param userUID user id that created the room
   */
  public LiveData<Integer> addRoom(String roomId, String name, String userUID) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    RoomObject roomObjectToCreate = new RoomObject(roomId, name, userUID, null, null);
    Call<Integer> call = databaseApi.addRoom(roomObjectToCreate);
    call.enqueue(new Callback<Integer>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<Integer> call, Response<Integer> response) {
                     Log.i(TAG, "Room Add Call response: " + response);
                     if (response.isSuccessful()) {
                       if (response.body() == 200) {
                         liveData.setValue(200);
                       } else if (response.body() == 417) {
                         liveData.setValue(417);
                       }
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<Integer> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went with add room");
                   }
                 }
    );
    return liveData;
  }

  /**
   * Changing the name of an already existing room object
   *
   * @param roomObject the room that the name change needs to be performed
   */
  public LiveData<Integer> changeRoomName(RoomObject roomObject) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    Call<Integer> call = databaseApi.changeName(roomObject);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG, "Room Change Name Call response: " + response);
        if (response.isSuccessful()) {
          liveData.setValue(200);
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<Integer> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went change name");
      }
    });
    return liveData;
  }

  /**
   * Deleting a room from the database
   *
   * @param roomId roomId for the selected room for deleting
   */
  public LiveData<Integer> deleteRoom(String roomId) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    Call<Integer> call = databaseApi.deleteRoom(roomId);
    System.out.println("DELETE room");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG, "Room Delete Call response: " + response);
        if (response.isSuccessful()) {
          liveData.setValue(200);
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<Integer> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went with delete Name");
      }
    });
    return liveData;
  }

  /**
   * Resetting (deleting all measurements) a room in the database
   *
   * @param roomId desired room for resetting
   */
  public LiveData<Integer> resetRoomMeasurements(String roomId) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    Call<Integer> call = databaseApi.resetMeasurements(roomId);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG, "Room Reset Call response: " + response);
        if (response.isSuccessful()) {
          System.out.println("Complete");
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<Integer> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went with reset measurement");
      }
    });
    return liveData;
  }
}


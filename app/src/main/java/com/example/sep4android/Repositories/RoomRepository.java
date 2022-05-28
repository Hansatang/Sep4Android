package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.RoomObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class RoomRepository {
  private final String TAG = "RoomRepository";
  private final ArchiveRepository archiveRepository;
  private static RoomRepository instance;
  private final MutableLiveData<List<RoomObject>> roomsLiveData;
  private final MutableLiveData<Integer> creationResult;

  private RoomRepository(Application application) {
    archiveRepository = ArchiveRepository.getInstance(application);
    roomsLiveData = new MutableLiveData<>();
    creationResult = new MutableLiveData<>();
  }

  public static synchronized RoomRepository getInstance(Application application) {
    if (instance == null)
      instance = new RoomRepository(application);
    return instance;
  }

  public LiveData<List<RoomObject>> getRoomsLiveData() {
    return roomsLiveData;
  }

  public MutableLiveData<Integer> getCreationResult() {
    return creationResult;
  }

  public void setResult() {
    creationResult.setValue(0);
  }

  //TODO change username to uid after work
  public void getDatabaseRooms(String uid) {
    Log.i(TAG,"Getting all rooms");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    System.out.println(uid);
    Call<List<RoomObject>> call = databaseApi.getRoomByUserId(uid);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<RoomObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<RoomObject>> call, Response<List<RoomObject>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<RoomObject> rs = response.body();
                       roomsLiveData.setValue(rs);
                       archiveRepository.insertAllRooms(roomsLiveData.getValue().toArray(new RoomObject[0]));
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<RoomObject>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     roomsLiveData.setValue(null);
                     Log.i("Retrofit", "Something went wrong get rooms");
                   }
                 }
    );
  }

  public void addRoomToDatabase(String roomId, String name, String userUID) {
    Log.i(TAG, "Adding rooms to database");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    RoomObject roomObjectToCreate = new RoomObject(roomId, name, userUID, null, null);
    Call<Integer> call = databaseApi.addRoom(roomObjectToCreate);
    System.out.println("Post");
    call.enqueue(new Callback<Integer>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<Integer> call, Response<Integer> response) {
                     System.out.println(response);
                     if (response.isSuccessful()) {
                       if (response.body()==200){
                         System.out.println("Complete");
                         creationResult.setValue(200);
                       }
                       else if (response.body()==417){
                         creationResult.setValue(417);
                       }

                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<Integer> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     roomsLiveData.setValue(null);
                     Log.i("Retrofit", "Something went with add room");
                   }
                 }
    );
  }

  public void changeName(RoomObject roomObject) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.changeName(roomObject);
    System.out.println("POST");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        if (response.isSuccessful()) {
          System.out.println("Successful");
          creationResult.setValue(200);
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
  }

  public void deleteRoom(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.deleteRoom(roomId);
    System.out.println("DELETE room");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        System.out.println(response);
        if (response.isSuccessful()) {
          System.out.println("Complete");
          creationResult.setValue(200);
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
  }

  public void resetMeasurements(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.resetMeasurements(roomId);
    System.out.println("DELETE");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        System.out.println(response);
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
  }
}


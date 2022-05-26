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
  private final ArchiveRepository repository;
  private static RoomRepository instance;
  private final MutableLiveData<List<RoomObject>> roomsLiveData;
  private final MutableLiveData<Boolean> creationResult;

  private RoomRepository(Application application) {
    repository = ArchiveRepository.getInstance(application);
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

  public MutableLiveData<Boolean> getCreationResult() {
    return creationResult;
  }

  public void setResult() {
    creationResult.setValue(false);
  }

  //TODO change username to uid after work
  public void getDatabaseRooms(String uid) {
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
                       repository.insertAllRooms(roomsLiveData.getValue().toArray(new RoomObject[0]));
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
                       System.out.println("Complete");
                       creationResult.setValue(true);
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

  public void changeName(String roomId, String newName) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.changeName(roomId, newName);
    System.out.println("POST");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        if (response.isSuccessful()) {
          System.out.println("Successful");
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


package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Objects.RoomObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class RoomRepository {
  private final ArchiveRepository repository;
  private static RoomRepository instance;
  private final MutableLiveData<List<RoomObject>> rooms;
  private final MutableLiveData<Boolean> creationResult;

  private RoomRepository(Application application) {
    repository = ArchiveRepository.getInstance(application);
    rooms = new MutableLiveData<>();
    creationResult = new MutableLiveData<>();
  }

  public static synchronized RoomRepository getInstance(Application application) {
    if (instance == null)
      instance = new RoomRepository(application);
    return instance;
  }

  public LiveData<List<RoomObject>> getRooms() {
    return rooms;
  }

  public void setResult() {
    creationResult.setValue(false);
  }

  //TODO change username to uid after work
  public LiveData<List<RoomObject>> getDatabaseRooms(String uid) {
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
                       rooms.setValue(rs);
                       repository.insertAllRooms(rooms.getValue().toArray(new RoomObject[0]));
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<RoomObject>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     rooms.setValue(null);
                     Log.i("Retrofit", "Something went wrong Room:(");
                   }
                 }
    );
    return rooms;
  }

  public LiveData<Boolean> addRoomToDatabase(String roomId, String name, String userUID) {
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
                     rooms.setValue(null);
                     Log.i("Retrofit", "Something went wrong :(");
                   }
                 }
    );
    return creationResult;
  }
}


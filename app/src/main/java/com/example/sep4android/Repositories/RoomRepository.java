package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.RepositoryIntefaces.RoomRepositoryInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class RoomRepository implements RoomRepositoryInterface {
  private final DatabaseApi databaseApi;
  private final String TAG = "RoomRepository";
  private static RoomRepository instance;


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

  //TODO change username to uid after work
  public LiveData<List<RoomObject>> getDatabaseRooms(String uid) {
    final MutableLiveData<List<RoomObject>> liveData = new MutableLiveData<>();
    Call<List<RoomObject>> call = databaseApi.getRoomByUserId(uid);
    call.enqueue(new Callback<List<RoomObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<RoomObject>> call, Response<List<RoomObject>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
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

  public LiveData<Integer> addRoomToDatabase(String roomId, String name, String userUID) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    RoomObject roomObjectToCreate = new RoomObject(roomId, name, userUID, null, null);
    Call<Integer> call = databaseApi.addRoom(roomObjectToCreate);
    System.out.println("Post");
    call.enqueue(new Callback<Integer>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<Integer> call, Response<Integer> response) {
                     System.out.println(response);
                     if (response.isSuccessful()) {
                       if (response.body() == 200) {
                         System.out.println("Complete");
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

  public LiveData<Integer> changeName(RoomObject roomObject) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    Call<Integer> call = databaseApi.changeName(roomObject);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        if (response.isSuccessful()) {
          System.out.println("Successful");
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

  public LiveData<Integer> deleteRoom(String roomId) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    Call<Integer> call = databaseApi.deleteRoom(roomId);
    System.out.println("DELETE room");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        System.out.println(response);
        if (response.isSuccessful()) {
          System.out.println("Complete");
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

  public LiveData<Integer> resetMeasurements(String roomId) {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
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
    return liveData;
  }
}


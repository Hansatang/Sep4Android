package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class RoomRepository {
    private static RoomRepository instance;
    private final MutableLiveData<List<Room>> rooms;

    private RoomRepository(Application application) {
        rooms = new MutableLiveData<>();
    }

    public static synchronized RoomRepository getInstance(Application application) {
        if (instance == null)
            instance = new RoomRepository(application);
        return instance;
    }

    public LiveData<List<Room>> getRooms() {
        return rooms;
    }

    public void getDatabaseRooms(String uid) {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        System.out.println(uid);
        Call<List<Room>> call = databaseApi.getRoomByUserId(3);
        System.out.println("Call");
        call.enqueue(new Callback<List<Room>>() {
                         @EverythingIsNonNull
                         @Override
                         public void onResponse(Call<List<Room>> call, Response<List<Room>> response) {
                             if (response.isSuccessful()) {
                                 System.out.println("YAY");
                                 System.out.println(response);
                                 List<Room> rs = response.body();

                                 rooms.setValue(rs);
                             }
                         }

                         @EverythingIsNonNull
                         @Override
                         public void onFailure(Call<List<Room>> call, Throwable t) {
                             System.out.println(t);
                             System.out.println(t.getMessage());
                             Log.i("Retrofit", "Something went wrong :(");
                         }
                     }
        );
    }

    public void addRoomToDatabase(String roomId, String id, String userUID) {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Room roomToCreate = new Room(roomId,id, userUID, null, null);
        Call<Integer> call = databaseApi.addRoom(roomToCreate);
        System.out.println("Post");
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
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }
}


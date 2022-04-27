package com.example.sep4android;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class RoomRepository {
    private static RoomRepository instance;
    private final MutableLiveData<List<RoomObject>> rooms;


    private RoomRepository(Application application) {
        rooms = new MutableLiveData<>();
        getDatabaseRooms();
    }

    public static synchronized RoomRepository getInstance(Application application) {
        if (instance == null)
            instance = new RoomRepository(application);
        return instance;
    }

    public LiveData<List<RoomObject>> getRooms() {
        return rooms;

    }

    public void getDatabaseRooms() {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<List<RoomObject>> call = databaseApi.getRoom();
        System.out.println("Call");
        call.enqueue(new Callback<List<RoomObject>>() {
                         @EverythingIsNonNull
                         @Override
                         public void onResponse(Call<List<RoomObject>> call, Response<List<RoomObject>> response) {
                             if (response.isSuccessful()) {
                                 System.out.println(response);
                                 List<RoomObject> rs = response.body();
                                 System.out.println(rs.size());
                                 rooms.setValue(rs);
                             }
                         }

                         @EverythingIsNonNull
                         @Override
                         public void onFailure(Call<List<RoomObject>> call, Throwable t) {
                             System.out.println(t);
                             System.out.println(t.getMessage());
                             Log.i("Retrofit", "Something went wrong :(");
                         }
                     }
        );
    }

    public void addRoomToDatabase(int roomId) {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<Integer> call = databaseApi.addRoom(roomId);
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

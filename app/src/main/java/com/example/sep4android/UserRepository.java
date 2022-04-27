package com.example.sep4android;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.room.Room;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class UserRepository {
    private static UserRepository instance;
    private final MutableLiveData<UserObject> user;

    private UserRepository(Application application) {
        user = new MutableLiveData<>();
    }

    public static synchronized UserRepository getInstance(Application application) {
        if (instance == null)
            instance = new UserRepository(application);
        return instance;
    }

    public LiveData<UserObject> getUser() {
        return user;
    }


    public void addUserToDatabase(String name, String password) {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        UserObject temp = new UserObject(name, password, null);
        Call<UserObject> call = databaseApi.addUser(temp);
        System.out.println("SET");
        call.enqueue(new Callback<UserObject>() {
            @Override
            public void onResponse(Call<UserObject> call, Response<UserObject> response) {
                if (response.isSuccessful()) {
                    UserObject temp = new UserObject(name, password, null);
                    user.postValue(temp);
                }
            }

            @Override
            public void onFailure(Call<UserObject> call, Throwable t) {
                System.out.println(t);
                System.out.println(t.getMessage());
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

    public void deleteAccount() {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<UserObject> call = databaseApi.deleteUser();
        System.out.println("Call");
        call.enqueue(new Callback<UserObject>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<UserObject> call, Response<UserObject> response) {
                if (response.isSuccessful()) {
                    System.out.println("Successful");
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<UserObject> call, Throwable t) {
                System.out.println(t);
                System.out.println(t.getMessage());
                Log.i("Retrofit", "Something went wrong :(");
            }
        });

    }

    public void deleteRoomData(int id) {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<RoomObject> call = databaseApi.deleteRoomData();
        System.out.println("Call");
        call.enqueue(new Callback<RoomObject>() {
            @Override
            public void onResponse(Call<RoomObject> call, Response<RoomObject> response) {
                if (response.isSuccessful()) {
                    //NOT FINISHED YET
                }
            }

            @Override
            public void onFailure(Call<RoomObject> call, Throwable t) {
                System.out.println(t);
                System.out.println(t.getMessage());
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }


}

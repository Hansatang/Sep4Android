package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.Objects.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TokenRepository {
  private static TokenRepository instance;

  private TokenRepository(Application application) {
  }

  public static synchronized TokenRepository getInstance(Application application) {
    if (instance == null)
      instance = new TokenRepository(application);
    return instance;
  }

  public void setNewToken(String uid, String token) {
    System.out.println("SetNew");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();

    UserToken userToken = new UserToken(uid, token);
    Call<Integer> call = databaseApi.setToken(userToken);
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
        System.out.println("SetNew Fail");
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong Token:(");
      }
    });
  }

  public void deleteToken(String userUID) {
    System.out.println("SetNew");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();

    UserToken userToken = new UserToken(userUID, null);
    Call<Integer> call = databaseApi.deleteToken(userToken);
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
        Log.i("Retrofit", "Something went wrong delete Token:(");
      }
    });
  }
}
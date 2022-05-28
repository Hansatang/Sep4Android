package com.example.sep4android.Repositories;

import android.util.Log;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.UserToken;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TokenRepository {
  private final String TAG = "TokenRepository";
  private static TokenRepository instance;

  private TokenRepository() {
  }

  public static synchronized TokenRepository getInstance() {
    if (instance == null)
      instance = new TokenRepository();
    return instance;
  }

  public void setNewToken(String uid, String token) {
    Log.i(TAG,"Setting new token");
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
    Log.i(TAG,"Deleting token");
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

package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Objects.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Repository for user token
 */
public class TokenRepository {
  private final String TAG = "TokenRepository";
  private final DatabaseApi databaseApi ;
  private static TokenRepository instance;

  private TokenRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  public static synchronized TokenRepository getInstance() {
    if (instance == null)
      instance = new TokenRepository();
    return instance;
  }

  public LiveData<Integer> setResult() {
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    liveData.setValue(0);
    return liveData;
  }

  /**
   * Setting a new token in the database
   * @param uid users id
   * @param token token created for each user when signing up
   */
  public void setNewToken(String uid, String token) {
    Log.i(TAG,"Setting new token");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    UserToken userToken = new UserToken(uid, token);
    Call<Integer> call = databaseApi.setToken(userToken);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG,"Token Set: " +response);
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

  /**
   * Deleting a token from the database
   * @param userUID user id
   */
  public LiveData<Integer> deleteToken(String userUID) {
    Log.i(TAG,"Deleting token");
    final MutableLiveData<Integer> liveData = new MutableLiveData<>();
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    UserToken userToken = new UserToken(userUID, null);
    Call<Integer> call = databaseApi.deleteToken(userToken);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG,"Token delete: " +response);
        if (response.isSuccessful()) {
          liveData.setValue(response.body());
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
    return liveData;
  }
}

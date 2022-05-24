package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.HumidityThresholdObject;

import java.util.List;

public class HumidityThresholdsRepository {
  private static HumidityThresholdsRepository instance;
  private final MutableLiveData<List<HumidityThresholdObject>> humidityThresholds;

  private HumidityThresholdsRepository(Application application) {
    humidityThresholds = new MutableLiveData<>();
  }

  public static synchronized HumidityThresholdsRepository getInstance(Application application) {
    if (instance == null) {
      instance = new HumidityThresholdsRepository(application);
    }
    return instance;
  }

  public LiveData<List<HumidityThresholdObject>> getHumidityThresholds() {
    return humidityThresholds;
  }

  public void getHumidityThresholds(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    // TODO: 20.05.2022 change hardcoded values
    Call<List<HumidityThresholdObject>> call = databaseApi.getHumidityThresholds("0004A30B00219CAC");
    System.out.println("Call");
    call.enqueue(new Callback<List<HumidityThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<HumidityThresholdObject>> call, Response<List<HumidityThresholdObject>> response) {
        if (response.isSuccessful()) {
          System.out.println("response:");
          System.out.println(response);
          List<HumidityThresholdObject> rs = response.body();
          System.out.println(rs.size());
          humidityThresholds.setValue(rs);
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<List<HumidityThresholdObject>> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong :(");
      }
    });
  }

  public void getAllHumidityThresholds() {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<HumidityThresholdObject>> call = databaseApi.getAllHumidityThresholds();
    System.out.println("Call");
    call.enqueue(new Callback<List<HumidityThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<HumidityThresholdObject>> call, Response<List<HumidityThresholdObject>> response) {
        if (response.isSuccessful()) {
          System.out.println("response:");
          System.out.println(response);
          List<HumidityThresholdObject> rs = response.body();
          System.out.println(rs.size());
          humidityThresholds.setValue(rs);
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<List<HumidityThresholdObject>> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong :(");
      }
    });
  }

  public void addHumidityThreshold(int thresholdHumidityId, String roomId, String startTime, String endTime, double maxValue, double minValue) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    HumidityThresholdObject thresholdToCreate = new HumidityThresholdObject(thresholdHumidityId, roomId, startTime, endTime, maxValue, minValue);
    Call<HumidityThresholdObject> call = databaseApi.addHumidityThreshold(thresholdToCreate);
    System.out.println("POST");
    call.enqueue(new Callback<HumidityThresholdObject>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<HumidityThresholdObject> call, Response<HumidityThresholdObject> response) {
        System.out.println(response);
        if (response.isSuccessful()) {
          System.out.println("Complete");
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<HumidityThresholdObject> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong :(");
      }
    });
  }

  public void deleteHumidityThreshold(int thresholdHumidityId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.deleteHumidityThreshold(thresholdHumidityId);
    System.out.println("POST");
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

  public void deleteAllHumidityThreshold() {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<HumidityThresholdObject> call = databaseApi.deleteAllHumidityThreshold();
    System.out.println("POST");
    call.enqueue(new Callback<HumidityThresholdObject>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<HumidityThresholdObject> call, Response<HumidityThresholdObject> response) {
        System.out.println(response);
        if (response.isSuccessful()) {
          System.out.println("Complete");
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<HumidityThresholdObject> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong :(");
      }
    });
  }

}

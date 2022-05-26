package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.TemperatureThresholdObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class TemperatureThresholdRepositories {
  private final String TAG = "TemperatureThresholdRepositories";
  private static TemperatureThresholdRepositories instance;
  private final MutableLiveData<List<TemperatureThresholdObject>> temperatureThresholds;
  private final MutableLiveData<String> status;

  private TemperatureThresholdRepositories(Application application) {
    temperatureThresholds = new MutableLiveData<>();
    status = new MutableLiveData<>();
  }

  public static synchronized TemperatureThresholdRepositories getInstance(Application application) {
    if (instance == null) {
      instance = new TemperatureThresholdRepositories(application);
    }
    return instance;
  }

  public LiveData<List<TemperatureThresholdObject>> getTemperatureThresholds() {
    return temperatureThresholds;
  }

  public LiveData<String> getStatus() {
    return status;
  }

  public void setResult() {
    status.setValue(null);
  }

  public void getTemperatureThresholds(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<TemperatureThresholdObject>> call = databaseApi.getTemperatureThresholds(roomId);
    System.out.println("Call");
    call.enqueue(new Callback<List<TemperatureThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<TemperatureThresholdObject>> call, Response<List<TemperatureThresholdObject>> response) {
        if (response.isSuccessful()) {
          System.out.println("response:");
          System.out.println(response);
          List<TemperatureThresholdObject> rs = response.body();
          System.out.println(rs.size());
          temperatureThresholds.setValue(rs);
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<List<TemperatureThresholdObject>> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong :(");
      }
    });
  }

  public void getAllTemperatureThresholds() {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<TemperatureThresholdObject>> call = databaseApi.getAllTemperatureThresholds();
    System.out.println("Call");
    call.enqueue(new Callback<List<TemperatureThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<TemperatureThresholdObject>> call, Response<List<TemperatureThresholdObject>> response) {
        if (response.isSuccessful()) {
          System.out.println("response:");
          System.out.println(response);
          List<TemperatureThresholdObject> rs = response.body();
          System.out.println(rs.size());
          temperatureThresholds.setValue(rs);
        }
      }

      @EverythingIsNonNull
      @Override
      public void onFailure(Call<List<TemperatureThresholdObject>> call, Throwable t) {
        System.out.println(t);
        System.out.println(t.getMessage());
        Log.i("Retrofit", "Something went wrong :(");
      }
    });
  }

  public void addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    TemperatureThresholdObject thresholdToCreate = new TemperatureThresholdObject(roomId, startTime, endTime, maxValue, minValue);
    Call<Integer> call = databaseApi.addTemperatureThreshold(thresholdToCreate);
    System.out.println("POST");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        switch (response.body()) {
          case 400:
            status.setValue("Wrong Threshold");
          case 200:
            status.setValue("Complete");
        }
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

  public void deleteTemperatureThreshold(int id) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.deleteTemperatureThreshold(id);
    System.out.println("POST");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        System.out.println(response);
        if (response.isSuccessful()) {
          switch (response.body()) {
            case 400:
              status.setValue("Wrong Threshold");
            case 200:
              status.setValue("Complete");
          }
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

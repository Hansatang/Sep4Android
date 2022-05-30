package com.example.sep4android.Repositories;

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

/**
 * Repository for temperature thresholds
 */
public class TemperatureThresholdRepository{
  private final String TAG = "TemperatureThresholdRepositories";
  private static TemperatureThresholdRepository instance;
  private final DatabaseApi databaseApi;


  /**
   * Simple constructor initializing temperatureThresholdObjects as a new list
   */
  private TemperatureThresholdRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  public static synchronized TemperatureThresholdRepository getInstance() {
    if (instance == null) {
      instance = new TemperatureThresholdRepository();
    }
    return instance;
  }


  public LiveData<String> setResult() {
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    liveData.setValue(null);
    return liveData;
  }

  /**
   * Getting the temperature thresholds from the database for a specific room
   * @param roomId desired room for getting the thresholds
   */
  public LiveData<List<TemperatureThresholdObject>> getTemperatureThresholds(String roomId) {
    Log.i(TAG, "Getting temperature thresholds for a specific room");
    final MutableLiveData<List<TemperatureThresholdObject>> liveData = new MutableLiveData<>();
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
          liveData.setValue(rs);
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
    return liveData;
  }

  /**
   * Adding a new temperature threshold object to the database
   * @param roomId room that the threshold is assigned to
   * @param startTime start time of the threshold
   * @param endTime end time of the threshold
   * @param maxValue max value for the threshold
   * @param minValue min value for the threshold
   */
  public LiveData<String> addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    Log.i(TAG, "Adding temperature threshold");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    TemperatureThresholdObject thresholdToCreate = new TemperatureThresholdObject(roomId, startTime, endTime, maxValue, minValue);
    Call<Integer> call = databaseApi.addTemperatureThreshold(thresholdToCreate);
    System.out.println("POST");
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        switch (response.body()) {
          case 400:
            liveData.setValue("Wrong Threshold");
            break;
          case 200:
            liveData.setValue("Complete");
            break;
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
    return liveData;
  }


  /**
   * Deleting a specific temperature threshold from the database
   * @param id id of the desired threshold
   */
  public LiveData<String> deleteTemperatureThreshold(int id) {
    Log.i(TAG, "Deleting temperature threshold");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
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
              liveData.setValue("Wrong Threshold");
              break;
            case 200:
              liveData.setValue("Complete");
              break;
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
    return liveData;
  }
}

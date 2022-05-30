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

/**
 * Repository for humidity threshold
 */
public class HumidityThresholdsRepository {
  private final String TAG = "HumidityThresholdsRepository";
  private static HumidityThresholdsRepository instance;
  private final MutableLiveData<List<HumidityThresholdObject>> humidityThresholds;
  private final MutableLiveData<String> status;

  /**
   * Simple constructor initializing humidityThresholdObjects as a new list
   */
  private HumidityThresholdsRepository() {
    humidityThresholds = new MutableLiveData<>();
    status = new MutableLiveData<>();
  }

  public static synchronized HumidityThresholdsRepository getInstance() {
    if (instance == null) {
      instance = new HumidityThresholdsRepository();
    }
    return instance;
  }

  public LiveData<List<HumidityThresholdObject>> getHumidityThresholds() {
    return humidityThresholds;
  }

  public LiveData<String> getStatus()
  {
    return status;
  }

  public void setResult(){
    status.setValue(null);
  }

  /**
   * Getting humidity thresholds that have a roomId
   * @param roomId id of the room that the threshold is assigned to
   */
  public void getHumidityThresholds(String roomId) {
    Log.i(TAG, "Get Humidity Threshold Get Call");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<HumidityThresholdObject>> call = databaseApi.getHumidityThresholds(roomId);
    call.enqueue(new Callback<List<HumidityThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<HumidityThresholdObject>> call, Response<List<HumidityThresholdObject>> response) {
        if (response.isSuccessful()) {
          System.out.println("response:");
          System.out.println("hej "+response);
          List<HumidityThresholdObject> rs = response.body();
          System.out.println("Inter "+rs.size());
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

  /**
   * Getting all the humidity thresholds
   */
  public void getAllHumidityThresholds() {
    Log.i(TAG,"Getting all humidity thresholds");
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

  /**
   * Adding a humidity threshold object to the database
   * @param roomId room id that the threshold belongs to
   * @param startTime start time of the threshold
   * @param endTime end time of the threshold
   * @param maxValue maximum value of the threshold
   * @param minValue minimum value of the threshold
   */
  public void addHumidityThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    Log.i(TAG, "Add Humidity Threshold Post Call");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    HumidityThresholdObject thresholdToCreate = new HumidityThresholdObject(roomId, startTime, endTime, maxValue, minValue);
    Call<Integer> call = databaseApi.addHumidityThreshold(thresholdToCreate);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        switch (response.body()) {
          case 400:
            status.setValue("Wrong Threshold");
            break;
          case 200:
            status.setValue("Complete");
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
  }

  /**
   * Deleting a threshold from the database
   * @param thresholdHumidityId id of the threshold
   */
  public void deleteHumidityThreshold(int thresholdHumidityId) {
    Log.i(TAG,"Deleting humidity threshold");
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<Integer> call = databaseApi.deleteHumidityThreshold(thresholdHumidityId);
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
              break;
            case 200:
              status.setValue("Complete");
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
  }

}

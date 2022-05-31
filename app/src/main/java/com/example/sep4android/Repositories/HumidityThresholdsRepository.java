package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.HumidityThresholdObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

/**
 * Repository for humidity threshold
 */
public class HumidityThresholdsRepository {
  private final String TAG = "HumidityThresholdsRepository";
  private static HumidityThresholdsRepository instance;
  private final DatabaseApi databaseApi;

  /**
   * Simple constructor initializing humidityThresholdObjects as a new list
   */
  private HumidityThresholdsRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  public static synchronized HumidityThresholdsRepository getInstance() {
    if (instance == null) {
      instance = new HumidityThresholdsRepository();
    }
    return instance;
  }

  public LiveData<String> setResult() {
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    liveData.setValue(null);
    return liveData;
  }

  /**
   * Getting humidity thresholds that have a roomId
   * @param roomId id of the room that the threshold is assigned to
   */
  public LiveData<List<HumidityThresholdObject>> getHumidityThresholds(String roomId) {
    Log.i(TAG, "Get Humidity Threshold Get Call");
    final MutableLiveData<List<HumidityThresholdObject>> liveData = new MutableLiveData<>();
    Call<List<HumidityThresholdObject>> call = databaseApi.getHumidityThresholds(roomId);
    call.enqueue(new Callback<List<HumidityThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<HumidityThresholdObject>> call, Response<List<HumidityThresholdObject>> response) {
        Log.i(TAG, "Get Humidity Threshold Get Call response: "+ response);
        if (response.isSuccessful()) {
          List<HumidityThresholdObject> rs = response.body();
          liveData.setValue(rs);
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
    return liveData;
  }

  /**
   * Adding a humidity threshold object to the database
   * @param roomId room id that the threshold belongs to
   * @param startTime start time of the threshold
   * @param endTime end time of the threshold
   * @param maxValue maximum value of the threshold
   * @param minValue minimum value of the threshold
   */
  public LiveData<String> addHumidityThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    Log.i(TAG, "Add Humidity Threshold Post Call");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    HumidityThresholdObject thresholdToCreate = new HumidityThresholdObject(roomId, startTime, endTime, maxValue, minValue);
    Call<Integer> call = databaseApi.addHumidityThreshold(thresholdToCreate);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG, "Get Humidity Threshold Add Call response: "+ response);
        if (response.isSuccessful()) {
          switch (response.body()) {
            case 400:
              liveData.setValue("Wrong Threshold");
              break;
            case 200:
              liveData.setValue("Complete");
              break;
          }
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
   * Deleting a threshold from the database
   * @param thresholdHumidityId id of the threshold
   */
  public LiveData<String> deleteHumidityThreshold(int thresholdHumidityId) {
    Log.i(TAG, "Deleting humidity threshold");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    Call<Integer> call = databaseApi.deleteHumidityThreshold(thresholdHumidityId);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG, "Get Humidity Threshold Delete Call response: "+ response);
        if (response.isSuccessful()) {
          switch (response.body()) {
            case 400:
              liveData.setValue("Wrong Threshold");
              break;
            case 200:
              liveData.setValue("Complete");
              break;
          }
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

  public LiveData<String> updateHumidityThreshold(int thresholdHumidityId, String roomId, String startTime, String endTime, double maxValue, double minValue) {
    Log.i(TAG, "Update Humidity Threshold Post Call");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    HumidityThresholdObject thresholdToCreate = new HumidityThresholdObject(thresholdHumidityId,roomId, startTime, endTime, maxValue, minValue);
    Call<Integer> call = databaseApi.updateHumidityThreshold(thresholdToCreate);
    call.enqueue(new Callback<Integer>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<Integer> call, Response<Integer> response) {
        Log.i(TAG, "Humidity Threshold Update Call response: "+ response);
        if (response.isSuccessful()) {
          switch (response.body()) {
            case 200:
              liveData.setValue("Complete");
              break;
            case 400:
              liveData.setValue("Wrong Threshold");
              break;
          }
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

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

public class HumidityThresholdsRepository {
  private final String TAG = "HumidityThresholdsRepository";
  private static HumidityThresholdsRepository instance;
  private final DatabaseApi databaseApi;

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

  public LiveData<List<HumidityThresholdObject>> getHumidityThresholds(String roomId) {
    Log.i(TAG, "Get Humidity Threshold Get Call");
    final MutableLiveData<List<HumidityThresholdObject>> liveData = new MutableLiveData<>();
    Call<List<HumidityThresholdObject>> call = databaseApi.getHumidityThresholds(roomId);
    call.enqueue(new Callback<List<HumidityThresholdObject>>() {
      @EverythingIsNonNull
      @Override
      public void onResponse(Call<List<HumidityThresholdObject>> call, Response<List<HumidityThresholdObject>> response) {
        if (response.isSuccessful()) {
          System.out.println("response:");
          System.out.println("hej " + response);
          List<HumidityThresholdObject> rs = response.body();
          System.out.println("Inter " + rs.size());
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

  public LiveData<String> addHumidityThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue) {
    Log.i(TAG, "Add Humidity Threshold Post Call");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
    HumidityThresholdObject thresholdToCreate = new HumidityThresholdObject(roomId, startTime, endTime, maxValue, minValue);
    Call<Integer> call = databaseApi.addHumidityThreshold(thresholdToCreate);
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

  public LiveData<String> deleteHumidityThreshold(int thresholdHumidityId) {
    Log.i(TAG, "Deleting humidity threshold");
    final MutableLiveData<String> liveData = new MutableLiveData<>();
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

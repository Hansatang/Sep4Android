package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class StatisticsRepository {
  private static StatisticsRepository instance;
  private final MutableLiveData<List<Double>> tempAverageWeek;
  private final MutableLiveData<List<Double>> humAverageWeek;
  private final MutableLiveData<List<Double>> co2AverageWeek;

  public StatisticsRepository() {
    this.tempAverageWeek = new MutableLiveData<>();
    this.humAverageWeek = new MutableLiveData<>();
    this.co2AverageWeek = new MutableLiveData<>();
  }

  public static synchronized StatisticsRepository getInstance() {
    if (instance == null)
      instance = new StatisticsRepository();
    return instance;
  }



  public LiveData<List<Double>> getTempAverageWeek() {
    return tempAverageWeek;
  }

  public LiveData<List<Double>> getHumAverageWeek() {
    return humAverageWeek;
  }

  public LiveData<List<Double>> getCo2AverageWeek() {
    return co2AverageWeek;
  }

  public void getTempStats(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<Double>> call = databaseApi.getTempStats(roomId);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<Double> rs = response.body();
                       tempAverageWeek.setValue(rs);
                     }
                   }
                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<Double>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());

                     Log.i("Retrofit", "Something went wrong get temp ave");
                   }
                 }
    );
  }

  public void getHumStats(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<Double>> call = databaseApi.getHumStats(roomId);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<Double> rs = response.body();
                       humAverageWeek.setValue(rs);
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<Double>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went wrong get hum ave");
                   }
                 }
    );
  }

  public void getCo2Stats(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<Double>> call = databaseApi.getCo2Stats(roomId);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<Double> rs = response.body();
                       co2AverageWeek.setValue(rs);
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<Double>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());

                     Log.i("Retrofit", "Something went wrong get co2 ave");
                   }
                 }
    );
  }
}

package com.example.sep4android.Repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.RepositoryIntefaces.StatisticsRepositoryInterface;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class StatisticsRepository implements StatisticsRepositoryInterface {
  private final DatabaseApi databaseApi;
  private static StatisticsRepository instance;

  private StatisticsRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  public static synchronized StatisticsRepository getInstance() {
    if (instance == null)
      instance = new StatisticsRepository();
    return instance;
  }

  public LiveData<List<Double>> getTempStats(String roomId) {
    final MutableLiveData<List<Double>> liveData = new MutableLiveData<>();
    Call<List<Double>> call = databaseApi.getTempStats(roomId);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<Double> rs = response.body();
                       liveData.setValue(rs);
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
    return liveData;
  }

  public LiveData<List<Double>> getHumStats(String roomId) {
    final MutableLiveData<List<Double>> liveData = new MutableLiveData<>();
    Call<List<Double>> call = databaseApi.getHumStats(roomId);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<Double> rs = response.body();
                       liveData.setValue(rs);
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
    return liveData;
  }

  public LiveData<List<Double>> getCo2Stats(String roomId) {
    final MutableLiveData<List<Double>> liveData = new MutableLiveData<>();
    Call<List<Double>> call = databaseApi.getCo2Stats(roomId);
    System.out.println("Call hello");
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       List<Double> rs = response.body();
                       liveData.setValue(rs);
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
    return liveData;
  }
}

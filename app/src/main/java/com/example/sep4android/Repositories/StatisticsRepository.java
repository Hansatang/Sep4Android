package com.example.sep4android.Repositories;

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

/**
 * Repository for statistic
 */
public class StatisticsRepository {
  private final String TAG = "StatisticsRepository";
  private static StatisticsRepository instance;
  private final DatabaseApi databaseApi;


  /**
   * Simple constructor initializing tempAverage, humAverage and co2Average in a new list
   */
  private StatisticsRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  public static synchronized StatisticsRepository getInstance() {
    if (instance == null)
      instance = new StatisticsRepository();
    return instance;
  }

  /**
   * Getting room temperatures from the database
   *
   * @param roomId desired room to get the measurements
   */
  public LiveData<List<Double>> getTempStats(String roomId) {
    final MutableLiveData<List<Double>> liveData = new MutableLiveData<>();
    Call<List<Double>> call = databaseApi.getTempStats(roomId);
    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     Log.i(TAG, "Statistics Temp Get Call response: " + response);
                     if (response.isSuccessful()) {
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

  /**
   * Getting room humidity statistics from the database
   *
   * @param roomId desired room to get the humidity statistics
   */
  public LiveData<List<Double>> getHumStats(String roomId) {
    final MutableLiveData<List<Double>> liveData = new MutableLiveData<>();
    Call<List<Double>> call = databaseApi.getHumStats(roomId);

    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     Log.i(TAG, "Statistics Hum Get Call response: " + response);
                     if (response.isSuccessful()) {
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

  /**
   * Getting room Co2 measurements from the database
   *
   * @param roomId desired room to get the Co2 measurements
   */
  public LiveData<List<Double>> getCo2Stats(String roomId) {
    final MutableLiveData<List<Double>> liveData = new MutableLiveData<>();
    Call<List<Double>> call = databaseApi.getCo2Stats(roomId);

    call.enqueue(new Callback<List<Double>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<Double>> call, Response<List<Double>> response) {
                     Log.i(TAG, "Statistics Co2 Get Call response: " + response);
                     if (response.isSuccessful()) {
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


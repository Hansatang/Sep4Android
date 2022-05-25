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
    private static  TemperatureThresholdRepositories instance;
    private final MutableLiveData<List<TemperatureThresholdObject>> temperatureThresholds;

    private TemperatureThresholdRepositories(Application application)
    {
        temperatureThresholds = new MutableLiveData<>();
    }

    public static synchronized TemperatureThresholdRepositories getInstance(Application application)
    {
        if (instance == null)
        {
            instance = new TemperatureThresholdRepositories(application);
        }
        return instance;
    }

    public LiveData<List<TemperatureThresholdObject>> getTemperatureThresholds()
    {
        return temperatureThresholds;
    }

    public void getTemperatureThresholds(String roomId)
    {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<List<TemperatureThresholdObject>> call = databaseApi.getTemperatureThresholds(roomId);
        System.out.println("Call");
        call.enqueue(new Callback<List<TemperatureThresholdObject>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<TemperatureThresholdObject>> call, Response<List<TemperatureThresholdObject>> response) {
                if (response.isSuccessful())
                {
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

    public void getAllTemperatureThresholds()
    {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<List<TemperatureThresholdObject>> call = databaseApi.getAllTemperatureThresholds();
        System.out.println("Call");
        call.enqueue(new Callback<List<TemperatureThresholdObject>>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<List<TemperatureThresholdObject>> call, Response<List<TemperatureThresholdObject>> response) {
                if (response.isSuccessful())
                {
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

    public void addTemperatureThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue)
    {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        TemperatureThresholdObject thresholdToCreate = new TemperatureThresholdObject(roomId, startTime, endTime, maxValue, minValue);
        Call<Integer> call = databaseApi.addTemperatureThreshold(thresholdToCreate);
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

    public void deleteTemperatureThreshold(int id)
    {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<Integer> call = databaseApi.deleteTemperatureThreshold(id);
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

    public void deleteAllTemperatureThreshold()
    {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<TemperatureThresholdObject> call = databaseApi.deleteAllTemperatureThreshold();
        System.out.println("POST");
        call.enqueue(new Callback<TemperatureThresholdObject>() {
            @EverythingIsNonNull
            @Override
            public void onResponse(Call<TemperatureThresholdObject> call, Response<TemperatureThresholdObject> response) {
                System.out.println(response);
                if (response.isSuccessful()) {
                    System.out.println("Complete");
                }
            }

            @EverythingIsNonNull
            @Override
            public void onFailure(Call<TemperatureThresholdObject> call, Throwable t) {
                System.out.println(t);
                System.out.println(t.getMessage());
                Log.i("Retrofit", "Something went wrong :(");
            }
        });
    }

}

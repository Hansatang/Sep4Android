package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.MeasurementObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MeasurementRepository {
  private static MeasurementRepository instance;
  private final MutableLiveData<List<MeasurementObject>> measurements;

  private MeasurementRepository(Application application) {
    measurements = new MutableLiveData<>();
  }

  public static synchronized MeasurementRepository getInstance(Application application) {
    if (instance == null)
      instance = new MeasurementRepository(application);
    return instance;
  }

  public LiveData<List<MeasurementObject>> getMeasurements() {
    return measurements;
  }

  public void getLatest(String userId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<MeasurementObject>> call = databaseApi.getMeasurements(userId);
    System.out.println("Call");
    call.enqueue(new Callback<List<MeasurementObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<MeasurementObject>> call, Response<List<MeasurementObject>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       System.out.println(response.body());
                       List<MeasurementObject> rs = response.body();
                       System.out.println(rs.size());
                       measurements.setValue(rs);
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<MeasurementObject>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went wrong :(");
                   }
                 }
    );
  }

}

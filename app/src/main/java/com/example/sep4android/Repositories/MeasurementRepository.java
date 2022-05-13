package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.Objects.MeasurementsObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MeasurementRepository {
  private static MeasurementRepository instance;
  private final MutableLiveData<List<MeasurementsObject>> measurements;

  private MeasurementRepository(Application application) {
    measurements = new MutableLiveData<>();
  }

  public static synchronized MeasurementRepository getInstance(Application application) {
    if (instance == null)
      instance = new MeasurementRepository(application);
    return instance;
  }

  public LiveData<List<MeasurementsObject>> getMeasurements() {
    return measurements;
  }

  public void getMeasurementRoom(String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    Call<List<MeasurementsObject>> call = databaseApi.getMeasurements(roomId);
    System.out.println("Call");
    call.enqueue(new Callback<List<MeasurementsObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<MeasurementsObject>> call, Response<List<MeasurementsObject>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       System.out.println(response.body());
                       List<MeasurementsObject> rs = response.body();
                       System.out.println(rs.size());
                       measurements.setValue(rs);
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<MeasurementsObject>> call, Throwable t) {
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went wrong :(");
                   }
                 }
    );
  }

}

package com.example.sep4android.Repositories;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Database.DatabaseApi;
import com.example.sep4android.Database.DatabaseServiceGenerator;
import com.example.sep4android.LocalDatabase.ArchiveRepository;
import com.example.sep4android.Objects.MeasurementsObject;

import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;

public class MeasurementRepository {
  private final ArchiveRepository repository;
  private static MeasurementRepository instance;
  private final MutableLiveData<List<MeasurementsObject>> measurements;
  private final MutableLiveData<List<MeasurementsObject>> measurementsByDate;
  private final MutableLiveData<String> status;

  private MeasurementRepository(Application application) {
    repository = ArchiveRepository.getInstance(application);
    measurements = new MutableLiveData<>();
    measurementsByDate = new MutableLiveData<>();
    status = new MutableLiveData<>();
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
                       status.setValue("Online");
                       measurements.setValue(rs);
                       repository.insertAll(measurements.getValue().toArray(new MeasurementsObject[0]));
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<MeasurementsObject>> call, Throwable t) {
                     status.setValue("Offline");
                     System.out.println(t);
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went wrong :(");
                   }
                 }
    );
  }

  public LiveData<String> getStatus() {
    return status;
  }

  public LiveData<List<MeasurementsObject>> filter(String str) {
    List<MeasurementsObject> result = new ArrayList<>();
    for (MeasurementsObject mes : measurements.getValue()) {
      if (mes.getTemperature() == 20.00) {
        result.add(mes);
      }
    }
    measurementsByDate.setValue(result);
    return measurementsByDate;

  }

  public LiveData<List<MeasurementsObject>> getMeasurementsByDate(LocalDateTime clickedItem, String roomId) {
    DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
    SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.US);
    Date convertedDatetime = Date.from(clickedItem.atZone(ZoneId.systemDefault()).toInstant());
    String date = dateFormat.format(convertedDatetime);

    Call<List<MeasurementsObject>> call = databaseApi.getMeasurementsByDate(roomId, date);
    call.enqueue(new Callback<List<MeasurementsObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<MeasurementsObject>> call, Response<List<MeasurementsObject>> response) {
                     if (response.isSuccessful()) {
                       System.out.println(response);
                       System.out.println(response.body());
                       List<MeasurementsObject> rs = response.body();
                       System.out.println(rs.size());
                       status.setValue("Online");
                       measurementsByDate.setValue(rs);
                     }
                   }

                   @EverythingIsNonNull
                   @Override
                   public void onFailure(Call<List<MeasurementsObject>> call, Throwable t) {
                     status.setValue("Offline");
                     System.out.println(t);
                     repository.getMeasurementByID(roomId, date).getValue();
                     System.out.println(t.getMessage());
                     Log.i("Retrofit", "Something went wrong :(");
                   }
                 }
    );
    return measurementsByDate;
  }
}

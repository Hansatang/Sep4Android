package com.example.sep4android.Repositories;

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

/**
 * Repository for measurement
 */
public class MeasurementRepository{
  private final String TAG = "MeasurementRepository";
  private static MeasurementRepository instance;
  private final DatabaseApi databaseApi;

  private MeasurementRepository() {
    databaseApi = DatabaseServiceGenerator.getDatabaseApi();
  }

  /**
   * Simple constructor initializing measurement objects in a new list
   */
  public static synchronized MeasurementRepository getInstance() {
    if (instance == null)
      instance = new MeasurementRepository();
    return instance;
  }

  /**
   * Getting measurements that have a specific roomId
   * @param userId room id that the measurement is assigned to
   */
  public LiveData<List<MeasurementsObject>> getMeasurementsFromAllRooms(String userId) {
    final MutableLiveData<List<MeasurementsObject>> liveData = new MutableLiveData<>();
    Log.i(TAG, "Getting room measurements for all rooms");
    Call<List<MeasurementsObject>> call = databaseApi.getMeasurementsAllRooms(userId);
    call.enqueue(new Callback<List<MeasurementsObject>>() {
                   @EverythingIsNonNull
                   @Override
                   public void onResponse(Call<List<MeasurementsObject>> call, Response<List<MeasurementsObject>> response) {
                     Log.i(TAG, "Get Measurements Get Call response: "+ response);
                     if (response.isSuccessful()) {
                       List<MeasurementsObject> rs = response.body();
                       System.out.println(rs.size());
                       liveData.setValue(rs);
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
    return liveData;
  }
}

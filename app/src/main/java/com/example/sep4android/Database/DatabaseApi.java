package com.example.sep4android.Database;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Objects.TemperatureThresholdObject;

import com.example.sep4android.Objects.UserToken;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface DatabaseApi {
  @GET("room/last/{userId}")
  Call<List<RoomObject>> getRoomByUserId(@Path("userId") String userId);

  @POST("room/")
  Call<Integer> addRoom(@Body RoomObject object);

  @GET("measurement/week/{userId}/")
  Call<List<MeasurementsObject>> getMeasurementsAllRooms(@Path("userId") String object);

  @POST("token/")
  Call<Integer> setToken(@Body UserToken userToken);

  @PUT("token/")
  Call<Integer> deleteToken(@Body UserToken userToken);

  @GET("humidityThresholds/{roomId}")
  Call<List<HumidityThresholdObject>> getHumidityThresholds(@Path("roomId") String roomId);

  @GET("temperatureThresholds/{roomId}")
  Call<List<TemperatureThresholdObject>> getTemperatureThresholds(@Path("roomId") String roomId);

  @POST("humidityThresholds/")
  Call<Integer> addHumidityThreshold(@Body HumidityThresholdObject object);

  @POST("temperatureThresholds/")
  Call<Integer> addTemperatureThreshold(@Body TemperatureThresholdObject object);

  @DELETE("humidityThresholds/{id}")
  Call<Integer> deleteHumidityThreshold(@Path("id") int id);

  @DELETE("temperatureThresholds/{id}")
  Call<Integer> deleteTemperatureThreshold(@Path("id") int id);

  @PUT("room/")
  Call<Integer> changeName(@Body RoomObject newName);

  @DELETE("room/{roomId}/")
  Call<Integer> deleteRoom(@Path("roomId") String roomId);

  @DELETE("measurement/room/{roomId}")
  Call<Integer> resetMeasurements(@Path("roomId") String roomId);

  @GET("/measurement/averageTemp/{roomId}")
  Call<List<Double>> getTempStats(@Path("roomId") String roomId);

  @GET("/measurement/averageHumidity/{roomId}")
  Call<List<Double>> getHumStats(@Path("roomId") String roomId);

  @GET("/measurement/averageCo2/{roomId}")
  Call<List<Double>> getCo2Stats(@Path("roomId") String roomId);
}

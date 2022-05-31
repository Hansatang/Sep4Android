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
  @GET("humidityThresholds/{roomId}")
  Call<List<HumidityThresholdObject>> getHumidityThresholds(@Path("roomId") String roomId);

  @POST("humidityThresholds/")
  Call<Integer> addHumidityThreshold(@Body HumidityThresholdObject object);

  @DELETE("humidityThresholds/{id}")
  Call<Integer> deleteHumidityThreshold(@Path("id") int id);

  @DELETE("measurement/room/{roomId}")
  Call<Integer> resetMeasurements(@Path("roomId") String roomId);

  @GET("measurement/week/{userId}/")
  Call<List<MeasurementsObject>> getMeasurementsAllRooms(@Path("userId") String object);

  @GET("/measurement/averageTemp/{roomId}")
  Call<List<Double>> getTempStats(@Path("roomId") String roomId);

  @GET("/measurement/averageHumidity/{roomId}")
  Call<List<Double>> getHumStats(@Path("roomId") String roomId);

  @GET("/measurement/averageCo2/{roomId}")
  Call<List<Double>> getCo2Stats(@Path("roomId") String roomId);

  @POST("token/")
  Call<Integer> setToken(@Body UserToken userToken);

  @PUT("token/")
  Call<Integer> deleteToken(@Body UserToken userToken);

  @POST("room/")
  Call<Integer> addRoom(@Body RoomObject object);

  @PUT("room/")
  Call<Integer> changeName(@Body RoomObject newName);

  @GET("room/last/{userId}")
  Call<List<RoomObject>> getRoomByUserId(@Path("userId") String userId);

  @DELETE("room/{roomId}/")
  Call<Integer> deleteRoom(@Path("roomId") String roomId);

  @GET("temperatureThresholds/{roomId}")
  Call<List<TemperatureThresholdObject>> getTemperatureThresholds(@Path("roomId") String roomId);

  @POST("temperatureThresholds/")
  Call<Integer> addTemperatureThreshold(@Body TemperatureThresholdObject object);

  @DELETE("temperatureThresholds/{id}")
  Call<Integer> deleteTemperatureThreshold(@Path("id") int id);



}

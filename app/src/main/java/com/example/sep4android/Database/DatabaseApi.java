package com.example.sep4android.Database;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Objects.TemperatureThresholdObject;
import com.example.sep4android.Objects.UserObject;
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
  // TODO: 17/05/2022  Change to Get
  @POST("room/last/{userId}")
  Call<List<RoomObject>> getRoomByUserId(@Path("userId") String userId);

  @POST("room/")
  Call<Integer> addRoom(@Body RoomObject object);

  // TODO: 10/05/2022  Change to roomId, and latest is stupid
  @GET("measurement/{roomId}/")
  Call<List<MeasurementsObject>> getMeasurements(@Path("roomId") String object);

  @GET("measurement/week/{userId}/")
  Call<List<MeasurementsObject>> getMeasurementsAllRooms(@Path("userId") String object);

  // TODO: 10/05/2022  Change to roomId, and latest is stupid
  @GET("measurement/{date}/{roomId}/")
  Call<List<MeasurementsObject>> getMeasurementsByDate(@Path("roomId") String roomId, @Path("date") String date);

  @POST("token/")
  Call<Integer> setToken(@Body UserToken userToken);

  @PUT("token/")
  Call<Integer> deleteToken(@Body UserToken userToken);

  @GET("humiditythresholds/{roomId}")
  Call<List<HumidityThresholdObject>> getHumidityThresholds(@Path("roomId") String roomId);

  @GET("all/humiditythresholds/")
  Call<List<HumidityThresholdObject>> getAllHumidityThresholds();

  @GET("temperatureThresholds/{roomId}")
  Call<List<TemperatureThresholdObject>> getTemperatureThresholds(@Path("roomId") String roomId);

  @GET("all/temperatureThresholds/")
  Call<List<TemperatureThresholdObject>> getAllTemperatureThresholds();

  @POST("api/random")
  Call<UserObject> addUser(@Body UserObject object);

  @POST("humidityThresholds/")
  Call<Integer> addHumidityThreshold(@Body HumidityThresholdObject object);

  @POST("temperatureThresholds/")
  Call<Integer> addTemperatureThreshold(@Body TemperatureThresholdObject object);

  @DELETE("api/random")
  Call<UserObject> deleteUser();

  @DELETE("api/random")
  Call<RoomObject> deleteRoomData();

  @DELETE("humidityThresholds/{id}")
  Call<Integer> deleteHumidityThreshold(@Path("id") int id);

  @DELETE("temperatureThresholds/{id}")
  Call<Integer> deleteTemperatureThreshold(@Path("id") int id);

  @POST("room/{roomId}")
  Call<Integer> changeName(@Path("roomId") String roomId, @Body String newName);

  @DELETE("room/{roomId}")
  Call<Integer> deleteRoom(@Path("roomId") String roomId);

  @DELETE("measurement/room/{roomId}")
  Call<Integer> resetMeasurements(@Path("roomId") String roomId);
}

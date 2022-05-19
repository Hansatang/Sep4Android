package com.example.sep4android.Database;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.MeasurementsObject;
import com.example.sep4android.Objects.Room;
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
  Call<List<Room>> getRoomByUserId(@Path("userId") String userId);

  @POST("room/")
  Call<Integer> addRoom(@Body Room object);

  // TODO: 10/05/2022  Change to roomId, and latest is stupid
  @GET("measurement/{roomId}/")
  Call<List<MeasurementsObject>> getMeasurements(@Path("roomId") String object);

  @POST("token/")
  Call<Integer> setToken(@Body UserToken userToken);

  @PUT("token/")
  Call<Integer> deleteToken(@Body UserToken userToken);

  @GET("api/random")
  Call<UserObject> getUser();

  @GET("humiditythresholds/{roomId}")
  Call<List<HumidityThresholdObject>> getHumidityThresholds(@Path("roomId") String roomId);

  @GET("all/humiditythresholds/")
  Call<List<HumidityThresholdObject>> getAllHumidityThresholds();

  @POST("api/random")
  Call<UserObject> addUser(@Body UserObject object);

  @POST("new/humiditythresholds/")
  Call<HumidityThresholdObject> addHumidityThreshold(@Body HumidityThresholdObject object);

  @DELETE("api/random")
  Call<UserObject> deleteUser();

  @DELETE("api/random")
  Call<Room> deleteRoomData();

  @DELETE("humidityThresholds/{id}")
  Call<HumidityThresholdObject> deleteHumidityThreshold(@Path("id") String id);

  @DELETE("removal/humiditythresholds")
  Call<HumidityThresholdObject> deleteAllHumidityThreshold();

}

package com.example.sep4android.Database;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Objects.MeasurementObject;
import com.example.sep4android.Objects.Room;
import com.example.sep4android.Objects.UserObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DatabaseApi {
    @POST("room/last/{userId}")
    Call<List<Room>> getRoomByUserId(@Path("userId") int userId);
    @POST("room/")
    Call<Integer> addRoom(@Body Room object);
    @GET("latest/{userId}")
    Call<List<MeasurementObject>> getMeasurements(@Path("userId") String object);

    @GET("api/random")
    Call<UserObject> getUser();
    @GET("latest/{userId}")
    Call<List<HumidityThresholdObject>> getHumidityThresholds(@Path("userId") String userId);
    @POST("api/random")
    Call<UserObject> addUser(@Body UserObject object);
    @POST()
    Call<HumidityThresholdObject> addHumidityThreshold(@Body HumidityThresholdObject object);
    @DELETE("api/random")
    Call<UserObject> deleteUser();
    @DELETE("api/random")
    Call<Room> deleteRoomData();

}

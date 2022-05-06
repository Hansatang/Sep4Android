package com.example.sep4android.Database;

import com.example.sep4android.Objects.MeasurementObject;
import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Objects.UserObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DatabaseApi {
    @GET("all/rooms/")
    Call<List<RoomObject>> getRoom();
    @POST("room/")
    Call<Integer> addRoom(@Body RoomObject object);
    @GET("latest/{userId}")
    Call<List<MeasurementObject>> getMeasurements(@Path("userId") String object);

    @GET("api/random")
    Call<UserObject> getUser();
    @POST("api/random")
    Call<UserObject> addUser(@Body UserObject object);
    @DELETE("api/random")
    Call<UserObject> deleteUser();
    @DELETE("api/random")
    Call<RoomObject> deleteRoomData();

}

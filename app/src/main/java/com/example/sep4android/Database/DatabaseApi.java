package com.example.sep4android.Database;

import com.example.sep4android.Objects.MeasurementsObject;
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
    // TODO: 10/05/2022  Change to roomId, and latest is stupid
    @GET("latest/{userId}")
    Call<List<MeasurementsObject>> getMeasurements(@Path("userId") String object);

    @GET("api/random")
    Call<UserObject> getUser();
    @POST("api/random")
    Call<UserObject> addUser(@Body UserObject object);
    @DELETE("api/random")
    Call<UserObject> deleteUser();
    @DELETE("api/random")
    Call<Room> deleteRoomData();

}

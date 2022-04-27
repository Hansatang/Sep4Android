package com.example.sep4android;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface DatabaseApi {
    @GET("api/random")
    Call<UserObject> getUser();
    @POST("api/random")
    Call<UserObject> addUser(@Body UserObject object);
    @DELETE("api/random")
    Call<UserObject> deleteUser();
    @DELETE("api/random")
    Call<RoomObject> deleteRoomData();

}

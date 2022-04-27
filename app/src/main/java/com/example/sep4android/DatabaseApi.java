package com.example.sep4android;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface DatabaseApi {
    @GET("rooms/")
    Call<List<RoomObject>> getRoom();
    @POST("room/registration/{RoomId}/")
    Call<Integer> addRoom(@Path("RoomId") int name);

}

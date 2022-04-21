package com.example.sep4android;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DatabaseApi {
    @GET("api/random")
    Call<UserObject> getUser();
}

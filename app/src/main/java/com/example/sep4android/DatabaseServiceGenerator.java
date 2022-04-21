package com.example.sep4android;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseServiceGenerator {
    private static DatabaseApi databaseApi;

    public static DatabaseApi getDatabaseApi() {
        if (databaseApi == null) {
            databaseApi = new Retrofit.Builder()
                    .baseUrl("https://animechan.vercel.app/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(DatabaseApi.class);
        }
        return databaseApi;
    }
}

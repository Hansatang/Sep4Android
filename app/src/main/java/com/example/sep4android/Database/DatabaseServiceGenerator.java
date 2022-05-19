package com.example.sep4android.Database;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DatabaseServiceGenerator {
  private static DatabaseApi databaseApi;

  public static DatabaseApi getDatabaseApi() {
    if (databaseApi == null) {
      databaseApi = new Retrofit.Builder()
          .baseUrl("http://air4you-env-1.eba-cpf6zx99.eu-north-1.elasticbeanstalk.com/")
          .addConverterFactory(GsonConverterFactory.create()).build()
          .create(DatabaseApi.class);
    }
    return databaseApi;
  }
}

package com.example.sep4android;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.internal.EverythingIsNonNull;


public class UserRepository {
    private static UserRepository instance;
    private final MutableLiveData<UserObject> user;


    private UserRepository(Application application) {
        user = new MutableLiveData<>();
    }

    public static synchronized UserRepository getInstance(Application application) {
        if (instance == null)
            instance = new UserRepository(application);
        return instance;
    }

    public LiveData<UserObject> getUser() {
        return user;
    }

    public void lookForUser() {
        DatabaseApi databaseApi = DatabaseServiceGenerator.getDatabaseApi();
        Call<UserObject> call = databaseApi.getUser();
        System.out.println("Call");
        call.enqueue(new Callback<UserObject>() {
                         @EverythingIsNonNull
                         @Override
                         public void onResponse(Call<UserObject> call, Response<UserObject> response) {
                             if (response.isSuccessful()) {
                                 UserObject rs = response.body();
                                 System.out.println("gtr " + rs.getUser());
                                 user.setValue(rs);
                             }
                         }

                         @EverythingIsNonNull
                         @Override
                         public void onFailure(Call<UserObject> call, Throwable t) {
                             System.out.println(t);
                             System.out.println(t.getMessage());
                             Log.i("Retrofit", "Something went wrong :(");
                         }
                     }
        );
    }
}

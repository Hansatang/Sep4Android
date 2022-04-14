package com.example.sep4android;

import android.app.Application;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;


public class UserRepository {
    private static UserRepository instance;
    private final MutableLiveData<String> User;


    private UserRepository(Application application) {
        User = new MutableLiveData<>();
    }

    public static synchronized UserRepository getInstance(Application application) {
        if (instance == null)
            instance = new UserRepository(application);
        return instance;
    }

    public LiveData<String> getUser(String str) {
        User.setValue(str);
        return User;
    }

    public void  mockUp() {
        User.setValue("Hej");
    }
}

package com.example.sep4android;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    public UserViewModel(Application app) {
        super(app);
        repository = UserRepository.getInstance(app);
    }

    public LiveData<UserObject> getUser(){
        return  repository.getUser();
    }

    public void changePassword(String newPassword)
    {
        getUser().getValue().setAnime(newPassword);
    }

    /*public void deleteRoomData()
    {
        repository.deleteRoomData();
    }
     */

    public void deleteAccount()
    {
        repository.deleteAccount();
    }
}

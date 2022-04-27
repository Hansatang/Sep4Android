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

    public LiveData<List<RoomObject>> getUser(){
        return  repository.getUser();
    }

    public void getUserFromRepo(){ ;
        repository.lookForUser();
    }

    public void addRoomToDatabase(int roomId)
    {
        repository.addRoomToDatabase(roomId);
    }
}

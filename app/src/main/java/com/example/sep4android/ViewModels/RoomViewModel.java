package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.RoomRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private RoomRepository repository;

    public RoomViewModel(Application app) {
        super(app);
        repository = RoomRepository.getInstance(app);
    }

    public LiveData<List<RoomObject>> getRooms(){
        return  repository.getRooms();
    }

    public void getRoomsFromRepo(){ ;
        repository.getDatabaseRooms();
    }


    public void addRoomToDatabase(String roomId,String userUID)
    {
        repository.addRoomToDatabase(Integer.parseInt(roomId), userUID);
    }
}


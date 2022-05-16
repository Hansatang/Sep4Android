package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.Room;
import com.example.sep4android.Repositories.RoomRepository;

import java.util.List;

public class RoomViewModel extends AndroidViewModel {
    private RoomRepository repository;

    public RoomViewModel(Application app) {
        super(app);
        repository = RoomRepository.getInstance(app);
    }

    public LiveData<List<Room>> getRooms() {
        return repository.getRooms();
    }

    public void getRoomsFromRepo(String uid) {
        repository.getDatabaseRooms(uid);
    }

    public void addRoomToDatabase(String roomId, String name, String userUID) {
        repository.addRoomToDatabase(roomId, name, userUID);
    }

    public void deleteToken(String userUID) {
        repository.deleteToken(userUID);
    }
}


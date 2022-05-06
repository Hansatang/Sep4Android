package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.RoomObject;
import com.example.sep4android.Repositories.RoomRepository;

import java.util.List;

public class MeasurementsViewModel extends AndroidViewModel {
    private RoomRepository repository;

    public MeasurementsViewModel(Application app) {
        super(app);
        repository = RoomRepository.getInstance(app);
    }

    public LiveData<List<RoomObject>> getMeasurements() {
        return repository.getRooms();
    }

    public void getMeasurementsFromRepo() {
        repository.getDatabaseRooms();
    }
}
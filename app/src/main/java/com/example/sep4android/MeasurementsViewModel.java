package com.example.sep4android;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import java.util.List;

public class MeasurementsViewModel extends AndroidViewModel {
    private UserRepository repository;

    public MeasurementsViewModel(Application app) {
        super(app);
        repository = UserRepository.getInstance(app);
    }

    public LiveData<List<RoomObject>> getMeasurements() {
        return repository.getUser();
    }

    public void getMeasurementsFromRepo() {
        repository.lookForUser();
    }
}
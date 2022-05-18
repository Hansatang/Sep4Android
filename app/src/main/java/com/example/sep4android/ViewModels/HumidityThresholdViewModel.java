package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Repositories.HumidityThresholdsRepository;

import java.util.List;


public class HumidityThresholdViewModel extends AndroidViewModel {
    private HumidityThresholdsRepository repository;

    public HumidityThresholdViewModel(Application application) {
        super(application);
        repository = HumidityThresholdsRepository.getInstance(application);
    }

    public LiveData<List<HumidityThresholdObject>> getThresholds() {
        return repository.getHumidityThresholds();
    }

    public void getRoomFromRepo(String uid) {
        repository.getDBSThresholds(uid);
    }

    public void addThresholdToDatabase(String thresholdHumidityId, String roomId){
        repository.addThresholdToDBS(thresholdHumidityId, roomId);
    }

}

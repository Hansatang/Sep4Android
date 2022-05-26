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

    public void getThresholdFromRepo(String roomId) {
        repository.getHumidityThresholds(roomId);
    }

    public void getAllThresholdFromRepo()
    {
        repository.getAllHumidityThresholds();
    }

    public void addThresholdToDatabase(String roomId, String startTime, String endTime, double maxValue, double minValue){
        repository.addHumidityThreshold(roomId, startTime, endTime, maxValue, minValue);
    }

    public void deleteThreshold(int thresholdId){
        repository.deleteHumidityThreshold(thresholdId);
    }

}

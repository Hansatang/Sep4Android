package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.TemperatureThresholdObject;
import com.example.sep4android.Repositories.TemperatureThresholdRepositories;

import java.util.List;

public class TemperatureThresholdViewModel extends AndroidViewModel {
    private TemperatureThresholdRepositories repository;


    public TemperatureThresholdViewModel(@NonNull Application application) {
        super(application);
        repository = TemperatureThresholdRepositories.getInstance(application);
    }

    public LiveData<List<TemperatureThresholdObject>> getThresholds(){
        return repository.getTemperatureThresholds();
    }

    public void getTemperatureThresholds(String roomId){
        repository.getTemperatureThresholds(roomId);
    }

    public void getAllTemperatureThresholds() {
        repository.getAllTemperatureThresholds();
    }

    public void addTemperatureThreshold(String id, String roomId, String startTime, String endTime, double maxValue, double minValue){
        repository.addTemperatureThreshold(id, roomId, startTime, endTime, maxValue, minValue);
    }

    public void deleteTemperatureThreshold(String id){
        repository.deleteTemperatureThreshold(id);
    }


    public void deleteAllTemperatureThresholds(){
        repository.deleteAllTemperatureThreshold();
    }

    public void getThresholdFromRepo(String roomId) {
        repository.getTemperatureThresholds(roomId);
    }
}

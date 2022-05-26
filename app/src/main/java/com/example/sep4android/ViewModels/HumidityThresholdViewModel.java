package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Repositories.HumidityThresholdsRepository;

import java.util.List;


public class HumidityThresholdViewModel extends AndroidViewModel {
    private final HumidityThresholdsRepository humidityThresholdsRepository;

    public HumidityThresholdViewModel(Application application) {
        super(application);
        humidityThresholdsRepository = HumidityThresholdsRepository.getInstance(application);
    }

    public LiveData<List<HumidityThresholdObject>> getThresholds() {
        return humidityThresholdsRepository.getHumidityThresholds();
    }

    public void getThresholdFromRepo(String roomId) {
        humidityThresholdsRepository.getHumidityThresholds(roomId);
    }

    public void getAllThresholdFromRepo()
    {
        humidityThresholdsRepository.getAllHumidityThresholds();
    }

    public void addThresholdToDatabase(String roomId, String startTime, String endTime, double maxValue, double minValue){
        humidityThresholdsRepository.addHumidityThreshold(roomId, startTime, endTime, maxValue, minValue);
    }

    public void deleteThreshold(int thresholdId){
        humidityThresholdsRepository.deleteHumidityThreshold(thresholdId);
    }

    public LiveData<String> getStatus(){
        return humidityThresholdsRepository.getStatus();
    }

    public void setResult() {
        humidityThresholdsRepository.setResult();
    }

}

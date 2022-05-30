package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Repositories.HumidityThresholdsRepository;

import java.util.List;


public class HumidityThresholdViewModel extends AndroidViewModel {
    private final HumidityThresholdsRepository humidityThresholdsRepository;
    private final MediatorLiveData<List<HumidityThresholdObject>> humidityThresholds;
    private final MediatorLiveData<String> status;

    public HumidityThresholdViewModel(Application application) {
        super(application);
        humidityThresholdsRepository = HumidityThresholdsRepository.getInstance();
        humidityThresholds = new MediatorLiveData<>();
        status = new MediatorLiveData<>();
    }

    public LiveData<List<HumidityThresholdObject>> getThresholds() {
        return humidityThresholds;
    }

    public LiveData<String> getStatus(){
        return status;
    }

    public void getThresholdFromRepo(String roomId) {
        humidityThresholds.addSource(humidityThresholdsRepository.getHumidityThresholds(roomId),humidityThresholds::setValue);
    }

    public void addThresholdToDatabase(String roomId, String startTime, String endTime, double maxValue, double minValue){
        status.addSource( humidityThresholdsRepository.addHumidityThreshold(roomId, startTime, endTime, maxValue, minValue), status::setValue);
    }

    public void deleteThreshold(int thresholdId){
        status.addSource( humidityThresholdsRepository.deleteHumidityThreshold(thresholdId), status::setValue);
    }

    public void setResult() {
        status.addSource( humidityThresholdsRepository.setResult(), status::setValue);
    }

}

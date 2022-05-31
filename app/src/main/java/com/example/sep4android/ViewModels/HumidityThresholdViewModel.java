package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;
import com.example.sep4android.Repositories.HumidityThresholdsRepository;

import java.util.List;

/**
 * View Model class for humidity threshold
 */
public class HumidityThresholdViewModel extends AndroidViewModel {
    private final HumidityThresholdsRepository humidityThresholdsRepository;
    private final MediatorLiveData<List<HumidityThresholdObject>> humidityThresholdsLiveData;
    private final MediatorLiveData<String> statusLiveData;

    public HumidityThresholdViewModel(Application application) {
        super(application);
        humidityThresholdsRepository = HumidityThresholdsRepository.getInstance();
        humidityThresholdsLiveData = new MediatorLiveData<>();
        statusLiveData = new MediatorLiveData<>();
    }

    public LiveData<List<HumidityThresholdObject>> getThresholdsLiveData() {
        return humidityThresholdsLiveData;
    }

    public LiveData<String> getStatusLiveData(){
        return statusLiveData;
    }

    public void getHumidityThresholds(String roomId) {
        humidityThresholdsLiveData.addSource(humidityThresholdsRepository.getHumidityThresholds(roomId), humidityThresholdsLiveData::setValue);
    }

    public void addHumidityThreshold(String roomId, String startTime, String endTime, double maxValue, double minValue){
        statusLiveData.addSource( humidityThresholdsRepository.addHumidityThreshold(roomId, startTime, endTime, maxValue, minValue), statusLiveData::setValue);
    }

    public void deleteHumidityThreshold(int thresholdId){
        statusLiveData.addSource( humidityThresholdsRepository.deleteHumidityThreshold(thresholdId), statusLiveData::setValue);
    }

    public void setResult() {
        statusLiveData.addSource( humidityThresholdsRepository.setResult(), statusLiveData::setValue);
    }

}

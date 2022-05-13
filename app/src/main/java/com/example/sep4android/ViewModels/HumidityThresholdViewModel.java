package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.HumidityThresholdObject;

//TODO
public class HumidityThresholdViewModel extends AndroidViewModel {

    public HumidityThresholdViewModel(@NonNull Application application) {
        super(application);
    }

    public LiveData<HumidityThresholdObject> getThresholds() {
        return null;
    }
}

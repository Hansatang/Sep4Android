package com.example.sep4android;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class UserViewModel extends AndroidViewModel {
    private MutableLiveData<String> User;
    private UserRepository repository;


    public UserViewModel(Application app) {
        super(app);
        User = new MutableLiveData<>();
        repository = UserRepository.getInstance(app);
    }

    public LiveData<String> getUser(String str){
        return  repository.getUser(str);
    }

    public void getUserFromRepo(String user){ ;
        User.setValue(user);
    }


}

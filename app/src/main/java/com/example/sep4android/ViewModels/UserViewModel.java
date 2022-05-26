package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.UserObject;
import com.example.sep4android.Repositories.UserRepository;

public class UserViewModel extends AndroidViewModel {
    private final UserRepository userRepository;

    public UserViewModel(Application app) {
        super(app);
        userRepository = UserRepository.getInstance(app);
    }

    public LiveData<UserObject> getUser() {
        return userRepository.getUser();
    }

    public void changePassword(String newPassword) {
        getUser().getValue().setAnime(newPassword);
    }

    /*public void deleteRoomData()
    {
        repository.deleteRoomData();
    }
     */

    public void deleteAccount() {
        userRepository.deleteAccount();
    }
}

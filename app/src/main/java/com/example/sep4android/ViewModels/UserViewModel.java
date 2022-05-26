package com.example.sep4android.ViewModels;

import static android.content.ContentValues.TAG;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.sep4android.Objects.UserObject;
import com.example.sep4android.Repositories.UserRepository;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UserViewModel extends AndroidViewModel {
    private UserRepository repository;

    public UserViewModel(Application app) {
        super(app);
        repository = UserRepository.getInstance(app);
    }

    public LiveData<UserObject> getUser() {
        return repository.getUser();
    }

    public void changePassword(String newPassword) {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        user.updatePassword(newPassword)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "User password updated.");
                        }
                    }
                });
        //getUser().getValue().setAnime(newPassword);
    }

    /*public void deleteRoomData()
    {
        repository.deleteRoomData();
    }
     */

    public void deleteAccount() {
        repository.deleteAccount();
        /*
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        user.delete()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            repository.deleteAccount();
                            Log.d(TAG, "User account deleted.");
                        }
                    }
                });
         */
    }
}

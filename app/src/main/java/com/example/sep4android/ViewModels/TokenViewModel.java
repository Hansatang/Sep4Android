package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.Repositories.TokenRepository;

/**
 * View Model class for tokens
 */
public class TokenViewModel extends AndroidViewModel {
  private final TokenRepository tokenRepository;
  private final MediatorLiveData<Integer> deletionResultLiveData;

  public TokenViewModel(@NonNull Application application) {
    super(application);
    tokenRepository = TokenRepository.getInstance();
    deletionResultLiveData = new MediatorLiveData<>();
  }

  public LiveData<Integer> getDeletionResultLiveData(){
    return deletionResultLiveData;
  }

  public void deleteToken(String userUID) {
    deletionResultLiveData.addSource( tokenRepository.deleteToken(userUID), deletionResultLiveData::setValue);
  }

  public void setResult() {
    deletionResultLiveData.addSource(tokenRepository.setResult(),  deletionResultLiveData::setValue);
  }
}

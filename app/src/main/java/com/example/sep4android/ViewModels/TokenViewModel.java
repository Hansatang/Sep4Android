package com.example.sep4android.ViewModels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MediatorLiveData;

import com.example.sep4android.Repositories.TokenRepository;

public class TokenViewModel extends AndroidViewModel {
  private final TokenRepository tokenRepository;
  private final MediatorLiveData<Integer> deletionResult;

  public TokenViewModel(@NonNull Application application) {
    super(application);
    tokenRepository = TokenRepository.getInstance();
    deletionResult = new MediatorLiveData<>();
  }

  public LiveData<Integer> getDeletionResult(){
    return deletionResult;
  }

  public void deleteToken(String userUID) {
    deletionResult.addSource( tokenRepository.deleteToken(userUID), deletionResult::setValue);
  }

  public void setResult() {
    deletionResult.addSource(tokenRepository.setResult(),  deletionResult::setValue);
  }
}

package com.example.sep4android.RepositoryIntefaces;

public interface TokenRepositoryInterface {
  void setNewToken(String uid, String token) ;

  void deleteToken(String userUID) ;

}

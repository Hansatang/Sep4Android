package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserToken {
  @SerializedName("uID")
  @Expose
  private String uId;

  @SerializedName("token")
  @Expose
  private String token;

  public UserToken(String uId, String token) {
    this.uId = uId;
    this.token = token;
  }

  public String getId() {
    return uId;
  }

  public void setId(String uId) {
    this.uId = uId;
  }

  public String getToken() {
    return token;
  }

  public void setToken(String token) {
    this.token = token;
  }
}

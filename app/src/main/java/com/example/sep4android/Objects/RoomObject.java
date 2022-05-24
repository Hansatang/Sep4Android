package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.util.List;

public class RoomObject {

  @SerializedName("roomId")
  @Expose
  private String RoomId;

  @SerializedName("name")
  @Expose
  private String name;

  @SerializedName("userId")
  @Expose
  private String UserId;

  @SerializedName("registrationDate")
  @Expose
  private String RegistrationDate;

  @SerializedName("measurements")
  @Expose
  private List<MeasurementsObject> Measurements;


  public String getRoomId() {
    return RoomId;
  }

  public void setRoomId(String roomId) {
    RoomId = roomId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserId() {
    return UserId;
  }

  public void setUserId(String userId) {
    UserId = userId;
  }

  public String getRegistrationDate() {
    return RegistrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    RegistrationDate = registrationDate;
  }

  public RoomObject(String roomId, String name, String userId, String registrationDate, List<MeasurementsObject> measurements) {
    RoomId = roomId;
    this.name = name;
    UserId = userId;
    RegistrationDate = registrationDate;
    Measurements = measurements;
  }

  public List<MeasurementsObject> getMeasurements() {
    return Measurements;
  }

  public void setMeasurements(List<MeasurementsObject> measurements) {
    Measurements = measurements;
  }

}

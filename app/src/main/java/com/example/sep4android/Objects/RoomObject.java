package com.example.sep4android.Objects;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

@Entity(tableName = "archivedRooms")
public class RoomObject {

  @NonNull
  @PrimaryKey
  @ColumnInfo(name = "roomId")
  @SerializedName("roomId")
  @Expose
  private String roomId;

  @ColumnInfo(name = "name")
  @SerializedName("name")
  @Expose
  private String name;

  @ColumnInfo(name = "userId")
  @SerializedName("userId")
  @Expose
  private String userId;

  @ColumnInfo(name = "registrationDate")
  @SerializedName("registrationDate")
  @Expose
  private String registrationDate;


  @SerializedName("measurements")
  @Expose
  @Ignore
  private List<MeasurementsObject> measurements;

  public RoomObject(String roomId, String name, String userId, String registrationDate, List<MeasurementsObject> measurements) {
    this.roomId = roomId;
    this.name = name;
    this.userId = userId;
    this.registrationDate = registrationDate;
    this.measurements = measurements;
  }

  public RoomObject(String roomId, String name, String userId, String registrationDate) {
    this.roomId = roomId;
    this.name = name;
    this.userId = userId;
    this.registrationDate = registrationDate;
  }


  public String getRoomId() {
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getUserId() {
    return userId;
  }

  public void setUserId(String userId) {
    this.userId = userId;
  }

  public String getRegistrationDate() {
    return registrationDate;
  }

  public void setRegistrationDate(String registrationDate) {
    this.registrationDate = registrationDate;
  }


  public List<MeasurementsObject> getMeasurements() {
    return measurements;
  }

  public void setMeasurements(List<MeasurementsObject> measurements) {
    this.measurements = measurements;
  }

}

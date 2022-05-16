package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.type.DateTime;

public class MeasurementObject {
  @SerializedName("timestamp")
  @Expose
  private DateTime Timestamp;

  @SerializedName("roomId")
  @Expose
  private int RoomId;

  @SerializedName("temp")
  @Expose
  private double Temperature;

  @SerializedName("humidity")
  @Expose
  private double Humidity;

  @SerializedName("co2")
  @Expose
  private double co2;

  public MeasurementObject(DateTime timestamp, int roomId, double temperature, double humidity, double co2) {
    Timestamp = timestamp;
    RoomId = roomId;
    Temperature = temperature;
    Humidity = humidity;
    this.co2 = co2;
  }

  public DateTime getTimestamp() {
    return Timestamp;
  }

  public void setTimestamp(DateTime timestamp) {
    Timestamp = timestamp;
  }

  public int getRoomId() {
    return RoomId;
  }

  public void setRoomId(int roomId) {
    RoomId = roomId;
  }

  public double getTemperature() {
    return Temperature;
  }

  public void setTemperature(double temperature) {
    Temperature = temperature;
  }

  public double getHumidity() {
    return Humidity;
  }

  public void setHumidity(double humidity) {
    Humidity = humidity;
  }

  public double getCo2() {
    return co2;
  }

  public void setCo2(double co2) {
    this.co2 = co2;
  }
}

package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class MeasurementsObject {

  @SerializedName("date")
  @Expose
  private String date;

  @SerializedName("roomId")
  @Expose
  private String RoomId;

  @SerializedName("temperature")
  @Expose
  private double temperature;

  @SerializedName("humidity")
  @Expose
  private double humidity;

  @SerializedName("co2")
  @Expose
  private double co2;

  @SerializedName("id")
  @Expose
  private int id;


  public MeasurementsObject(String date, String roomId, double temperature, double humidity, double co2, int id) {
    this.date = date;
    RoomId = roomId;
    this.temperature = temperature;
    this.humidity = humidity;
    this.co2 = co2;
    this.id = id;
  }

  public String getDate() {
    return date;
  }

  public void setDate(String date) {
    this.date = date;
  }

  public String getRoomId() {
    return RoomId;
  }

  public void setRoomId(String roomId) {
    RoomId = roomId;
  }

  public double getTemperature() {
    return temperature;
  }

  public void setTemperature(double temperature) {
    this.temperature = temperature;
  }

  public double getHumidity() {
    return humidity;
  }

  public void setHumidity(double humidity) {
    this.humidity = humidity;
  }

  public double getCo2() {
    return co2;
  }

  public void setCo2(double co2) {
    this.co2 = co2;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }
}

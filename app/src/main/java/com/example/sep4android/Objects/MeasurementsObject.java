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

  @SerializedName("temperatureExceeded")
  @Expose
  private Boolean temperatureExceeded;

  @SerializedName("humidity")
  @Expose
  private double humidity;

  @SerializedName("humidityExceeded")
  @Expose
  private Boolean humidityExceeded;

  @SerializedName("co2")
  @Expose
  private double co2;

  @SerializedName("co2Exceeded")
  @Expose
  private Boolean co2Exceeded;


  @SerializedName("id")
  @Expose
  private int id;




  public MeasurementsObject(String date, String roomId, double temperature,
                            Boolean temperatureExceeded, double humidity,
                            Boolean humidityExceeded, double co2,
                            Boolean co2Exceeded, int id) {
    this.date = date;
    this.RoomId = roomId;
    this.temperature = temperature;
    this.temperatureExceeded = temperatureExceeded;
    this.humidity = humidity;
    this.humidityExceeded = humidityExceeded;
    this.co2 = co2;
    this.co2Exceeded = co2Exceeded;
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

  public Boolean isTemperatureExceeded() {
    return temperatureExceeded;
  }

  public void setTemperatureExceeded(Boolean temperatureExceeded) {
    this.temperatureExceeded = temperatureExceeded;
  }

  public Boolean isHumidityExceeded() {
    return humidityExceeded;
  }

  public void setHumidityExceeded(Boolean humidityExceeded) {
    this.humidityExceeded = humidityExceeded;
  }

  public Boolean isCo2Exceeded() {
    return co2Exceeded;
  }

  public void setCo2Exceeded(Boolean co2Exceeded) {
    this.co2Exceeded = co2Exceeded;
  }
}

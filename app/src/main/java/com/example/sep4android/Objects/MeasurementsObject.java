package com.example.sep4android.Objects;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "archivedMeasurements")
public class MeasurementsObject {

  @ColumnInfo(name = "date")
  @SerializedName("date")
  @Expose
  private String date;

  @ColumnInfo(name = "roomId")
  @SerializedName("roomId")
  @Expose
  private String roomId;

  @ColumnInfo(name = "temperature")
  @SerializedName("temperature")
  @Expose
  private double temperature;

  @ColumnInfo(name = "temperatureExceeded")
  @SerializedName("temperatureExceeded")
  @Expose
  private Boolean temperatureExceeded;

  @ColumnInfo(name = "humidity")
  @SerializedName("humidity")
  @Expose
  private double humidity;

  @ColumnInfo(name = "humidityExceeded")
  @SerializedName("humidityExceeded")
  @Expose
  private Boolean humidityExceeded;

  @ColumnInfo(name = "co2")
  @SerializedName("co2")
  @Expose
  private double co2;

  @ColumnInfo(name = "co2Exceeded")
  @SerializedName("co2Exceeded")
  @Expose
  private Boolean co2Exceeded;

  @PrimaryKey
  @ColumnInfo(name = "id")
  @SerializedName("id")
  @Expose
  private int id;


  public MeasurementsObject(String date, String roomId, double temperature,
                            Boolean temperatureExceeded, double humidity,
                            Boolean humidityExceeded, double co2,
                            Boolean co2Exceeded, int id) {
    this.date = date;
    this.roomId = roomId;
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
    return roomId;
  }

  public void setRoomId(String roomId) {
    this.roomId = roomId;
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

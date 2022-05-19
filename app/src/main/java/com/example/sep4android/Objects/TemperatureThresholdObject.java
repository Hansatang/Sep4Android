package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class TemperatureThresholdObject {

    @SerializedName("thresholdTemperatureId")
    @Expose
    private String thresholdHumidityId;

    @SerializedName("roomId")
    @Expose
    private String roomId;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    @SerializedName("maxValue")
    @Expose
    private double maxValue;

    @SerializedName("minValue")
    @Expose
    private double minValue;

    public TemperatureThresholdObject(String thresholdHumidityId, String roomId, String startTime, String endTime, double maxValue, double minValue) {
        this.thresholdHumidityId = thresholdHumidityId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }


    public String getThresholdHumidityId() {
        return thresholdHumidityId;
    }

    public void setThresholdHumidityId(String thresholdHumidityId) {
        this.thresholdHumidityId = thresholdHumidityId;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public double getMaxValue() {
        return maxValue;
    }

    public void setMaxValue(double maxValue) {
        this.maxValue = maxValue;
    }

    public double getMinValue() {
        return minValue;
    }

    public void setMinValue(double minValue) {
        this.minValue = minValue;
    }
}

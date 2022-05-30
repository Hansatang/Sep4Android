package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Represents the humidityThresholdObject
 */
public class HumidityThresholdObject {

    @SerializedName("id")
    @Expose
    private int thresholdHumidityId;

    @SerializedName("roomId")
    @Expose
    private String roomId;

    @SerializedName("startTime")
    @Expose
    private String startTime;

    @SerializedName("endTime")
    @Expose
    private String endTime;

    @SerializedName("max")
    @Expose
    private double maxValue;

    @SerializedName("min")
    @Expose
    private double minValue;

    public HumidityThresholdObject(int thresholdHumidityId, String roomId, String startTime,
                                   String endTime, double maxValue, double minValue) {
        this.thresholdHumidityId = thresholdHumidityId;
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public HumidityThresholdObject(String roomId, String startTime,
                                   String endTime, double maxValue, double minValue) {
        this.roomId = roomId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.maxValue = maxValue;
        this.minValue = minValue;
    }

    public int getThresholdHumidityId() {
        return thresholdHumidityId;
    }

    public void setThresholdHumidityId(int thresholdHumidityId) {
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

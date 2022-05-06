package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;

public class RoomObject {

    @SerializedName("roomId")
    @Expose
    private int RoomId;

    @SerializedName("userId")
    @Expose
    private String UserId;

    @SerializedName("registrationDate")
    @Expose
    private String RegistrationDate;

    public RoomObject(int roomId, String userId, String registrationDate) {
        RoomId = roomId;
        UserId = userId;
        RegistrationDate = registrationDate;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
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
}

package com.example.sep4android;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomObject {

    @SerializedName("roomId")
    @Expose
    private int RoomId;


    public RoomObject(int roomId) {
        RoomId = roomId;
    }

    public int getRoomId() {
        return RoomId;
    }

    public void setRoomId(int roomId) {
        RoomId = roomId;
    }
}

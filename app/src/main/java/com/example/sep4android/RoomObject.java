package com.example.sep4android;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RoomObject {

    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("id")
    @Expose
    private String id;

    public RoomObject(String name, String id)
    {
        this.id = id;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}

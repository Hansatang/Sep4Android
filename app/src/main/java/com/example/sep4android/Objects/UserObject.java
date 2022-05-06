package com.example.sep4android.Objects;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class UserObject {

    @SerializedName("anime")
    @Expose
    private String anime;

    @SerializedName("character")
    @Expose
    private String character;

    @SerializedName("quote")
    @Expose
    private String quote;

    public UserObject(String name, String character, String quote) {
        this.anime = name;
        this.character = character;
        this.quote = quote;
    }

    public String getUser() {
        return anime;
    }

    public String getCharacter() {
        return character;
    }

    public String getQuote() {
        return quote;
    }

    public void setAnime(String anime) {
        this.anime = anime;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public void setQuote(String quote) {
        this.quote = quote;
    }
}

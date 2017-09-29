package com.example.android.mysongapp;

/**
 * Created by JOHN on 26/9/2017.
 */

public class ListItem {

    private String artist;
    private String songname;
    private String songURL;

    public ListItem(String head, String description, String songURL) {
        this.artist = head;
        this.songname = description;
        this.songURL = songURL;
    }

    public String getArtist() {
        return artist;
    }

    public String getSongname() {
        return songname;
    }

    public String getSongURL() {
        return songURL;
    }
}

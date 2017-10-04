package com.example.android.mysongapp;

/**
 * Created by JOHN on 26/9/2017.
 */

public class ListItem {

    private String artist;
    private String songname;
    private String songURL;
    private String albumname;

    public ListItem(String artist, String albumname, String songname, String songURL) {
        this.artist = artist;
        this.albumname = albumname;
        this.songname = songname;
        this.songURL = songURL;
    }

    public String getAlbumname() { return albumname; }

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

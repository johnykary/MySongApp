package com.example.android.mysongapp;

/**
 * Created by JOHN on 29/9/2017.
 */

public class ServerURL {
    private static String URL = "http://192.168.1.7:7777";

    public ServerURL(String URL ) {
        this.URL = URL;
    }

    public static String getURL() {
        return URL;
    }
}

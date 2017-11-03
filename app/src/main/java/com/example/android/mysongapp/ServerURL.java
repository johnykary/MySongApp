package com.example.android.mysongapp;

/**
 * Created by JOHN on 29/9/2017.
 */

public class ServerURL {
    private static String URL = "http://c5651aba.ngrok.io";

    public ServerURL(String URL ) {
        this.URL = URL;
    }

    public static String getURL() {
        return URL;
    }
}

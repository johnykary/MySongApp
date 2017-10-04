package com.example.android.mysongapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SongPlay extends AppCompatActivity {
    private TextView artist_Text_View;
    private TextView song_Text_View;
    private TextView album_Text_View;
    private Button playButton;
    private Button stopButton;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private Context context;

    private Intent myAdapterIntent;
    private String URL = ServerURL.getURL() + "/play/";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_song_play);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        context = getApplicationContext();



        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        artist_Text_View = (TextView) findViewById(R.id.textViewArtist);
        album_Text_View = (TextView) findViewById(R.id.textViewAlbum);
        song_Text_View = (TextView) findViewById(R.id.textViewSong);


        myAdapterIntent = getIntent();
        String artistName = myAdapterIntent.getStringExtra("artistName");
        String albumName = myAdapterIntent.getStringExtra("albumName");
        String songName = myAdapterIntent.getStringExtra("songName");


        artist_Text_View.setText("Artist :" + " " + artistName);
        album_Text_View.setText("Album :" + "" + albumName);
        song_Text_View.setText("Song  :" + " " + songName);

        playButton = (Button) findViewById(R.id.buttonPlay);
        stopButton = (Button) findViewById(R.id.buttonStop);

        playButton.setOnClickListener(handlePlayButton());
        stopButton.setOnClickListener(handleStopButton());



    }

    private View.OnClickListener handleStopButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();

//                    mediaPlayer.release();
                    mediaPlayer = null;

                }

            }
        };
    }

    private View.OnClickListener handlePlayButton() {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if(mediaPlayer == null){
                    String songPath = myAdapterIntent.getStringExtra("songURL");

                    String URLtoPlay = URL + songPath.replaceAll(" ", "%20");

                    audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

                    mediaPlayer = new MediaPlayer();

                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                    try {
                        mediaPlayer.reset();
                        mediaPlayer.setDataSource(URLtoPlay);
                        mediaPlayer.prepare();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    mediaPlayer.start();


                }
                else{
                    return;
                }
            }
        };
    }

    @Override
    public void onBackPressed() {


        mediaPlayer.stop();
        mediaPlayer = null;

        finish();
        super.onBackPressed();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        finish();
    }
}

package com.example.android.mysongapp;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.exoplayer.ExoPlayer;
import com.google.android.exoplayer.MediaCodecAudioTrackRenderer;
import com.google.android.exoplayer.extractor.ExtractorSampleSource;
import com.google.android.exoplayer.upstream.Allocator;
import com.google.android.exoplayer.upstream.DataSource;
import com.google.android.exoplayer.upstream.DefaultAllocator;
import com.google.android.exoplayer.upstream.DefaultUriDataSource;
import com.google.android.exoplayer.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;

import wseemann.media.FFmpegMediaPlayer;

public class SongPlay extends AppCompatActivity {
    private TextView artist_Text_View;
    private TextView song_Text_View;
    private TextView album_Text_View;
    private Button playButton;
    private Button stopButton;
    private AudioManager audioManager;
    private MediaPlayer mediaPlayer;
    private Context context;

    private ExoPlayer exoPlayer;
    private static final int BUFFER_SEGMENT_SIZE = 64 * 1024;
    private static final int BUFFER_SEGMENT_COUNT = 256;




    private Intent myAdapterIntent;
    private String URL = ServerURL.getURL() + "/play/";
    private int mainHandler;







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
                

//                if(mediaPlayer == null){
//                    String songPath = myAdapterIntent.getStringExtra("songURL");
//
//                    String URLtoPlay = URL + songPath.replaceAll(" ", "%20");
//
//                    audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//                    mediaPlayer = new MediaPlayer();
//
//                    mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//                    try {
//                        mediaPlayer.reset();
//                        mediaPlayer.setDataSource(URLtoPlay);
//                        mediaPlayer.prepare();
//                    } catch (IOException e) {
//                        e.printStackTrace();
//                    }
//                    mediaPlayer.start();
//
//
//                }
//                else{
//                    return;
//                }
                String songPath = myAdapterIntent.getStringExtra("songURL");
                String URLtoPlay = URL + songPath.replaceAll(" ", "%20");
                Uri Urlfor = Uri.parse(URLtoPlay);
//
//                FFmpegMediaPlayer mp = new FFmpegMediaPlayer();
//                mp.setOnPreparedListener(new FFmpegMediaPlayer.OnPreparedListener() {
//
//                    @Override
//                    public void onPrepared(FFmpegMediaPlayer mp) {
//                        mp.start();
//                    }
//                });
//                mp.setOnErrorListener(new FFmpegMediaPlayer.OnErrorListener() {
//
//                    @Override
//                    public boolean onError(FFmpegMediaPlayer mp, int what, int extra) {
//                        mp.release();
//                        return false;
//                    }
//                });
//
//                try {
//                    mp.setDataSource(URLtoPlay);
//                    mp.prepareAsync();
//                } catch (IllegalArgumentException e) {
//                    e.printStackTrace();
//                } catch (SecurityException e) {
//                    e.printStackTrace();
//                } catch (IllegalStateException e) {
//                    e.printStackTrace();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                exoPlayer = ExoPlayer.Factory.newInstance(1);



// Settings for exoPlayer
                Allocator allocator = new DefaultAllocator(BUFFER_SEGMENT_SIZE);
                String userAgent = Util.getUserAgent(context, "SongPlay");
                DataSource dataSource = new DefaultUriDataSource(context, null, userAgent);
                ExtractorSampleSource sampleSource = new ExtractorSampleSource(
                        Urlfor, dataSource, allocator, BUFFER_SEGMENT_SIZE * BUFFER_SEGMENT_COUNT);
                MediaCodecAudioTrackRenderer audioRenderer = new MediaCodecAudioTrackRenderer(sampleSource);
// Prepare ExoPlayer
                exoPlayer.prepare(audioRenderer);
                exoPlayer.setPlayWhenReady(true);


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

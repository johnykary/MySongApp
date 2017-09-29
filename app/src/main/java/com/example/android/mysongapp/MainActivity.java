package com.example.android.mysongapp;

import android.app.ProgressDialog;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.EditText;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText artist_edit_text;
    private EditText song_edit_text;
    MediaPlayer mediaPlayer;
    private final String IP = ServerURL.getURL();

    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;
    private String jsonin;

    private ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecyclerViewData();



    }

    private void loadRecyclerViewData() {
        ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading...");
        progressDialog.show();
        ThreadedConnection fetchSongs = new ThreadedConnection("/", "GET");
        fetchSongs.execute();
        progressDialog.dismiss();


    }

    private class ThreadedConnection extends AsyncTask<Object, Object, Object> {

        private String request;
        private String requestMethod;

        public ThreadedConnection(String request, String requestMethod) {
            this.request = request;
            this.requestMethod = requestMethod;
        }

        @Override
        protected Object doInBackground(Object... objects) {
            HttpURLConnection httpConn = null;
            BufferedReader reader = null;
            InputStream inputStream;
            StringBuilder stringBuilder = new StringBuilder();
            try {
                URL url = new URL(IP + request);
                httpConn = (HttpURLConnection) url.openConnection();
                httpConn.setRequestMethod(requestMethod);
                httpConn.setConnectTimeout(7000);
//                httpConn.connect();

                int HttpResult = httpConn.getResponseCode();
                if(HttpResult == HttpURLConnection.HTTP_OK){
                    inputStream = httpConn.getInputStream();
                    if(inputStream == null){
                        return null;
                    }
                    BufferedReader buffer = new BufferedReader(new InputStreamReader(inputStream));
                    String line;

                    while ((line = buffer.readLine()) != null) {
                        stringBuilder.append(line).append('\n');
                    }
                    buffer.close();
                    jsonin = stringBuilder.toString();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpConn != null)
                httpConn.disconnect();

            return jsonin;

        }

        @Override
        protected void onPostExecute(Object o) {
            if (request == "/") {
                JSONObject jsonReceived;
                try {
                    jsonReceived = new JSONObject(jsonin);
                    JSONArray array = jsonReceived.getJSONArray("artists");

                    for (int i = 0; i < array.length(); i++) {
                        JSONObject n = array.getJSONObject(i);
                        ListItem item = new ListItem(
                                n.getString("artistname"),
                                n.getString("songname"),
                                n.getString("songpath")
                        );
                        listItems.add(item);
                    }

                    adapter = new MyAdapter(listItems, getApplicationContext());
                    recyclerView.setAdapter(adapter);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}

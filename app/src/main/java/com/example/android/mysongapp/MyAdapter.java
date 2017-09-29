package com.example.android.mysongapp;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

/**
 * Created by JOHN on 26/9/2017.
 */


public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {

    private List<ListItem> listItems;
    private Context context;

//    private String IP = "http://192.168.1.10:7777/song/QueenInnuendo.mp3";

    public MyAdapter(List<ListItem> listItems, Context context) {
        this.listItems = listItems;
        this.context = context;
    }
    View v;

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item, parent, false);
        return new ViewHolder(v);
    }

    public List<ListItem> getListItems() {
        return listItems;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final ListItem listItem = listItems.get(position);

        holder.textArtist.setText(listItem.getArtist());

        holder.textSongName.setText(listItem.getSongname());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(context, "Clicked" + listItem.getHead(), Toast.LENGTH_SHORT).show();
//                AudioManager audioManager;
//                audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
//
//                MediaPlayer mediaPlayer = new MediaPlayer();
//                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
//
//                try {
//                    mediaPlayer.setDataSource(IP);
//                    mediaPlayer.prepare();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//
//                mediaPlayer.start();

                Intent intent = new Intent(context ,SongPlay.class );

                intent.putExtra("artistName" , listItem.getArtist());
                intent.putExtra("songName", listItem.getSongname());
                intent.putExtra("songURL", listItem.getSongURL());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textArtist;
        public TextView textSongName;
        public LinearLayout linearLayout;
        private final Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textArtist = (TextView) itemView.findViewById(R.id.textArtist);
            textSongName = (TextView) itemView.findViewById(R.id.textSongName);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }

    }

}

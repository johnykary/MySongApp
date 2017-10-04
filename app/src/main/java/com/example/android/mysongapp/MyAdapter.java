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

        holder.textAlbumName.setText(listItem.getAlbumname());

        holder.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent = new Intent(context ,SongPlay.class );

                intent.putExtra("artistName" , listItem.getArtist());
                intent.putExtra("albumName", listItem.getAlbumname());
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
        public TextView textAlbumName;
        public LinearLayout linearLayout;
        private final Context context;
        public ViewHolder(View itemView) {
            super(itemView);
            context = itemView.getContext();
            textArtist = (TextView) itemView.findViewById(R.id.textArtist);
            textSongName = (TextView) itemView.findViewById(R.id.textSongName);
            textAlbumName = (TextView) itemView.findViewById(R.id.textAlbum);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.linearLayout);

        }

    }

}

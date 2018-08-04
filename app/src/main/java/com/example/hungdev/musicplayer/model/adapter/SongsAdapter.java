package com.example.hungdev.musicplayer.model.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.model.entity.Song;

import java.io.IOException;
import java.util.List;

/**
 * Created by hungdev on 24/07/2018.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder> {

    private List<Song> mSongs;

    public SongsAdapter(List<Song> songs) {
        this.mSongs = songs;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,
                parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mSongs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView mTextSongName, mTextSingerName;
        ImageView mImageItem;

        public MyViewHolder(final View itemView) {
            super(itemView);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextSingerName = itemView.findViewById(R.id.text_singer);
            mImageItem = itemView.findViewById(R.id.imageItem);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    MediaPlayer mediaPlayer = new MediaPlayer();
                    try {
                        mediaPlayer.setDataSource(mSongs.get(getAdapterPosition()).getmUri());
                        mediaPlayer.prepare();
                        mediaPlayer.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song mSong = mSongs.get(position);
        holder.mTextSongName.setText(mSong.getmSongName());
        holder.mTextSingerName.setText(mSong.getmSingerName());
       if(mSong.getmUriImage() == null){
           holder.mImageItem.setBackgroundResource(R.drawable.image_item_song);
       }else {
           holder.mImageItem.setImageBitmap(BitmapFactory.decodeByteArray(mSong.getmUriImage(),0,mSong.getmUriImage().length));
       }
    }
}

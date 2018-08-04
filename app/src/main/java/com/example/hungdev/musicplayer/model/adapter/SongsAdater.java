package com.example.hungdev.musicplayer.model.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.model.entity.Song;

import java.util.List;

/**
 * Created by hungdev on 24/07/2018.
 */

public class SongsAdater extends RecyclerView.Adapter<SongsAdater.MyViewHolder> {

    private List<Song> mSongs;

    public SongsAdater(List<Song> songs) {
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

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextSingerName = itemView.findViewById(R.id.text_singer);
            mImageItem = itemView.findViewById(R.id.imageItem);
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song mSong = mSongs.get(position);
        holder.mTextSongName.setText(mSong.getmSongName());
        holder.mTextSingerName.setText(mSong.getmSingerName());
        holder.mImageItem.setBackgroundResource(R.drawable.image_item_song);
    }
}

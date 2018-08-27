package com.example.hungdev.musicplayer.main.songs;

import android.graphics.BitmapFactory;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.data.Song;

import java.util.List;

/**
 * Created by hungdev on 24/07/2018.
 */

public class SongsAdapter extends RecyclerView.Adapter<SongsAdapter.MyViewHolder>{

    private SongItemListener mSongItemListener;
    private List<Song> mSongs;

    public SongsAdapter(List<Song> songs, SongItemListener songItemListener) {
        this.mSongs = songs;
        this.mSongItemListener = songItemListener;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,
                parent, false);
        return new MyViewHolder(itemView);
    }

    @Override
    public int getItemCount() {
        return mSongs == null ? 0 : mSongs.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mTextSongName, mTextSingerName;
        private ImageView mImageItem;

        public MyViewHolder(View itemView) {
            super(itemView);
            mTextSongName = itemView.findViewById(R.id.text_song_name);
            mTextSingerName = itemView.findViewById(R.id.text_singer);
            mImageItem = itemView.findViewById(R.id.imageItem);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            mSongItemListener.onSongClick(mSongs.get(getAdapterPosition()), getAdapterPosition());
        }
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        Song song = mSongs.get(position);
        holder.mTextSongName.setText(song.getSongName());
        holder.mTextSingerName.setText(song.getSingerName());
        if(song.getUriImage() != null){
            holder.mImageItem.setImageBitmap(BitmapFactory.decodeByteArray(song.getUriImage(),0,song.getUriImage().length));
        }
        else {
            holder.mImageItem.setImageResource(R.drawable.image_item_song);
        }
    }

    public interface SongItemListener {
        void onSongClick(Song clickedSong, int positon);
    }
}

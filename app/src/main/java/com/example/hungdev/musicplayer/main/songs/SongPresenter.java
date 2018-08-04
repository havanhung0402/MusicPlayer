package com.example.hungdev.musicplayer.main.songs;

import android.content.ContentResolver;
import android.content.Context;

import com.example.hungdev.musicplayer.data.Song;
import com.example.hungdev.musicplayer.data.source.SongDataSource;
import com.example.hungdev.musicplayer.data.source.local.SongLocalDataSource;

import java.util.List;

/**
 * Created by hungdev on 03/08/2018.
 */

public class SongPresenter implements SongContract.Presenter, SongLocalDataSource.LoadSongsCallback{

    private SongContract.View mSongsView;
    private SongLocalDataSource mSongLocalDataSource;

    public SongPresenter(Context context, SongContract.View mSongsView) {
        this.mSongsView = mSongsView;
        this.mSongLocalDataSource = new SongLocalDataSource(context);
    }

    @Override
    public void start() {
    }

    @Override
    public void loadSongs() {
        mSongLocalDataSource.getSongs(this);
    }

    @Override
    public void playMedia() {

    }

    @Override
    public void onSongLoaded(List<Song> songs) {
        mSongsView.showSongs(songs);
    }

    @Override
    public void onDataNotAvailable() {
        mSongsView.showEmpty();
    }
}

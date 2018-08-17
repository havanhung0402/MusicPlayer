package com.example.hungdev.musicplayer.player;

import android.content.Context;
import android.content.Intent;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.data.Song;
import com.example.hungdev.musicplayer.data.source.local.SongLocalDataSource;

import java.util.List;

/**
 * Created by hungdev on 07/08/2018.
 */

public class PlayerPresenter implements PlayerContract.Presenter {

    private Context mContext;
    private PlayerContract.View mView;
    private SongLocalDataSource mSongLocalDataSource;

    public PlayerPresenter(Context context, PlayerContract.View view) {
        mContext = context;
        mView = view;
    }

    @Override
    public void loadSongDetails(Song song, List<Song> songs) {
        mView.showSongView(song, songs);
    }
}

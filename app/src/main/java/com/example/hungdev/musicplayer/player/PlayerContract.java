package com.example.hungdev.musicplayer.player;

import android.content.Intent;

import com.example.hungdev.musicplayer.data.Song;

import java.util.List;

/**
 * Created by hungdev on 07/08/2018.
 */

public interface PlayerContract {
    interface View {
        void showSongView(Song song, List<Song> songs);
        void playService();
        void showUpdateView(Song song);
    }
    interface Presenter {
        void loadSongDetails(Song song, List<Song> songs);
    }
}

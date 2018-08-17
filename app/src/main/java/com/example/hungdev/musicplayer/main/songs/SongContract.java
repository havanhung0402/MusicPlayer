package com.example.hungdev.musicplayer.main.songs;

import com.example.hungdev.musicplayer.BasePresenter;
import com.example.hungdev.musicplayer.BaseView;
import com.example.hungdev.musicplayer.data.Song;

import java.util.List;

/**
 * Created by hungdev on 03/08/2018.
 */

public interface SongContract {

    interface View {
        void showSongs(List<Song> songs);
        void showEmpty();
        void showPlayer(Song song, int positon);
        void showMiniPlayer();
    }

    interface Presenter extends BasePresenter{
        void loadSongs();
        void openPlayer(Song song, int position);
    }
}

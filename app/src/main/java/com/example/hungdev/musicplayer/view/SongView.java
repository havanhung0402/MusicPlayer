package com.example.hungdev.musicplayer.view;

import com.example.hungdev.musicplayer.model.adapter.SongsAdapter;
import com.example.hungdev.musicplayer.model.entity.Song;

import java.util.List;

/**
 * Created by hungdev on 04/08/2018.
 */

public interface SongView {
    void displaySongs(SongsAdapter adapter);
    void play(List<Song> songs);
}

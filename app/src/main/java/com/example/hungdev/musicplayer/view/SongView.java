package com.example.hungdev.musicplayer.view;

import com.example.hungdev.musicplayer.model.entity.Song;

import java.util.List;

/**
 * Created by hungdev on 04/08/2018.
 */

public interface SongView {
    void displaySongs(List<Song> songs);
}

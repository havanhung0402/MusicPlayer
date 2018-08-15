package com.example.hungdev.musicplayer.data.source;

import android.database.Cursor;

import com.example.hungdev.musicplayer.data.Song;

import java.util.List;

/**
 * Created by hungdev on 06/08/2018.
 */

public interface SongDataSource {
     interface LoadSongsCallback {
          void onSongLoaded(List<Song> songs);
          void onDataNotAvailable();
     }
     void getSongs(LoadSongsCallback callback);
     void getSong(String path);
}

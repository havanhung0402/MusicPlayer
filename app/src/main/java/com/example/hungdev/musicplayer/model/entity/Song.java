package com.example.hungdev.musicplayer.model.entity;

/**
 * Created by hungdev on 24/07/2018.
 */

public class Song {
    private String mSongName;
    private String mSingerName;
    private String mUri;

    public Song() {
    }

    public Song(String mSongName, String mSingerName, String mUri) {
        this.mSongName = mSongName;
        this.mSingerName = mSingerName;
        this.mUri = mUri;
    }

    public String getmSongName() {
        return mSongName;
    }

    public void setmSongName(String mSongName) {
        this.mSongName = mSongName;
    }

    public String getmSingerName() {
        return mSingerName;
    }

    public void setmSingerName(String mSingerName) {
        this.mSingerName = mSingerName;
    }

    public String getmUri() {
        return mUri;
    }

    public void setmUri(String mUri) {
        this.mUri = mUri;
    }
}

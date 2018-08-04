package com.example.hungdev.musicplayer.model.entity;

/**
 * Created by hungdev on 24/07/2018.
 */

public class Song {
    private long mId;
    private String mSongName;
    private String mSingerName;
    private byte[] mUriImage;
    private String mUri;

    public Song() {
    }

    public Song(long mId, String mSongName, String mSingerName, byte[] mUriImage, String mUri) {
        this.mId = mId;
        this.mSongName = mSongName;
        this.mSingerName = mSingerName;
        this.mUriImage = mUriImage;
        this.mUri = mUri;
    }

    public long getmId() {
        return mId;
    }

    public void setmId(long mId) {
        this.mId = mId;
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

    public byte[] getmUriImage() {
        return mUriImage;
    }

    public void setmUriImage(byte[] mUriImage) {
        this.mUriImage = mUriImage;
    }

    public String getmUri() {
        return mUri;
    }

    public void setmUri(String mUri) {
        this.mUri = mUri;
    }
}

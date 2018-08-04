package com.example.hungdev.musicplayer.data;

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

    public Song(long id, String songName, String singerName, byte[] uriImage, String uri) {
        this.mId = id;
        this.mSongName = songName;
        this.mSingerName = singerName;
        this.mUriImage = uriImage;
        this.mUri = uri;
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    public String getSongName() {
        return mSongName;
    }

    public void setSongName(String songName) {
        mSongName = songName;
    }

    public String getSingerName() {
        return mSingerName;
    }

    public void setSingerName(String singerName) {
        mSingerName = singerName;
    }

    public byte[] getUriImage() {
        return mUriImage;
    }

    public void setUriImage(byte[] uriImage) {
        mUriImage = uriImage;
    }

    public String getUri() {
        return mUri;
    }

    public void setUri(String uri) {
        mUri = uri;
    }
}

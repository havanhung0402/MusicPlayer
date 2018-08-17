package com.example.hungdev.musicplayer.data;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by hungdev on 24/07/2018.
 */

public class Song implements Parcelable{
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

    protected Song(Parcel in) {
        mId = in.readLong();
        mSongName = in.readString();
        mSingerName = in.readString();
        mUriImage = in.createByteArray();
        mUri = in.readString();
    }

    public static final Creator<Song> SONG_CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel in) {
            return new Song(in);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeLong(mId);
        dest.writeString(mSongName);
        dest.writeString(mSingerName);
        dest.writeByteArray(mUriImage);
        dest.writeString(mUri);
    }

    public static final Creator<Song> CREATOR = new Creator<Song>() {
        @Override
        public Song createFromParcel(Parcel source) {
            return new Song(source);
        }

        @Override
        public Song[] newArray(int size) {
            return new Song[size];
        }
    };
}

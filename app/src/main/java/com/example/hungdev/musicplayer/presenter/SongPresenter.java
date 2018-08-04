package com.example.hungdev.musicplayer.presenter;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.hungdev.musicplayer.model.adapter.SongsAdapter;
import com.example.hungdev.musicplayer.model.entity.Song;
import com.example.hungdev.musicplayer.view.SongView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by hungdev on 03/08/2018.
 */

public class SongPresenter implements SongImpPresenter{
    private Context mContext;
    private SongView mSongView;
    private SongsAdapter mSongsAdapter;

    public SongPresenter(Context mContext, SongView mSongView, SongsAdapter mSongsAdapter) {
        this.mContext = mContext;
        this.mSongView = mSongView;
        this.mSongsAdapter = mSongsAdapter;
    }

    @Override
    public void getMp3External() {
        List<Song> mSongs = new ArrayList<>();
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        ContentResolver mContentResolver = mContext.getContentResolver();
        Uri mBaseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor mCursor = mContentResolver.query(mBaseUri,null, null, null, null);
        if (mCursor == null){
            Toast.makeText(mContext,"Something Went Wrong.", Toast.LENGTH_LONG);
        }else if (!mCursor.moveToFirst()){
            Toast.makeText(mContext,"No Music Found on SD Card.", Toast.LENGTH_LONG);
        }else {
            int mTitleColIndex = mCursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
            int mIdColIndex = mCursor.getColumnIndex(MediaStore.Audio.Media._ID);
            int mArtistColIndex = mCursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
            int mPathIndex = mCursor.getColumnIndex(MediaStore.Audio.Media.DATA);
            while (mCursor.moveToNext()){
                String mSongName = mCursor.getString(mTitleColIndex);
                String mArtist = mCursor.getString(mArtistColIndex);
                String mPath = mCursor.getString(mPathIndex);
                long mId = mCursor.getInt(mIdColIndex);
                metaRetriver.setDataSource(mPath);
                byte[] mImage = metaRetriver.getEmbeddedPicture();
                mSongs.add(new Song(mId, mSongName, mArtist, mImage, mPath));
                mSongsAdapter = new SongsAdapter(mSongs);
                mSongView.displaySongs(mSongsAdapter);
            }
        }
    }
}

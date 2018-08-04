package com.example.hungdev.musicplayer.data.source.local;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.media.MediaMetadataRetriever;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.widget.Toast;

import com.example.hungdev.musicplayer.data.Song;
import com.example.hungdev.musicplayer.data.source.SongDataSource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungdev on 06/08/2018.
 */

public class SongLocalDataSource implements SongDataSource{

    private List<Song> mSongs;
    private Context mContext;
    private int mTitleColIndex;
    private int mIdColIndex;
    private int mArtistColIndex;
    private int mPathIndex;

    public SongLocalDataSource(Context context) {
        this.mContext = context;
    }

    @Override
    public void getSongs(LoadSongsCallback callback) {
        new AsynSong(callback).execute();
    }

    private void outputSong(Cursor cursor) {
        MediaMetadataRetriever metaRetriver = new MediaMetadataRetriever();
        String mSongName = cursor.getString(mTitleColIndex);
        String mArtist = cursor.getString(mArtistColIndex);
        String mPath = cursor.getString(mPathIndex);
        long mId = cursor.getInt(mIdColIndex);
        metaRetriver.setDataSource(mPath);
        byte[] mImage = metaRetriver.getEmbeddedPicture();
        this.mSongs.add(new Song(mId, mSongName, mArtist, mImage, mPath));
    }

    private void inputCursor(Cursor cursor) {
        this.mTitleColIndex = cursor.getColumnIndex(MediaStore.Audio.Media.TITLE);
        this.mIdColIndex = cursor.getColumnIndex(MediaStore.Audio.Media._ID);
        this.mArtistColIndex = cursor.getColumnIndex(MediaStore.Audio.Media.ARTIST);
        this.mPathIndex = cursor.getColumnIndex(MediaStore.Audio.Media.DATA);
    }

    public class AsynSong extends AsyncTask<Void, List<Song>, List<Song>>{

        private SongLocalDataSource.LoadSongsCallback mLoadSongsCallback;

        public AsynSong(LoadSongsCallback loadSongsCallback) {
            mLoadSongsCallback= loadSongsCallback;
        }

        @Override
        protected List<Song> doInBackground(Void... voids) {
            mSongs = new ArrayList<>();
            ContentResolver contentResolver = mContext.getContentResolver();
            Uri baseUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
            Cursor cursor = contentResolver.query(baseUri,null, null, null, null);
            if (cursor == null){
                return null;
            }else if (!cursor.moveToFirst()){
                return null;
            }else {
                inputCursor(cursor);
                while (cursor.moveToNext()){
                    outputSong(cursor);
                }
            }
            return mSongs;
        }

        @Override
        protected void onProgressUpdate(List<Song>[] values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected void onPostExecute(List<Song> songs) {
            super.onPostExecute(songs);
            if(mSongs.isEmpty()){
                mLoadSongsCallback.onDataNotAvailable();
            }else {
                mLoadSongsCallback.onSongLoaded(songs);
            }
        }
    }
}

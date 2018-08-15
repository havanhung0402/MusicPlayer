package com.example.hungdev.musicplayer.player;

import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.data.Song;
import com.example.hungdev.musicplayer.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PlayerActivity extends AppCompatActivity implements PlayerContract.View,
        View.OnClickListener, SeekBar.OnSeekBarChangeListener{

    private MusicService mMusicService;
    private List<Song> mSongs;
    private Song mSong;
    private Boolean mBound = false;
    private ImageView mImageView;
    private TextView mTextViewSong;
    private TextView mTextView_currentTime;
    private TextView mTextView_totalTime;
    private SeekBar mSeekBar;
    private ImageView mImageViewState;
    private ImageView mImageView_next;
    private ImageView mImageView_previous;
    private PlayerPresenter mPresenter;
    private int mPos;
    private boolean mIsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(this);
        findView();
        setOnClick();
        mPresenter = new PlayerPresenter(getApplication(), this);
        mSongs = new ArrayList<>();
        Intent intent = getIntent();
        mSong = intent.getParcelableExtra(Util.KEY_SONG);
        mPos = intent.getIntExtra(Util.KEY_POSITION,0);
        mSongs =  intent.getParcelableArrayListExtra(Util.KEY_SONGS);
        mPresenter.loadSongDetails(mSong, mSongs);
        IntentFilter filter = new IntentFilter();
        filter.addAction(Util.ACTION_NEXT);
        filter.addAction(Util.ACTION_PREVIOUS);
        filter.addAction(Util.ACTION_COMPLETE);
        filter.addAction(Util.ACTION_STATUS);
        registerReceiver(mBroadcastReceiver, filter);
    }
    private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (intent.getAction().equals(Util.ACTION_NEXT)){
                setPositionNext();
            }else if (intent.getAction().equals(Util.ACTION_PREVIOUS)){
                setPositionPrevious();
            }else if (intent.getAction().equals(Util.ACTION_STATUS)){
                if(mMusicService.isPlaying()){
                    mImageViewState.setImageResource(R.drawable.ic_pause_white_48dp);
                }else {
                    mImageViewState.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                }
            }else if (intent.getAction().equals(Util.ACTION_COMPLETE)){
                setPositionNext();
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mBroadcastReceiver);
    }

    private void findView() {
        mImageView = findViewById(R.id.image_song);
        mTextViewSong = findViewById(R.id.text_title);
        mTextView_currentTime = findViewById(R.id.text_currentTime);
        mTextView_totalTime = findViewById(R.id.text_totalTime);
        mSeekBar = findViewById(R.id.seek_bar);
        mImageViewState = findViewById(R.id.button_status_music);
        mImageView_previous = findViewById(R.id.button_previous);
        mImageView_next = findViewById(R.id.button_next);
    }

    private void setOnClick() {
        mImageViewState.setOnClickListener(this);
        mImageView_next.setOnClickListener(this);
        mImageView_previous.setOnClickListener(this);
        mSeekBar.setOnSeekBarChangeListener(this);
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // Respond to the action bar's Up/Home button
            case android.R.id.home:
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (mBound) {
            unbindService(mServiceConnection);
            mBound = false;
        }
    }

    @Override
    public void showSongView(Song song, List<Song> songs) {
        String name = song.getSongName();
        byte[] images = song.getUriImage();
        mTextViewSong.setText(name);
        if(images == null){
            mImageView.setImageResource(R.drawable.image_item_song);
        }else {
            mImageView.setImageBitmap(BitmapFactory.decodeByteArray(images,0, song.getUriImage().length));
        }
        playService();
    }

    @Override
    public void playService() {
        Intent intent = new Intent(this, MusicService.class);
        bindService(intent, mServiceConnection, BIND_AUTO_CREATE);
    }

    @Override
    public void showUpdateView(Song song) {
        String name = song.getSongName();
        byte[] images = song.getUriImage();
        mTextViewSong.setText(name);
        if(images == null){
            mImageView.setImageResource(R.drawable.image_item_song);
        }else {
            mImageView.setImageBitmap(BitmapFactory.decodeByteArray(images,0, song.getUriImage().length));
        }
        mImageViewState.setImageResource(R.drawable.ic_pause_white_48dp);
    }

    @Override
    public void showUpdateCurrentTime(String current) {
        mTextView_currentTime.setText(current);
    }

    @Override
    public void showUpdateSeekBar(int progress) {
        mSeekBar.setProgress(progress);
    }

    private ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.LocalBinder binder = (MusicService.LocalBinder) service;
            mMusicService = binder.getService();
            mMusicService.setPresenter(mPresenter);
            mTextView_totalTime.setText(mMusicService.getDuration());
            mBound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mBound = false;
            mMusicService.unbindService(mServiceConnection);
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.button_status_music:
                setMusicStatus();
                break;
            case R.id.button_next:
                next();
                break;
            case R.id.button_previous:
                previous();
                break;
            case -1:
                finish();
                break;
        }
    }

    private void previous() {
        if(mBound == true){
            try {
                setPositionPrevious();
                mMusicService.previous();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void next() {
        if(mBound == true){
            try {
                setPositionNext();
                mMusicService.next();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void setPositionPrevious(){
        if(mPos == 0){
            mPos = mSongs.size() -1;
            Song song = mSongs.get(mPos);
            showUpdateView(song);
        }else {
            mPos--;
            Song song = mSongs.get(mPos);
            showUpdateView(song);
        }
    }

    private void setPositionNext(){
        if(mPos == mSongs.size() -1){
            mPos = 0;
            Song song = mSongs.get(mPos);
            showUpdateView(song);
        }else {
            mPos++;
            Song song = mSongs.get(mPos);
            showUpdateView(song);
        }
    }

    private void setMusicStatus() {
        if(mBound == true){
            if(mMusicService.isPlaying()){
                mImageViewState.setImageResource(R.drawable.ic_play_arrow_white_48dp);
                mMusicService.pause();
            }else {
                mImageViewState.setImageResource(R.drawable.ic_pause_white_48dp);
                mMusicService.start();
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(fromUser == true){
            mMusicService.seekTo(progress);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {

    }
}

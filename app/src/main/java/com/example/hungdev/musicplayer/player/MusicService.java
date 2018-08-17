package com.example.hungdev.musicplayer.player;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.MediaPlayer;
import android.os.Binder;
import android.os.IBinder;
import android.os.Parcelable;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;
import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.data.Song;
import com.example.hungdev.musicplayer.util.Util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MusicService extends Service implements MediaPlayer.OnPreparedListener,
    MediaPlayer.OnCompletionListener{

    private List<Song> mSongs;
    private int mPos;
    private MediaPlayer mMediaPlayer;
    private RemoteViews mRemoteViews;
    private Notification mNotification;
    private Intent mIntentPlayer;
    private PendingIntent mPendingIntent;
    private final IBinder mIBinder = new LocalBinder();
    public MusicService() {
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mSongs = new ArrayList<>();
        mMediaPlayer = new MediaPlayer();
        mMediaPlayer.setOnCompletionListener(this);
        mMediaPlayer.setOnPreparedListener(this);
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (intent.getAction().equals(Util.ACTION_NEXT)){
                    try {
                        next();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (intent.getAction().equals(Util.ACTION_PREVIOUS)){
                    try {
                        previous();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }else if (intent.getAction().equals(Util.ACTION_STATUS)){
                    if(mMediaPlayer.isPlaying()){
                        pause();
                    }else {
                        start();
                    }
                }else if(intent.getAction().equals(Util.ACTION_CLEAR)){
                    stopForeground(true);
                    stopSelf();
                }
            }
        };
        IntentFilter filter = new IntentFilter();
        filter.addAction(Util.ACTION_NEXT);
        filter.addAction(Util.ACTION_PREVIOUS);
        filter.addAction(Util.ACTION_STATUS);
        filter.addAction(Util.ACTION_CLEAR);
        registerReceiver(broadcastReceiver, filter);

    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        mPos = intent.getIntExtra(Util.KEY_POSITION, 0);
        mSongs =  intent.getParcelableArrayListExtra(Util.KEY_SONGS);
        playmusic(intent);
        return START_NOT_STICKY;
    }

    private void playmusic(Intent intent) {
        Song song = intent.getParcelableExtra(Util.KEY_SONG);
        try {
            mMediaPlayer.reset();
            mMediaPlayer.setDataSource(song.getUri());
            mMediaPlayer.prepareAsync();
        } catch (IOException e) {
            e.printStackTrace();
        }
        showNotification();
    }

    private void showNotification() {
        Song song = mSongs.get(mPos);
        //set notification custom
        String songTitle = song.getSongName();
        String artist = song.getSingerName();
        byte[] images = song.getUriImage();
        mRemoteViews = new RemoteViews(getApplication().getPackageName(), R.layout.notification_media);
        if(images != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
            mRemoteViews.setImageViewBitmap(R.id.imageView_notifi, bitmap);
        }else {
            mRemoteViews.setImageViewResource(R.id.imageView_notifi, R.drawable.image_item_song);
        }
        mRemoteViews.setTextViewText(R.id.text_song, songTitle);
        mRemoteViews.setTextViewText(R.id.text_artist, artist);
        mIntentPlayer = new Intent(this, PlayerActivity.class);
        mIntentPlayer.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        mIntentPlayer.putExtra(Util.KEY_POSITION, mPos);
        mIntentPlayer.putExtra(Util.KEY_SONG, song);
        mIntentPlayer.putParcelableArrayListExtra(Util.KEY_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                mIntentPlayer, PendingIntent.FLAG_UPDATE_CURRENT);

        //create notification
        mNotification = new NotificationCompat.Builder(getApplication(), Util.CHANNEL_ID)
                .setContentIntent(mPendingIntent)
                .setSmallIcon(R.drawable.image_item_song)
                .setCustomBigContentView(mRemoteViews)
                .build();

        //set listener notification
        Intent intentPlay = new Intent(Util.ACTION_STATUS);
        Intent intentPrevious = new Intent(Util.ACTION_PREVIOUS);
        Intent intentNext = new Intent(Util.ACTION_NEXT);
        Intent intentClear = new Intent(Util.ACTION_CLEAR);

        PendingIntent pendingStatus = PendingIntent.getBroadcast(getApplication(),
                0, intentPlay,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingPrevious = PendingIntent.getBroadcast(getApplication(),
                0, intentPrevious,PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingNext = PendingIntent.getBroadcast(getApplication(),
                0, intentNext, PendingIntent.FLAG_UPDATE_CURRENT);
        PendingIntent pendingClear = PendingIntent.getBroadcast(getApplication(),
                0, intentClear, PendingIntent.FLAG_UPDATE_CURRENT);

        mRemoteViews.setOnClickPendingIntent(R.id.button_next_notify, pendingNext);
        mRemoteViews.setOnClickPendingIntent(R.id.button_previous_notify, pendingPrevious);
        mRemoteViews.setOnClickPendingIntent(R.id.button_status_notify, pendingStatus);
        mRemoteViews.setOnClickPendingIntent(R.id.image_clear_notify, pendingClear );

        //show notification
        startForeground(1, mNotification);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mIBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mMediaPlayer.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        try {
            next();
            Intent intentComplete = new Intent(Util.ACTION_COMPLETE);
            sendBroadcast(intentComplete);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class LocalBinder extends Binder {

        MusicService getService(){
            return MusicService.this;
        }
    }

    public void play(int position){
        Song song = mSongs.get(position);
        String path = song.getUri();
        try {
            mMediaPlayer.setDataSource(path);
            mMediaPlayer.prepareAsync();
            //mMediaPlayer.setOnPreparedListener(new );
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public boolean isPlaying(){
        return mMediaPlayer.isPlaying();
    }

    public void pause(){
        if(mMediaPlayer != null){
            mMediaPlayer.pause();
            mRemoteViews.setImageViewResource(R.id.button_status_notify, R.drawable.ic_play_arrow_white_24dp);
            startForeground(1, mNotification);
        }
    }

    public void start(){
        if(mMediaPlayer != null){
            mMediaPlayer.start();
            mRemoteViews.setImageViewResource(R.id.button_status_notify, R.drawable.ic_pause_white_24dp);
            startForeground(1, mNotification);
        }
    }

    public void next() throws IOException {
        if(mPos == mSongs.size() - 1){
            mPos = 0;
            mMediaPlayer.reset();
            play(mPos);
        }else {
            mPos++;
            mMediaPlayer.reset();
            play(mPos);
        }
        updateNotification();
    }

    public void previous() throws IOException {
        if(mPos == 0){
            mPos = mSongs.size() - 1;
            mMediaPlayer.reset();
            play(mPos);
        }else {
            mPos--;
            mMediaPlayer.reset();
            play(mPos);
        }
        updateNotification();
    }

    private void updateNotification() {
        Song song = mSongs.get(mPos);
        //set notification custom
        String songTitle = song.getSongName();
        String artist = song.getSingerName();
        byte[] images = song.getUriImage();
        if(images != null){
            Bitmap bitmap = BitmapFactory.decodeByteArray(images, 0, images.length);
            mRemoteViews.setImageViewBitmap(R.id.imageView_notifi, bitmap);
        }else {
            mRemoteViews.setImageViewResource(R.id.imageView_notifi, R.drawable.image_item_song);
        }
        mIntentPlayer.putExtra(Util.KEY_POSITION, mPos);
        mIntentPlayer.putExtra(Util.KEY_SONG, song);
        mIntentPlayer.putParcelableArrayListExtra(Util.KEY_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        mPendingIntent = PendingIntent.getActivity(this, 0,
                mIntentPlayer, PendingIntent.FLAG_UPDATE_CURRENT);
        mRemoteViews.setImageViewResource(R.id.button_status_notify, R.drawable.ic_pause_white_24dp);
        mRemoteViews.setTextViewText(R.id.text_song, songTitle);
        mRemoteViews.setTextViewText(R.id.text_artist, artist);
        startForeground(1, mNotification);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}

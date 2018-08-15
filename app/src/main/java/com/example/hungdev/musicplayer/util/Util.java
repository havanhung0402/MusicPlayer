package com.example.hungdev.musicplayer.util;

/**
 * Created by hungdev on 15/08/2018.
 */

public class Util {
    public static final String KEY_SONG = "song";
    public static final String KEY_SONGS = "songs";
    public static final String KEY_POSITION = "pos";

    public static final String CHANNEL_ID = "1";
    public static final String ACTION_NEXT = "next";
    public static final String ACTION_PREVIOUS = "previous";
    public static final String ACTION_COMPLETE = "complete";
    public static final String ACTION_STATUS = "status";
    public static final String ACTION_CLEAR = "clear";

    public static long getCurrentTime(int progress, long total){
        double currentTime = ((double)total / 100) * progress;
        return (long) currentTime;
    }

    public static int getProgress(long currentTime, long total){
        double progress = ((double)currentTime / total) * 100;
        return (int) progress;
    }

    public static int toSecond(int current){
        return current/1000;
    }
}

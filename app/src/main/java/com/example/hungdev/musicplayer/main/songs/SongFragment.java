package com.example.hungdev.musicplayer.main.songs;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.data.Song;
import com.example.hungdev.musicplayer.player.MusicService;
import com.example.hungdev.musicplayer.player.PlayerActivity;
import com.example.hungdev.musicplayer.util.Util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment implements SongContract.View, SongsAdapter.SongItemListener{

    private List<Song> mSongs;
    private SongPresenter mSongPresenter;
    private SongsAdapter mSongsAdapter;
    private TextView mTextViewEmpty;
    public SongFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mSongPresenter = new SongPresenter(getActivity(), this);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View mView = inflater.inflate(R.layout.content_main, container, false);
        RecyclerView mRecyclerView = mView.findViewById(R.id.recycler_songs);
        mTextViewEmpty = mView.findViewById(R.id.textView_empty);
        mTextViewEmpty.setVisibility(View.VISIBLE);
        mSongs = new ArrayList<>();
        mSongsAdapter = new SongsAdapter(mSongs, this);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(mSongsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return mView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onResume() {
        super.onResume();
        mSongPresenter.loadSongs();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void showSongs(List<Song> songs) {
       if(songs != null){
           mTextViewEmpty.setVisibility(View.GONE);
           mSongs.clear();
           mSongs.addAll(songs);
           mSongsAdapter.notifyDataSetChanged();
       }
       mSongs = songs;
    }

    @Override
    public void showEmpty() {
        mTextViewEmpty.setVisibility(View.VISIBLE);
    }

    @Override
    public void showPlayer(Song song, int positon) {
        Intent intent = new Intent(getActivity(), PlayerActivity.class);
        intent.putExtra(Util.KEY_SONG, song);
        intent.putExtra(Util.KEY_POSITION, positon);
        intent.putParcelableArrayListExtra(Util.KEY_SONGS, (ArrayList<? extends Parcelable>) mSongs);
        startActivity(intent);
        Intent intentService = new Intent(getActivity(), MusicService.class);
        intentService.putExtra(Util.KEY_SONG, song);
        intentService.putExtra(Util.KEY_POSITION, positon);
        intentService.putParcelableArrayListExtra("songs", (ArrayList<? extends Parcelable>) mSongs);
        getActivity().startService(intentService);
    }

    @Override
    public void showMiniPlayer() {

    }

    @Override
    public void onSongClick(Song clickedSong, int position) {
        mSongPresenter.openPlayer(clickedSong, position);
    }
}

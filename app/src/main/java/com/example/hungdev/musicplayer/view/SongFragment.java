package com.example.hungdev.musicplayer.view;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.model.adapter.SongsAdapter;
import com.example.hungdev.musicplayer.model.entity.Song;
import com.example.hungdev.musicplayer.presenter.SongPresenter;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment implements SongView{
    private SongPresenter mSongPresenter;
    private SongsAdapter mSongsAdapter;
    RecyclerView mRecyclerView;
    public SongFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mRecyclerView = (RecyclerView) inflater.inflate(R.layout.content_main, container, false);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(mLayoutManager);
        mSongPresenter = new SongPresenter(getActivity(), this, mSongsAdapter);
        mSongPresenter.getMp3External();
        return mRecyclerView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void displaySongs(SongsAdapter adapter) {
        mRecyclerView.setAdapter(adapter);
    }

    @Override
    public void play(List<Song> songs) {

    }
}

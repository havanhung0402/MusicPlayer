package com.example.hungdev.musicplayer;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

public class SongFragment extends Fragment {

    private List<Song> mSongs;
    private SongsAdater mSongsAdapter;
    private RecyclerView mRecyclerView;
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
        View view = inflater.inflate(R.layout.fragment_song, container, false);
        mRecyclerView = view.findViewById(R.id.recyclerView);
        mSongs = new ArrayList<>();
        mSongs.add(new Song("Tự lau nước mắt", "Mr.Siro","sdcard/a.jpg"));
        mSongs.add(new Song("Dưới những cơn mưa", "Mr.Siro","sdcard/a.jpg"));
        mSongs.add(new Song("Day dứt nỗi đau", "Mr.Siro","sdcard/a.jpg"));
        mSongs.add(new Song("Lặng lẽ tổn thương", "Mr.Siro","sdcard/a.jpg"));
        mSongs.add(new Song("Đã từng vô giá", "Mr.Siro","sdcard/a.jpg"));
        mSongs.add(new Song("Tình yêu chắp vá", "Mr.Siro","sdcard/a.jpg"));
        mSongs.add(new Song("Gương mặt lạ lẫm", "Mr.Siro","sdcard/a.jpg"));
        mSongsAdapter = new SongsAdater(mSongs);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
        mRecyclerView.setAdapter(mSongsAdapter);
        mRecyclerView.setLayoutManager(mLayoutManager);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }
}

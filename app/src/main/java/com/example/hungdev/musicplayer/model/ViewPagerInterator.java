package com.example.hungdev.musicplayer.model;

import android.content.Context;
import android.support.v4.app.ActivityCompat;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.model.adapter.ViewPagerAdapter;
import com.example.hungdev.musicplayer.presenter.ViewPagerImpPresenter;
import com.example.hungdev.musicplayer.view.SingerFragment;
import com.example.hungdev.musicplayer.view.SongFragment;

/**
 * Created by hungdev on 04/08/2018.
 */

public class ViewPagerInterator extends ActivityCompat{
    private Context mContext;
    private ViewPagerImpPresenter mViewPagerImpPresenter;

    public ViewPagerInterator(Context mContext, ViewPagerImpPresenter mViewPagerImpPresenter) {
        this.mViewPagerImpPresenter = mViewPagerImpPresenter;
        this.mContext = mContext;
    }

    public void createListPager(ViewPagerAdapter mViewPagerAdapter){
        mViewPagerAdapter.addFragment(new SongFragment(), mContext.getResources().getString(R.string.title_song_fragment));
        mViewPagerAdapter.addFragment(new SingerFragment(), mContext.getResources().getString(R.string.title_singer_fragment));
        mViewPagerImpPresenter.loadViewPager(mViewPagerAdapter);
    }
}

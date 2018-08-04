package com.example.hungdev.musicplayer.presenter;

import android.content.Context;

import com.example.hungdev.musicplayer.model.adapter.ViewPagerAdapter;
import com.example.hungdev.musicplayer.model.ViewPagerInterator;
import com.example.hungdev.musicplayer.view.MainView;

/**
 * Created by hungdev on 04/08/2018.
 */

public class ViewPagerPresenter implements ViewPagerImpPresenter{
    private Context mContext;
    private MainView mMainView;
    private ViewPagerInterator mViewPagerInterator;
    private ViewPagerAdapter mViewPagerAdapter;

    public ViewPagerPresenter(Context mContext, MainView mMainView, ViewPagerAdapter mViewPagerAdapter) {
        this.mMainView = mMainView;
        this.mViewPagerInterator = new ViewPagerInterator(mContext, this);
        this.mViewPagerAdapter = mViewPagerAdapter;
    }

    public void loadData(){
        mViewPagerInterator.createListPager(mViewPagerAdapter);
    }
    @Override
    public void loadViewPager(ViewPagerAdapter adapter) {
        mMainView.displayViewPager(adapter);
    }
}

package com.example.hungdev.musicplayer.view;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import com.example.hungdev.musicplayer.R;
import com.example.hungdev.musicplayer.model.adapter.ViewPagerAdapter;
import com.example.hungdev.musicplayer.presenter.ViewPagerPresenter;

public class MainActivity extends AppCompatActivity implements MainView{
    private ViewPagerPresenter mViewPagerPresenter;
    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mTablayout = findViewById(R.id.tabs);
        mViewPagerPresenter.loadData();
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void initPresenter() {
        mViewPagerPresenter = new ViewPagerPresenter(getApplication(),this, new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void displayViewPager(ViewPagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }
}

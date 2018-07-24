package com.example.hungdev.musicplayer;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class MainActivity extends AppCompatActivity {
    private Toolbar mToolbar;
    private TabLayout mTablayout;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.viewpager);
        setUpViewPager();
        mTablayout = findViewById(R.id.tabs);
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void setUpViewPager() {
        ViewPagerAdapter viewPagerAdapter = new ViewPagerAdapter(getSupportFragmentManager());
        viewPagerAdapter.addFragment(new SongFragment(), getString(R.string.title_song_flagment));
        viewPagerAdapter.addFragment(new SingleFragment(), getString(R.string.title_single_flagment));
        mViewPager.setAdapter(viewPagerAdapter);

    }
}

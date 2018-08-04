package com.example.hungdev.musicplayer.view;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.app.ActivityCompat;
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
    private Boolean mIsGranted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPresenter();
        mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        mViewPager = findViewById(R.id.viewpager);
        mTablayout = findViewById(R.id.tabs);
        mViewPagerPresenter.loadData();
        mTablayout.setupWithViewPager(mViewPager);
    }

    private void initPermisstion() {
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if(ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    != PackageManager.PERMISSION_GRANTED){
                mIsGranted = false;
                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},1);
            }else {
                mIsGranted = true;
            }
        }
    }

    private void initPresenter() {
        mViewPagerPresenter = new ViewPagerPresenter(this, this,new ViewPagerAdapter(getSupportFragmentManager()));
    }

    @Override
    public void displayViewPager(ViewPagerAdapter adapter) {
        mViewPager.setAdapter(adapter);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if(permissions.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            mIsGranted = true;
        }else {
            mIsGranted = false;
        }
    }
}

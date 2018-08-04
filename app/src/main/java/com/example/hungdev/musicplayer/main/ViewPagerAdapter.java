package com.example.hungdev.musicplayer.main;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by hungdev on 24/07/2018.
 */

public class ViewPagerAdapter extends FragmentPagerAdapter{
    List<Fragment> mFlagmentList = new ArrayList<>();
    List<String> mFlagmentTitleList = new ArrayList<>();

    public ViewPagerAdapter(FragmentManager fm) {
        super(fm);
    }


    @Override
    public Fragment getItem(int position) {
        return mFlagmentList.get(position);
    }

    @Override
    public int getCount() {
        return mFlagmentList.size();
    }

    public void addFragment(Fragment fragment, String title){
        mFlagmentList.add(fragment);
        mFlagmentTitleList.add(title);
    }
    @Override
    public CharSequence getPageTitle(int position) {
        return mFlagmentTitleList.get(position);
    }
}

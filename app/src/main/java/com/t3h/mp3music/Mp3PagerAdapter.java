package com.t3h.mp3music;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.t3h.mp3music.base.BaseFragment;

public class Mp3PagerAdapter extends FragmentPagerAdapter {
    private BaseFragment []fms;
    public Mp3PagerAdapter( FragmentManager fm,BaseFragment...fms) {
        super(fm);
        this.fms=fms;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return fms[position];
    }

    @Override
    public int getCount() {
        return fms==null?0:fms.length;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return fms[position].getTitle();
    }
}

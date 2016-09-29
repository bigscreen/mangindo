package com.bigscreen.mangindo.adapter;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.bigscreen.mangindo.fragment.MangaImageFragment;

import java.util.List;

public class MangaImagePagerAdapter extends FragmentStatePagerAdapter {

    private List<MangaImageFragment> fragments;

    public MangaImagePagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public MangaImageFragment getItem(int position) {
        if (getCount() > 0) {
            MangaImageFragment fragment = fragments.get(position);
            fragment.setFragmentPosition(position);
            return fragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return fragments == null ? 0 : fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    public void setFragments(List<MangaImageFragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}

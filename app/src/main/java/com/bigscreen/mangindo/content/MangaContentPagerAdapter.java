package com.bigscreen.mangindo.content;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MangaContentPagerAdapter extends FragmentStatePagerAdapter {

    private List<MangaContentFragment> fragments;

    public MangaContentPagerAdapter(FragmentManager fm) {
        super(fm);
        fragments = new ArrayList<>();
    }

    @Override
    public MangaContentFragment getItem(int position) {
        if (getCount() > 0 && position < getCount()) {
            MangaContentFragment fragment = fragments.get(position);
            fragment.setFragmentPosition(position);
            return fragment;
        } else {
            return null;
        }
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + position;
    }

    public void setFragments(List<MangaContentFragment> fragments) {
        this.fragments.clear();
        this.fragments.addAll(fragments);
        notifyDataSetChanged();
    }
}

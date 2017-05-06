package com.bigscreen.mangindo.manga.content;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.List;

public class MangaContentPagerAdapter extends FragmentStatePagerAdapter {

    private List<MangaContentFragment> fragments;

    public MangaContentPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public MangaContentFragment getItem(int position) {
        if (getCount() > 0) {
            MangaContentFragment fragment = fragments.get(position);
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

    public void setFragments(List<MangaContentFragment> fragments) {
        this.fragments = fragments;
        notifyDataSetChanged();
    }
}

package com.bigscreen.mangindo.content

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter


class MangaContentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var fragments: List<MangaContentFragment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment? {
        return if (count > 0 && position < count) {
            val fragment = fragments[position]
            fragment.setFragmentPosition(position)
            fragment
        } else {
            null
        }
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = "Page $position"
}
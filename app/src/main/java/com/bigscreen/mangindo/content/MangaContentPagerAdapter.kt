package com.bigscreen.mangindo.content

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter

class MangaContentPagerAdapter(fm: FragmentManager) : FragmentStatePagerAdapter(fm) {

    var fragments: List<MangaContentFragment> = emptyList()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItem(position: Int): Fragment? {
        return if (position in 0 until count) {
            fragments[position].apply {
                setFragmentPosition(position)
            }
        } else {
            null
        }
    }

    override fun getCount(): Int = fragments.size

    override fun getPageTitle(position: Int): CharSequence = "Page $position"
}

package com.dev.librorum.customViews

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val fragments = mutableListOf<Fragment>()

    constructor(fm: FragmentManager, fragments: Array<Fragment>) : this(fm) {
        this.fragments.addAll(fragments)
    }

    override fun getItem(position: Int): Fragment {
        return fragments[position]
    }

    override fun getCount() = fragments.size
}


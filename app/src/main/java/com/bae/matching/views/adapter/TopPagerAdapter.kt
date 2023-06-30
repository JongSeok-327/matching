package com.bae.matching.views.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.Lifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.bae.matching.views.fragment.ForYouFragment
import com.bae.matching.views.fragment.MyPageFragment
import com.bae.matching.views.fragment.SearchFragment

class TopPagerAdapter(
    fragmentManager: FragmentManager,
    lifecycle: Lifecycle
): FragmentStateAdapter(fragmentManager, lifecycle)
{
    private val fragment: ArrayList<Fragment> = arrayListOf(SearchFragment(), ForYouFragment())
    override fun getItemCount(): Int {
        return fragment.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragment[position]
    }
}
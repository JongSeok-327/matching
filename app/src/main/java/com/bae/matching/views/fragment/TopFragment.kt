package com.bae.matching.views.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.bae.matching.R
import com.bae.matching.databinding.FragmentTopBinding
import com.bae.matching.utils.Dlog
import com.bae.matching.views.adapter.TopPagerAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayout.OnTabSelectedListener
import com.google.android.material.tabs.TabLayoutMediator

class TopFragment : Fragment()
{
    private var _binding: FragmentTopBinding? = null
    private val binding
        get() = _binding!!
    private var tabLayoutMediator: TabLayoutMediator? = null
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       _binding = FragmentTopBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewpager2.apply {
                adapter = TopPagerAdapter(childFragmentManager, this@TopFragment.lifecycle)
                orientation = ViewPager2.ORIENTATION_HORIZONTAL
                offscreenPageLimit = 1
            }
            tabLayout.addOnTabSelectedListener(object : OnTabSelectedListener{
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    Dlog.d("onTabSelected : ${tab?.position}")
                    tab?.position?.let {
                        when (it) {
                            0 -> searchView.visibility = View.VISIBLE
                            1 -> searchView.visibility = View.GONE
                        }
                    }
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}
                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })

            tabLayoutMediator = TabLayoutMediator(tabLayout, viewpager2) { tab, position ->
                tab.text = getTabTitle(position)
            }
            tabLayoutMediator?.attach()
        }
    }

    private fun getTabTitle(position: Int): String? {
        return when (position) {
            0 -> getString(R.string.tab_bar_item_1_sub_1_title)
            1 -> getString(R.string.tab_bar_item_1_sub_2_title)
            else -> null
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tabLayoutMediator?.detach()
        tabLayoutMediator = null
        _binding = null
    }
}
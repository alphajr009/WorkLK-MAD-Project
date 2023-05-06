package com.example.worklk_madproject

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class AccountPagerAdapter(fragment: Fragment) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int {
        return 4
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> About()
            1 -> Education()
            2 -> Experience()
            3 -> Security()
            else -> throw IllegalStateException("Invalid position")
        }
    }
}
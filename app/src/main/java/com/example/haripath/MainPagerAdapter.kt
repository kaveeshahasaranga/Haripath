package com.example.haripath

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class MainPagerAdapter(activity: FragmentActivity) : FragmentStateAdapter(activity) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> TransactionsFragment()
            1 -> BudgetFragment()
            else -> throw IllegalArgumentException("Invalid position: $position")
        }
    }
} 
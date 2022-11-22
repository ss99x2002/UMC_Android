package com.example.assignment4.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.assignment4.memo.fragment.MyprofileFragment
import com.example.assignment4.memo.fragment.BookmarkFragment

class ProfileVPAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0->MyprofileFragment()
            1->BookmarkFragment()
            else -> {MyprofileFragment()}
        }
    }

}
package com.example.assignment4.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.assignment4.memo.MainActivity
import com.example.assignment4.memo.fragment.Profile1Fragment
import com.example.assignment4.memo.fragment.Profile2Fragment

class ProfileVPAdapter(fragmentActivity:FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when(position)
        {
            0->Profile1Fragment()
            1->Profile2Fragment()
            else -> {Profile1Fragment()}
        }
    }

}
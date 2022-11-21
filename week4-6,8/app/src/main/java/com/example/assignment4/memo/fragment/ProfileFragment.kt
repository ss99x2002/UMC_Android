package com.example.assignment4.memo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment4.adapter.ProfileVPAdapter
import com.example.assignment4.databinding.FragmentProfileBinding
import com.google.android.material.tabs.TabLayoutMediator

class ProfileFragment: Fragment(){
    private lateinit var binding:FragmentProfileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val profileVPAdapter = ProfileVPAdapter(this.requireActivity())
        binding.vpProfile.adapter= profileVPAdapter

        val tabTitleArray = arrayOf(
            "나의 프로필","메모 보관함"
        )
        TabLayoutMediator(binding.tabProfile,binding.vpProfile) {tab, position ->
            tab.text = tabTitleArray[position]
        }.attach()

        return binding.root
    }

}
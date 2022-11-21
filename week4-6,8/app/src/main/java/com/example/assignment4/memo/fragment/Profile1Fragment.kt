package com.example.assignment4.memo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment4.databinding.FragmentMyprofileBinding

class Profile1Fragment: Fragment(){
    private lateinit var binding: FragmentMyprofileBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMyprofileBinding.inflate(layoutInflater)
        return binding.root
    }

}
package com.example.assignment4.memo.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.assignment4.databinding.FragmentProfile2Binding

class Profile2Fragment: Fragment(){
    private lateinit var binding: FragmentProfile2Binding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfile2Binding.inflate(layoutInflater)
        return binding.root
    }
}
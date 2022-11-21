package com.example.assignment4.memo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityNextBinding

class NextActivity  : AppCompatActivity(){
    private lateinit var binding:ActivityNextBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.etMemo.setText(intent.getStringExtra("value"))
    }

}
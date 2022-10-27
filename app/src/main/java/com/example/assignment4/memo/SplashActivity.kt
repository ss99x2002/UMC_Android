package com.example.assignment4.memo

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Assignment4)
        super.onCreate(savedInstanceState)
        val intent = Intent(this@SplashActivity,MemoListActivity::class.java)
        startActivity(intent)
        finish()
    }
}
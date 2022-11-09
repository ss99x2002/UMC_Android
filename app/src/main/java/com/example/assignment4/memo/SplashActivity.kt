package com.example.assignment4.memo

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.R

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val handler = Handler()
        handler.postDelayed({
            val intent = Intent(applicationContext,MainActivity::class.java)
            startActivity(intent)
            finish()
        },2000)
    }

    override fun onPause() {
        super.onPause()
        finish()
    }
}

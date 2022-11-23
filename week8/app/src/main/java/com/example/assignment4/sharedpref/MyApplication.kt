package com.example.assignment4.data

import android.app.Application
import com.example.assignment4.sharedpref.MySharedPreferences

class App : Application() {

    companion object {
        lateinit var prefs : MySharedPreferences
    }

    override fun onCreate() {
        prefs = MySharedPreferences(applicationContext)
        super.onCreate()
    }
}
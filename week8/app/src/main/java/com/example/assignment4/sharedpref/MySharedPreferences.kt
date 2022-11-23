package com.example.assignment4.sharedpref

import android.content.Context
import android.content.SharedPreferences


class MySharedPreferences(context: Context) {

    private val prefsFilename = "prefs"
    private val prefs: SharedPreferences = context.getSharedPreferences(prefsFilename, 0)

}
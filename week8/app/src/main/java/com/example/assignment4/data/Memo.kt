package com.example.assignment4.data

import android.graphics.Color
import com.example.assignment4.R

data class Memo(
    val title:String?,
    val note:String?,
    val day:String?,
    val color:String?,
    val favorite:Boolean?
)
data class Color(
    val color:String
)
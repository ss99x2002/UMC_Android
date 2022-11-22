package com.example.assignment4.roomdb

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MemoData(
    @ColumnInfo(name="title") val title:String?,
    @ColumnInfo(name="content") val content:String?,
    @ColumnInfo(name="day") val day : String?,
    @ColumnInfo(name ="color") val color: String?,
    @ColumnInfo(name="favorite") val favorite :Boolean,
    @PrimaryKey(autoGenerate = true) @ColumnInfo (name="memoId") val memoId : Int =0
)

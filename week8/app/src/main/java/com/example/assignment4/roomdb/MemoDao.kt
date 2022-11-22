package com.example.assignment4.roomdb

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MemoDao {
    //메모 추가
    @Insert
    fun insert(memo:MemoData)

    @Delete
    fun delete(memo:MemoData)

    //메모 삭제 (userId 이용)
    @Query ("DELETE FROM MemoData WHERE memoId=:memoId")
    fun deleteMemo(memoId:Int)

    // 메모 전체
    @Query("SELECT * FROM MemoData")
    fun selectAllMemo()

    //메모 내용 수정 및 즐겨찾기 등 . .
    @Query("UPDATE MemoData SET title = :title, content = :content, day = :day, color = :color, favorite = :favorite WHERE memoId=:memoId")
    fun updateMemo(memoId:Int,title:String?,content:String?,day:String?,color:String?,favorite:Boolean)

}
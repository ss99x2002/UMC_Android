package com.example.assignment4.memo.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.adapter.FavoriteRVAdapter
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.databinding.FragmentBookmarkBinding
import com.example.assignment4.roomdb.AppDatabase
import com.example.assignment4.roomdb.MemoData

class BookmarkFragment: Fragment(){
    private lateinit var binding:FragmentBookmarkBinding
    private lateinit var roomDB: AppDatabase
    private var dataList: MutableList<MemoData> = arrayListOf()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentBookmarkBinding.inflate(layoutInflater)
        roomDB = AppDatabase.getInstance(this.requireContext())!!
        setData()
        val dataRVAdapter = FavoriteRVAdapter(dataList)
        with(binding)
        {
            rvFavorite.layoutManager = LinearLayoutManager(context)
            rvFavorite.adapter = dataRVAdapter.apply {
                this.setItemClickListener(object : FavoriteRVAdapter.OnItemClickListener {
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onClick(v: View, position: Int) {
                        Log.e("summer", "onClick 함수 진입")
                    }
                })
            }
        }
        return binding.root
    }
    fun setData()
    {
        dataList = roomDB.memoDao().selectFavorite(true)
        Log.e("dataList", "${dataList}")
    }
}
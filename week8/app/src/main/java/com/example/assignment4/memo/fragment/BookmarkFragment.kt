package com.example.assignment4.memo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.adapter.FavoriteRVAdapter
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.databinding.FragmentBookmarkBinding
import com.example.assignment4.memo.MemoActivity
import com.example.assignment4.roomdb.AppDatabase
import com.example.assignment4.roomdb.MemoData

class BookmarkFragment: Fragment(){
    private lateinit var binding:FragmentBookmarkBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>
    private lateinit var roomDB: AppDatabase
    private var position :Int = -1
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
                        modifyMemo(v, position)
                    }
                })
            }
        }

        getResultText = registerForActivityResult(
                ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== androidx.appcompat.app.AppCompatActivity.RESULT_OK)
            {
                val modify = result.data?.getIntExtra("modify",-1)
                val title = result.data?.getStringExtra("title")
                val note = result.data?.getStringExtra("note")
                val day = result.data?.getStringExtra("day")
                val color = result.data?.getStringExtra("color")
                val favorite = result.data?.getBooleanExtra("favorite",false)
                val memoId = result.data?.getIntExtra("memoId",-1)
                if (modify==1)
                {
                    dataList[position] = MemoData(title, note, day, color, favorite!!, memoId!!)
                    Log.e("data변경처리","${dataList}")
                    binding.rvFavorite.adapter?.notifyItemChanged(position)
                }
            }
        }
        return binding.root
    }

    private fun modifyMemo(v:View,position:Int)
    {
        Log.e("summer","modify 함수 진입")
        this.position = position
        val mIntent2 = Intent(this.requireActivity(), MemoActivity::class.java)
            .apply{
                putExtra("intent","modify")
                putExtra("memoId",dataList[position].memoId)
            }
        getResultText.launch(mIntent2)
    }

    fun setData()
    {
        dataList = roomDB.memoDao().selectFavorite(true)
        Log.e("dataList", "${dataList}")
    }
}
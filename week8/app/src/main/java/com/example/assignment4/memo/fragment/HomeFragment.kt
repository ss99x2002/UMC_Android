package com.example.assignment4.memo.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.R
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.data.Color
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.FragmentHomeBinding
import com.example.assignment4.memo.MemoActivity
import com.example.assignment4.data.Nickname
import com.example.assignment4.roomdb.AppDatabase
import com.example.assignment4.roomdb.MemoData

class HomeFragment  : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>
    private lateinit var roomDB:AppDatabase
    private var dataList: MutableList<MemoData> = arrayListOf()
    private var position:Int = -1

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)
        roomDB = AppDatabase.getInstance(this.requireContext())!!
        val mIntent = Intent(this.requireContext(), MemoActivity::class.java)
        setData()
        val dataRVAdapter = MemoRVAdapter(dataList)
        with(binding)
        {
            rvMemo.layoutManager = LinearLayoutManager(context)
            rvMemo.adapter = dataRVAdapter.apply{
                this.setItemClickListener(object: MemoRVAdapter.OnItemClickListener{
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onClick(v: View, position: Int) {
                            Log.e("summer","onClick 함수 진입")
                            modifyMemo(v, position,mIntent)
                    }
                    @SuppressLint("NotifyDataSetChanged")
                    override fun onDelete(v: View, position: Int) {
                        roomDB.memoDao().deleteMemo(dataList[position].memoId)
                        dataList.removeAt(position)
                        dataRVAdapter.notifyDataSetChanged()
                        Log.e("summer","onDelete 함수 진입")
                    }
                })
            }
            btnAdd.setOnClickListener {
                getResultText.launch(mIntent)
            }
            btnProfile.setOnClickListener {
                Toast.makeText(context,"profile button", Toast.LENGTH_SHORT).show()
            }
        }

        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== AppCompatActivity.RESULT_OK)
            {
                val modify = result.data?.getIntExtra("modify",-1)
                val title = result.data?.getStringExtra("title")
                val note = result.data?.getStringExtra("note")
                val day = result.data?.getStringExtra("day")
                val color = result.data?.getStringExtra("color")
                val favorite = result.data?.getBooleanExtra("favorite",false)
                val memoId = result.data?.getIntExtra("memoId",-1)
                addData(title,note,day,modify,color,favorite!!,memoId!!)
            }
        }
        return binding.root
    }
    private fun addData(title:String?, note:String?, day:String?,modify:Int?,color:String?,favorite:Boolean,memoId:Int)
    {
        if (modify==1)
        {
            dataList[position] = MemoData(title, note, day, color, favorite, memoId)
            binding.rvMemo.adapter?.notifyDataSetChanged()
        }
        else
        {
            if(memoId == -1)
            {
               val memoRealId=dataList[dataList.size-1].memoId +1
                dataList.add(MemoData(title,note,day,color,favorite,memoRealId))
                binding.rvMemo.adapter?.notifyDataSetChanged()
            }
        }
    }

    private fun modifyMemo(v:View,position:Int,mIntent:Intent)
    {
        Log.e("summer","modify 함수 진입")
        this.position = position
        val mIntent2 = Intent(this@HomeFragment.requireContext(),MemoActivity::class.java)
        .apply{
            putExtra("intent","modify")
            putExtra("memoId",dataList[position].memoId)
        }
        getResultText.launch(mIntent2)
    }

    private fun setData()
    {
        if (roomDB!=null)  {
            dataList = roomDB.memoDao().selectAllMemo()
            Log.e("summer","${dataList.toString()}")
//            roomDB.memoDao().insert(MemoData("토요일 UMC 세미나 듣기","INHA University umc 활동","2022.10.21","#FF000000",true))
//            roomDB.memoDao().insert(MemoData("시스템프로그래밍 시험","시험 2022.12.9 잊지말기","2022.11.22","#5ECFFF",false))
//            roomDB.memoDao().insert(MemoData("마늘,대파사기","","2022.10.27","#FF000000",true))
//            roomDB.memoDao().insert(MemoData("선형대수 과제하기","12월 4일까지","2022.11.23","#4A05EB",false))
//            roomDB.memoDao().insert(MemoData("리눅스 과제하기","12월 18일까지 shell 개선하기","2022.11.20","#A5FF55",false))
//            roomDB.memoDao().insert(MemoData("12/18 종강!!!","종강 내놔","2022.11.5","#FFDE00",true))
        }
        binding.tvTitle.setText("${Nickname.getNickName()}님의 메모")
    }
}
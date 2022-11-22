package com.example.assignment4.memo.fragment

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
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.data.Color
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.FragmentHomeBinding
import com.example.assignment4.memo.MemoActivity
import com.example.assignment4.data.Nickname

class HomeFragment  : Fragment() {
    private lateinit var binding:FragmentHomeBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>
    private val dataList: ArrayList<Memo> = arrayListOf()
    private val colorList:ArrayList<Color> = arrayListOf()
    private var preTitle:String =""
    private var preNote:String = ""
    private var preDay:String = ""
    private var position:Int = -1
    private var preColor:String=""
    private var preFavorite:Boolean=false

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(layoutInflater)

        val mIntent = Intent(this.requireContext(), MemoActivity::class.java)
        val dataRVAdapter = MemoRVAdapter(dataList)
        setData()

        with(binding)
        {
            rvMemo.layoutManager = LinearLayoutManager(context)
            rvMemo.adapter = dataRVAdapter.apply{
                this.setItemClickListener(object: MemoRVAdapter.OnItemClickListener{
                    override fun onClick(v: View, position: Int) {
                        Log.e("summer","onClick함수 진입")
                        modifyMemo(v, position,mIntent)
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
                Log.e("summer","제목, 내용, 날짜 순서 ${title + note + day}")
                addData(title,note,day,modify,color,favorite)
                dataRVAdapter.notifyDataSetChanged()
            }
        }
        return binding.root
    }
    private fun addData(title:String?, note:String?, day:String?,modify:Int?,color:String?,favorite:Boolean?)
    {
        if (modify==1)
        {
            if (dataList.contains(Memo(preTitle,preNote,preDay,preColor,preFavorite)))
            {
                dataList.set(position,Memo(title,note,day,color,favorite))
            }
        }
        else
        {
            dataList.add(Memo(title,note,day,color,favorite))
        }
    }

    private fun modifyMemo(v:View,position:Int,mIntent:Intent)
    {
        Log.e("summer","modify 함수 진입")
        preTitle = dataList[position].title!!
        preNote = dataList[position].note!!
        preDay  = dataList[position].day!!
        preColor = dataList[position].color!!
        preFavorite = dataList[position].favorite!!

        this.position = position
        val mIntent2 = Intent(this@HomeFragment.requireContext(),MemoActivity::class.java).apply{
            putExtra("intent","modify")
            putExtra("title",dataList[position].title)
            putExtra("note",dataList[position].note)
            putExtra("day",dataList[position].day)
            putExtra("color",dataList[position].color)
            putExtra("favorite",dataList[position].favorite)
        }
        getResultText.launch(mIntent2)
    }

    private fun setData()
    {
        //더미 데이터 set
        dataList.apply{
            add(Memo("토요일 UMC 세미나 듣기","INHA University umc활동","2022.10.21","#FF000000",false))
            add(Memo("시스템프로그래밍 시험","시험 2022.10.27 잊지말기","2022.10.25","#5ECFFF",false))
            add(Memo("두부사기","","2022.10.27","#FF000000",true))
            add(Memo("대파사기","","2022.10.27","#FF000000",true))
            add(Memo("선형대수 과제하기","11월 1일까지","2022.10.29","#FF000000",false))
            add(Memo("에세이 작성하기","11월 13일까지 shell 만들기","2022.10.31","#0B22B7",false))
            add(Memo("비밀번호","1345","2022.11.01","#FF000000",false))
            add(Memo("11/15 예슬이랑 점심","서울역에서 만나기로 함","2022.11.05","#FF9797",true))
        }
        binding.tvTitle.setText("${Nickname.getNickName()}님의 메모")
    }
}
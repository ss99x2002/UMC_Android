package com.example.assignment4.memo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract.Data
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.R
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.data.Color
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.ActivityMemoBinding
import com.example.assignment4.databinding.ActivityMemoListBinding
import com.example.assignment4.databinding.ItemMemoBinding

class MemoListActivity : AppCompatActivity() {
    private lateinit var getResultText: ActivityResultLauncher<Intent>
    private lateinit var binding:ActivityMemoListBinding
    private val dataList: ArrayList<Memo> = arrayListOf()
    private val colorList:ArrayList<Color> = arrayListOf()
    private var preTitle:String =""
    private var preNote:String = ""
    private var preDay:String = ""
    private var position:Int = -1
    private var preColor:String=""


    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMemoListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val mIntent = Intent(this@MemoListActivity,MemoActivity::class.java)
        val dataRVAdapter = MemoRVAdapter(dataList)
        setData()

        with(binding)
        {
            rvMemo.layoutManager = LinearLayoutManager(this@MemoListActivity)
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
                Toast.makeText(this@MemoListActivity,"profile button",Toast.LENGTH_SHORT).show()
            }
        }

        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== RESULT_OK)
            {
                val modify = result.data?.getIntExtra("modify",-1)
                val title = result.data?.getStringExtra("title")
                val note = result.data?.getStringExtra("note")
                val day = result.data?.getStringExtra("day")
                val color = result.data?.getStringExtra("color")
                Log.e("summer","제목, 내용, 날짜 순서 ${title + note + day}")
                addData(title,note,day,modify,color)
                dataRVAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun addData(title:String?, note:String?, day:String?,modify:Int?,color:String?)
    {
      if (modify==1)
      {
          if (dataList.contains(Memo(preTitle,preNote,preDay,preColor)))
          {
              dataList.set(position,Memo(title,note,day,color))
          }
      }
        else
      {
          dataList.add(Memo(title,note,day,color))
      }
    }

    private fun modifyMemo(v:View,position:Int,mIntent:Intent)
    {
        Log.e("summer","modify 함수 진입")
        preTitle = dataList[position].title!!
        preNote = dataList[position].note!!
        preDay  = dataList[position].day!!
        preColor = dataList[position].color!!
        this.position = position
        val mIntent2 = Intent(this@MemoListActivity,MemoActivity::class.java).apply{
            putExtra("intent","modify")
            putExtra("title",dataList[position].title)
            putExtra("note",dataList[position].note)
            putExtra("day",dataList[position].day)
        }
        getResultText.launch(mIntent2)
    }

    private fun setData()
    {
        //더미 데이터 set
        dataList.apply{
            add(Memo("UMC","INHA University umc활동","2022.10.21","#FF000000"))
            add(Memo("시스템프로그래밍 시험","시험 2022.10.27","2022.10.25","#FF000000"))
            add(Memo("두부사기","","2022.10.27","#FF000000"))
            add(Memo("선형대수 과제하기","11월 1일까지","2022.10.29","#FF000000"))
            add(Memo("리눅스 과제","11월 13일까지 shell 만들기","2022.10.31","#FF000000"))
            add(Memo("비밀번호","1345","2022.11.01","#FF000000"))
        }
    }

}
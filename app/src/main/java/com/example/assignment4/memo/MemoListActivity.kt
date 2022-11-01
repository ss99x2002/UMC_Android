package com.example.assignment4.memo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.R
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.ActivityMemoListBinding
import com.example.assignment4.databinding.ItemMemoBinding

class MemoListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMemoListBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>
    private val dataList: ArrayList<Memo> = arrayListOf()
    private val dataRVAdapter = MemoRVAdapter(dataList)
    private var preTitle:String =""
    private var preNote:String = ""
    private var preDay:String = ""
    private var position:Int = -1


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
        }

        getResultText = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== RESULT_OK)
            {
                val modify = result.data?.getIntExtra("modify",-1)
                val title = result.data?.getStringExtra("title")
                val note = result.data?.getStringExtra("note")
                val day = result.data?.getStringExtra("day")
                Log.e("summer","제목, 내용, 날짜 순서 ${title + note + day}")
                addData(title,note,day,modify)
                dataRVAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun addData(title:String?, note:String?, day:String?,modify:Int?)
    {
      if (modify==1)
      {
          if (dataList.contains(Memo(preTitle,preNote,preDay)))
          {
              dataList.set(position,Memo(title,note,day))
          }
      }
    }

    private fun modifyMemo(v:View,position:Int,mIntent:Intent)
    {
        Log.e("summer","modify 함수 진입")
        preTitle = dataList[position].title!!
        preNote = dataList[position].note!!
        preDay  = dataList[position].day!!
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
            add(Memo("UMC","INHA University umc활동","2022.10.21"))
            add(Memo("SystemProgramming","시험 2022.10.27","2022.10.25"))
            add(Memo("두부사기","","2022.10.27"))
        }
    }

}
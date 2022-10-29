package com.example.assignment4.memo

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.adapter.MemoRVAdapter
import com.example.assignment4.data.Memo
import com.example.assignment4.databinding.ActivityMemoListBinding

class MemoListActivity : AppCompatActivity() {
    private lateinit var binding:ActivityMemoListBinding
    private lateinit var getResultText: ActivityResultLauncher<Intent>
    private val dataList: ArrayList<Memo> = arrayListOf()
    @SuppressLint("NotifyDataSetChanged")
    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        binding = ActivityMemoListBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataList.add(Memo("UMC","INHA University umc활동","2022.10.21"))

        val dataRVAdapter = MemoRVAdapter(dataList)

        getResultText = registerForActivityResult(
            /* contract = */ ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode== RESULT_OK)
            {
                val title = result.data?.getStringExtra("title")
                val note = result.data?.getStringExtra("note")
                val day = result.data?.getStringExtra("day")
                Log.e("summer","제목, 내용, 날짜 순서 ${title + note + day}")
                addData(title,note,day)
                dataRVAdapter.notifyDataSetChanged()
            }

        }

        binding.rvMemo.adapter = dataRVAdapter
        binding.rvMemo.layoutManager = LinearLayoutManager(this)

        with(binding)
        {
            btnAdd.setOnClickListener {
                val intent = Intent(this@MemoListActivity,MemoActivity::class.java)
                getResultText.launch(intent)
            }
        }
    }

    private fun addData(title:String?, note:String?, day:String?)
    {
        dataList.add(Memo(title,note,day))
    }
}
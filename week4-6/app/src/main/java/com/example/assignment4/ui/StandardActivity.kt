package com.example.assignment4.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.assignment4.adapter.StandardRVAdapter
import com.example.assignment4.data.Standard
import com.example.assignment4.data.checkedStatus
import com.example.assignment4.databinding.ActivityStandardBinding

class StandardActivity : AppCompatActivity() {
    private lateinit var binding : ActivityStandardBinding
    private val dataList: ArrayList<Standard> = arrayListOf()
    private val statusList:ArrayList<checkedStatus> = arrayListOf()
    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityStandardBinding.inflate(layoutInflater)
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        dataList.apply {
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
            add(Standard("hello", "INHA University", "2022.10.21"))
        }
        statusList.apply {
            add(checkedStatus(0, true))
            add(checkedStatus(1, false))
            add(checkedStatus(2, false))
            add(checkedStatus(3, true))
            add(checkedStatus(4, false))
            add(checkedStatus(5, true))
            add(checkedStatus(6, false))
            add(checkedStatus(7, true))
            add(checkedStatus(8, false))
            add(checkedStatus(9, false))
            add(checkedStatus(10, true))
            add(checkedStatus(11, false))
            add(checkedStatus(12, true))
            add(checkedStatus(13, false))
        }
            val dataRVAdapter = StandardRVAdapter(dataList,statusList)
            binding.rvStandard.adapter = dataRVAdapter
            binding.rvStandard.layoutManager = LinearLayoutManager(this)
        }
    }
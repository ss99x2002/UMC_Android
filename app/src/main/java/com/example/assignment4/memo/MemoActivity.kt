package com.example.assignment4.memo

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityMemoBinding
import java.time.LocalDate
import java.util.*

class MemoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding
    private lateinit var textVar: Editable
    private lateinit var titleVar : Editable
    private val calendar = Calendar.getInstance()
    private val year = calendar.get(Calendar.YEAR)
    private val month = calendar.get(Calendar.MONTH) + 1
    private val day = calendar.get(Calendar.DAY_OF_MONTH)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView()
    {
        with(binding)
        {
            btnSave.setOnClickListener {
                val mIntent = Intent(this@MemoActivity,MemoListActivity::class.java).apply{
                    putExtra("title","${etTitle.text}")
                    putExtra("note","${etMemo.text}")
                    putExtra("day","${tvDay.text}")
                }
                setResult(RESULT_OK,mIntent)
                if (!isFinishing) finish()
            }
            tvDay.text = (LocalDate.now()).toString()
        }
    }

    override fun onRestart() {
        super.onRestart()
        showDialog()
    }

    override fun onStop() {
        super.onStop()
        with(binding)
        {
            textVar = etMemo.text
            titleVar = etTitle.text
            etMemo.text.clear()
            etTitle.text.clear()
        }
    }

    private fun showDialog()
    {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("메모")
            .setMessage("이어서 작성하시겠습니까?")
            .setPositiveButton("예") {dialog, which ->
                with(binding)
                {
                    etMemo.text = textVar
                    etTitle.text= titleVar
                }
            }
            .setNegativeButton("아니오") {dialog,which ->
                textVar.clear()
                titleVar.clear()
                with(binding)
                {
                    etMemo.text.clear()
                    etTitle.text.clear()
                }
            }
            .create()
            .show()
    }

}
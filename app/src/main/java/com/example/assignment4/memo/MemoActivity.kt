package com.example.assignment4.memo

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.databinding.ActivityMemoBinding

class MemoActivity: AppCompatActivity() {
    private lateinit var binding: ActivityMemoBinding
    lateinit var textVar: Editable
    lateinit var titleVar : Editable
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding)
        {
            btnSave.setOnClickListener {
                val mIntent = Intent(this@MemoActivity,MemoListActivity::class.java).apply{
                    putExtra("title","${etTitle.text}")
                    putExtra("note","${etMemo.text}")
                    putExtra("day","2022.10.26(임시)")
                }
                setResult(RESULT_OK,mIntent)
                if (!isFinishing) finish()
            }
        }
    }

    override fun onStop() {
        super.onStop()
        with(binding)
        {
            textVar = etMemo.text
            titleVar = etTitle.text
            etMemo.text=null
            etTitle.text=null
        }
    }

    override fun onRestart() {
        super.onRestart()
        showDialog()
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
                    etMemo.text = null
                    etTitle.text= null
                }
            }
            .create()
            .show()
    }

}
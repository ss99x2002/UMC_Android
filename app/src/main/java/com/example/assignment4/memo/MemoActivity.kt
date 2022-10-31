package com.example.assignment4.memo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityMemoBinding
import com.example.assignment4.databinding.DialogConfirmBinding
import com.example.assignment4.ui.dialog.CustomDialog
import java.time.LocalDate
import java.util.*

class MemoActivity: AppCompatActivity() ,View.OnClickListener {
    private lateinit var binding: ActivityMemoBinding
    private lateinit var textVar: Editable
    private lateinit var titleVar: Editable
    private lateinit var dlg: CustomDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMemoBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        with(binding)
        {
            tvDay.text = (LocalDate.now()).toString()
        }
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_save -> {
                with(binding)
                {
                    if (etTitle.text.isEmpty()) {
                        Toast.makeText(this@MemoActivity, "제목을 입력하세요!", Toast.LENGTH_SHORT).show()
                    } else {
                        showSaveDialog()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.e("summer","onStop")
        with(binding)
        {
            titleVar = etTitle.text
            textVar = etMemo.text
            etMemo.text.clear()
            etTitle.text.clear()
        }
    }


    override fun onRestart() {
        Log.e("summer","onRestart")
        super.onRestart()
        showDialog()
    }

    private fun showDialog()
    {
        val dialog = AlertDialog.Builder(this)
        dialog.setTitle("메모")
            .setMessage("이어서 작성하시겠습니까?")
            .setPositiveButton("예") {dialog, which ->
                binding.etMemo.text = textVar
            }
            .setNegativeButton("아니오") {dialog,which ->
                textVar.clear()
                binding.etMemo.text= null
            }
            .create()
            .show()
    }

    private fun showBackDialog() {
        Log.e("summer", "back 버튼 눌려짐")
        with(binding)
        {
           DataBindingUtil.inflate<DialogConfirmBinding>(LayoutInflater.from(this@MemoActivity),R.layout.dialog_confirm,null,false).apply {
               this.dialog = CustomDialog(this@MemoActivity,root,"이어서 작성하시겠습니까?").apply{
                   this.setClickListener(object : CustomDialog.DialogClickListener {
                       override fun onConfirm() {
                           etMemo.text = textVar
                           etTitle.text= titleVar
                       }
                       override fun onClose() {
                           textVar.clear()
                           titleVar.clear()
                           with(binding)
                           {
                               etMemo.text.clear()
                               etTitle.text.clear()
                           }
                       }

                   })
                   show()
               }
           }
        }
    }

    private fun showSaveDialog() {
        Log.e("summer", "save dialog로 들어옴")

        with(binding)
        {
            DataBindingUtil.inflate<DialogConfirmBinding>(
                LayoutInflater.from(this@MemoActivity),
                R.layout.dialog_confirm,
                null,
                false
            ).apply {
                this.dialog = CustomDialog(this@MemoActivity, root, "메모를 저장하시겠습니까?").apply {
                    this.setClickListener(object : CustomDialog.DialogClickListener {
                        override fun onConfirm() {
                            Log.e("summer", "save dialog on Confirm으로 들어옴")
                            val mIntent =
                                Intent(this@MemoActivity, MemoListActivity::class.java).apply {
                                    putExtra("title", "${etTitle.text}")
                                    putExtra("note", "${etMemo.text}")
                                    putExtra("day", "${tvDay.text}")
                                }
                            setResult(RESULT_OK, mIntent)
                            if (!isFinishing) finish()
                        }

                        override fun onClose() {
                            Log.e("summer", "save dialog on Close로 들어옴")
                        }
                    })
                    show()
                }
            }
        }
    }
}
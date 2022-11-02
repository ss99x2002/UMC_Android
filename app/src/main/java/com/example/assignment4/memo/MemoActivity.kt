package com.example.assignment4.memo

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityMemoBinding
import com.example.assignment4.databinding.DialogConfirmBinding
import com.example.assignment4.databinding.DialogPaletteBinding
import com.example.assignment4.ui.dialog.CustomDialog
import com.example.assignment4.ui.dialog.CustomPaletteDialog
import java.time.LocalDate
import java.util.*

class MemoActivity: AppCompatActivity() ,View.OnClickListener {
    private lateinit var binding: ActivityMemoBinding
    private lateinit var textVar: Editable
    private lateinit var titleVar: Editable
    private var MODIFY:Int = 0
    private var Color:String ="#FF000000"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_memo)
        setContentView(binding.root)
        Log.e("summer","memo activity 시작")
        with(binding)
        {
                 if (intent.getStringExtra("intent")!=null) {
                        Log.e("summer","modify 진입")
                        etTitle.setText(intent.getStringExtra("title"))
                        etMemo.setText(intent.getStringExtra("note"))
                        tvDay.text = LocalDate.now().toString()
                        MODIFY=1
                    }
                 else {
                        tvDay.text = LocalDate.now().toString()
                        etTitle.setTextColor(getColor(R.color.black))
                        etTitle.text.clear()
                        etMemo.text.clear()
                    }
            }
    }


    override fun onClick(v: View?) {
        with(binding)
        {
            when (v?.id) {
                R.id.btn_save -> {
                    if (etTitle.text.isEmpty()) {
                        Toast.makeText(this@MemoActivity, "제목을 입력하세요!", Toast.LENGTH_SHORT).show()
                    } else {
                        showSaveDialog()
                    }
                }
                R.id.btn_palette->{
                    showPalette()
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        Log.e("summer","onStop")
        with(binding)
        {
            etMemo.text.clear()
            etTitle.text.clear()
            titleVar = etTitle.text
            textVar = etMemo.text
        }
    }

    override fun onRestart() {
        super.onRestart()
        showDialog()
    }

    private fun showDialog()
    {
        val dialog = AlertDialog.Builder(this)
        Log.e("summer","dialog 내부")
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
                                    putExtra("modify",MODIFY)
                                    putExtra("title", "${etTitle.text}")
                                    putExtra("note", "${etMemo.text}")
                                    putExtra("day", "${tvDay.text}")
                                    putExtra("color",Color)
                                    Log.e("summer","${tvDay.text}")
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
    private fun showPalette()
    {
        with(binding)
        {
            DataBindingUtil.inflate<DialogPaletteBinding>(
                LayoutInflater.from(this@MemoActivity),
                R.layout.dialog_palette,
                null,
                false
            ).apply {
                this.colorPick = CustomPaletteDialog(this@MemoActivity, root).apply{
                    this.setClickListener(object : CustomPaletteDialog.DialogClickListener{
                        override fun onClick(v:View?) {
                            Log.e("summer","색상표입장")
                            when(v?.id)
                            {
                                R.id.btn_black->{
                                    Log.e("summer","black true")
                                    etTitle.setTextColor(resources.getColor(R.color.black))
                                    Color="#FF000000"

                                }
                                R.id.btn_navy->{
                                    Log.e("summer","navy true")
                                    etTitle.setTextColor(resources.getColor(R.color.navy))
                                    Color="#0B22B7"
                                }
                                R.id.btn_blue->{
                                    etTitle.setTextColor(resources.getColor(R.color.blue))
                                }
                                R.id.btn_skyBlue->{
                                    etTitle.setTextColor(resources.getColor(R.color.sky_blue))
                                }
                                R.id.btn_purple->{
                                    etTitle.setTextColor(resources.getColor(R.color.purple))
                                }
                                R.id.btn_lightPurple->{
                                    etTitle.setTextColor(resources.getColor(R.color.light_purple))
                                }
                                R.id.btn_pink->{
                                    etTitle.setTextColor(resources.getColor(R.color.pink))
                                }
                                R.id.btn_peach->{
                                    etTitle.setTextColor(resources.getColor(R.color.peach))
                                }
                                R.id.btn_yellow->{
                                    etTitle.setTextColor(resources.getColor(R.color.yellow))
                                }
                                R.id.btn_lightGreen->{
                                    etTitle.setTextColor(resources.getColor(R.color.light_green))
                                }
                            }
                        }
                    })
                    show()
                }

                }
            }
    }
}
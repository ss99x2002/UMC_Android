package com.example.assignment4.memo

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityMemoBinding
import com.example.assignment4.ui.dialog.CustomDialog
import java.time.LocalDate
import java.util.*

class MemoActivity: AppCompatActivity() ,View.OnClickListener{
    private lateinit var binding: ActivityMemoBinding
    private lateinit var textVar: Editable
    private lateinit var titleVar : Editable
    private lateinit var dlg: CustomDialog

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
            tvDay.text = (LocalDate.now()).toString()
        }
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_save ->{
                Log.e("summer","눌려짐")
                with(binding)
                {
                    if (etTitle.text==null)
                    {
                        Log.e("summer","text 입력x")
                        Toast.makeText(this@MemoActivity,"제목을 작성해주세요",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        showSaveDialog()
                    }
                }
            }
        }
    }

    override fun onRestart() {
        super.onRestart()
        showBackDialog()
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

    private fun showBackDialog()
    {
        Log.e("summer","back 버튼 눌려짐")
        dlg = CustomDialog(this@MemoActivity,"이어서 작성하시겠습니까?").apply{
            this.setClickListener(object: CustomDialog.DialogClickListener{
                override fun onConfirm() {
                    with(binding)
                    {
                        etMemo.text = textVar
                        etTitle.text= titleVar
                    }
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
        }
        dlg.show()
    }
    private fun showSaveDialog()
    {
        Log.e("summer","메모 저장버튼 눌려짐")
        dlg = CustomDialog(this@MemoActivity,"메모를 저장하시겠습니까?").apply{
            this.setClickListener(object: CustomDialog.DialogClickListener{
                override fun onConfirm() {
                    with(binding)
                    {
                        val mIntent = Intent(this@MemoActivity,MemoListActivity::class.java).apply{
                            putExtra("title","${etTitle.text}")
                            putExtra("note","${etMemo.text}")
                            putExtra("day","${tvDay.text}")
                        }
                        setResult(RESULT_OK,mIntent)
                        if (!isFinishing) finish()
                    }
                }
                override fun onClose() {
                    TODO("Not yet implemented")
                }
            })
        }
        dlg.show()
    }
}
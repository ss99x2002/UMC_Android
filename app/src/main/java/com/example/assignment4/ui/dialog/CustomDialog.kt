package com.example.assignment4.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.example.assignment4.R

class CustomDialog(context: Context,text:String,width:Int = WindowManager.LayoutParams.MATCH_PARENT, height:Int = WindowManager.LayoutParams.WRAP_CONTENT):Dialog(context) {
    private lateinit var listener: DialogClickListener
    private var isClickConfirm:Boolean = false

    init{
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.dialog_confirm)
        findViewById<TextView>(R.id.tv_dialog_title).text = text
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //바깥 투명하게인듯
        window?.setLayout(width,height)
    }

    interface DialogClickListener{
        fun onClose()
        fun onConfirm()
    }

    fun setClickListener(listener_ : DialogClickListener)
    {
        this.listener = listener_
    }

    fun onClose()
    {
        Log.e("dialog 함수","onClose 들어옴")
        dismiss()
    }

    fun onConfirm()
    {
        Log.e("dialog 함수","onConfirm 들어옴")
        isClickConfirm = true
        dismiss()
        listener?.onConfirm()
    }
}
package com.example.assignment4.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager
import android.widget.TextView
import com.example.assignment4.R

class CustomDialog(context: Context,text:String,width:Int = WindowManager.LayoutParams.WRAP_CONTENT, height:Int = WindowManager.LayoutParams.WRAP_CONTENT):Dialog(context) {
    private var listener: DialogClickListener? = null
    private var isClickConfirm:Boolean = false

    init{
        setCanceledOnTouchOutside(true)
        setContentView(R.layout.dialog_confirm)
        findViewById<TextView>(R.id.tv_dialog_title).text = text
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT)) //바깥 투명하게인듯
        window?.setLayout(width,height)

        setOnDismissListener {
            if (!isClickConfirm) listener?.onClose()
        }
    }

    interface DialogClickListener{
        fun onClose()
        fun onConfirm()
    }

    fun setClickListener(listener : DialogClickListener)
    {
        this.listener = listener
    }

    fun onClose()
    {
        dismiss()
    }

    fun onConfirm()
    {
        isClickConfirm = true
        dismiss()
        listener?.onConfirm()
    }
}
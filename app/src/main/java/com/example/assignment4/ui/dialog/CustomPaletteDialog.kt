package com.example.assignment4.ui.dialog

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import android.view.WindowManager

class CustomPaletteDialog(context: Context,view: View): Dialog(context),View.OnClickListener {
    private lateinit var listener: CustomPaletteDialog.DialogClickListener

    init{
        setCanceledOnTouchOutside(true)
        setContentView(view)
        window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window?.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT)
    }
    interface DialogClickListener{
        fun onClick(v:View?)
    }

    fun setClickListener(listener: DialogClickListener)
    {
        this.listener=listener
    }

    override fun onClick(v: View?) {
        dismiss()
        listener?.onClick(v)
    }
}
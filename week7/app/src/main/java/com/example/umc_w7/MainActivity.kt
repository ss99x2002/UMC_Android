package com.example.umc_w7

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import com.example.umc_w7.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding:ActivityMainBinding
    private var btnFlag = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        val timerText= binding.tvTime
        var pickedMin=binding.numberMin.value
        var pickedSec =binding.numberSec.value
        setNumberPicker()
        
        with(binding)
        {
            numberMin.setOnValueChangedListener(NumberPicker.OnValueChangeListener { numberPicker, old, new ->
                pickedMin = new
                timerText.text = setTime(pickedMin,pickedSec)
            })
            numberSec.setOnValueChangedListener(NumberPicker.OnValueChangeListener { numberPicker, old, new ->
                pickedSec = new
                timerText.text = setTime(pickedMin,pickedSec)
            })
        }
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_picker_start-> {
                binding.layoutTimepicker.isInvisible=true
                binding.layoutTimer.visibility = View.VISIBLE
            }
            R.id.btn_cancel_timer -> {
                binding.layoutTimepicker.visibility = View.VISIBLE
                binding.layoutTimer.isInvisible=true
            }

            R.id.btn_start_timer -> {
                btnFlag++
                when(btnFlag)
                {
                    1 -> {
                        binding.btnStartTimer.text = "정지"
                        btnFlag++
                        Log.e("summer","flag1 : ${btnFlag}")
                    }
                    2 -> {
                        binding.btnStartTimer.text = "재개"
                        btnFlag ++
                        Log.e("summer","flag2 : ${btnFlag} ")
                    }
                   else -> {
                       btnFlag = 0
                       binding.btnStartTimer.text = "시작"
                       Log.e("summer","flag else : ${btnFlag} ")
                   }
                }

           }
        }
    }

    private fun timer (flag : Int)
    {

    }
    private fun setNumberPicker()
    {
        with(binding)
        {
            numberMin.apply{
                this.maxValue=59
                this.value=0
                this.wrapSelectorWheel=false
            }
            numberSec.apply{
                this.maxValue=59
                this.value=0
                this.wrapSelectorWheel=false
            }

        }
    }
    private fun setTime(min:Int, sec:Int) : String
    {
        if (min<10)
        {
            return if (sec<10) {
                "0${min} : 0${sec}"
            } else {
                "0${min} : $sec"
            }
        }
        else
        {
            return if (sec<10) {
                "$min : 0${sec}"
            } else {
                "$min : $sec"
            }
        }
    }
}

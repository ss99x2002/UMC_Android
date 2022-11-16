package com.example.umc_w7

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.*
import androidx.appcompat.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.NumberPicker
import androidx.annotation.RequiresApi
import androidx.core.view.isInvisible
import androidx.databinding.DataBindingUtil
import com.example.umc_w7.databinding.ActivityMainBinding
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity(), View.OnClickListener{

    private lateinit var binding:ActivityMainBinding
    private var started = false
    private var total =0

    val handler = object:Handler(Looper.getMainLooper())
    {
        @SuppressLint("SetTextI18n")
        override fun handleMessage(msg: Message) {
            super.handleMessage(msg)

            binding.tvTime.text = "${String.format("%02d",total/60)} : ${String.format("%02d",total%60)}"
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_main)

        var pickedMin=binding.numberMin.value
        var pickedSec =binding.numberSec.value
        total = pickedMin*60+pickedSec

        setNumberPicker()
        with(binding)
        {
            numberMin.setOnValueChangedListener(NumberPicker.OnValueChangeListener { numberPicker, old, new ->
                pickedMin = new
            })
            numberSec.setOnValueChangedListener(NumberPicker.OnValueChangeListener { numberPicker, old, new ->
                pickedSec = new
                total = pickedMin*60+pickedSec
            })
            btnStartTimer.setOnClickListener {
                started = true
                toggleBtn(false)

                progressTimer.max = total
                progressTimer.progress = total

                thread(start=true) {
                    while(started) {
                        if (total!=0) {
                            Log.e("summer","$total")
                            if (total < 5)
                            {
                                tvTime.setTextColor(Color.parseColor("#FF0000"))
                            }
                            else {
                                tvTime.setTextColor(Color.parseColor("#FF000000"))
                            }
                            total--
                            handler?.sendEmptyMessage(0)
                            Thread.sleep(1000)
                        }
                    }
                }
            }
            btnStopTimer.setOnClickListener {
                if (started) {
                    started = false
                    toggleBtn(true)
                    Log.e("summer","$started")
                }
            }
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_picker_start-> {
                toggleView(true)
                binding.tvTime.text = "${String.format("%02d",total/60)} : ${String.format("%02d",total%60)}"
            }
            R.id.btn_cancel_timer -> {
                total=0
                toggleView(false)
                started = false
                toggleBtn(true)
            }
        }
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
    private fun toggleView(flag : Boolean)
    {
        if (flag)
        {
            // 타이머창 보이게
            binding.layoutTimepicker.isInvisible=true
            binding.layoutTimer.visibility = View.VISIBLE
            binding.tvTime.setTextColor(Color.parseColor("#FF000000"))
        }
        else
        {
            // 시간 선택창 보이게
            binding.layoutTimepicker.visibility = View.VISIBLE
            binding.layoutTimer.isInvisible=true
            binding.numberMin.value =0
            binding.numberSec.value =0
        }
    }
    private fun toggleBtn(flag:Boolean)
    {
        if (flag)
        {
            // 시작 버튼 보이게
            binding.btnStartTimer.visibility=View.VISIBLE
            binding.btnStopTimer.isInvisible = true
        }
        else {
            // 정지버튼 보이게
            binding.btnStartTimer.isInvisible = true
            binding.btnStopTimer.visibility=View.VISIBLE
        }
    }
}

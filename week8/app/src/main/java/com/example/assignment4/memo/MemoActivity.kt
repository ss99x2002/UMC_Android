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
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityMemoBinding
import com.example.assignment4.databinding.DialogConfirmBinding
import com.example.assignment4.databinding.DialogPaletteBinding
import com.example.assignment4.databinding.FragmentHomeBinding
import com.example.assignment4.roomdb.AppDatabase
import com.example.assignment4.roomdb.MemoData
import com.example.assignment4.ui.dialog.CustomDialog
import com.example.assignment4.ui.dialog.CustomPaletteDialog
import java.time.LocalDate
import java.util.*

class MemoActivity: AppCompatActivity() ,View.OnClickListener {
    private lateinit var binding: ActivityMemoBinding
    private lateinit var textVar: Editable
    private lateinit var titleVar: Editable
    private lateinit var roomDB: AppDatabase
    private var MODIFY:Int = 0
    private var colorString :String ="#FF000000"
    private var favorite : Boolean = false
    private var memoId = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_memo)
        setContentView(binding.root)
        roomDB = AppDatabase.getInstance(this@MemoActivity)!!
        onBackPressedDispatcher.addCallback(this,onBackInvokedCallback)
        Log.e("summer","memo activity 시작")
        with(binding)
        {
                 if (intent.getStringExtra("intent")!=null) {
                        Log.e("summer","modify 진입")
                     memoId = intent.getIntExtra("memoId",-1)
                     Log.e("memoid","${memoId}")
                     if (memoId != -1)
                     {
                         Log.e("room db안 값","${roomDB.memoDao().selectOneMemo(memoId)}")
                         etTitle.setText(roomDB.memoDao().selectOneMemo(memoId).title)
                         etMemo.setText(roomDB.memoDao().selectOneMemo(memoId).content)
                         tvDay.text = LocalDate.now().toString()
                         etTitle.setTextColor(Color.parseColor(roomDB.memoDao().selectOneMemo(memoId).color))
                         favorite = roomDB.memoDao().selectOneMemo(memoId).favorite
                         colorString = roomDB.memoDao().selectOneMemo(memoId).color!!
                     }
                        if (favorite)
                        {
                            toggleFavorite(true)
                        }
                        else {
                         toggleFavorite(false)
                        }
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
                R.id.layout_star-> {
                    if (btnFalseStar.isVisible)
                    {
                       toggleFavorite(true)
                    }
                    else
                    {
                        toggleFavorite(false)
                    }
                }
            }
        }
    }

  private val onBackInvokedCallback = object: OnBackPressedCallback(true)
  {
      override fun handleOnBackPressed() {
          showBackDialog()
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

                       }
                       override fun onClose() {
                          finish()
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
                            Log.e("summer", "save dialog onConfirm 로 들어옴")
                            val mIntent =
                                Intent(this@MemoActivity, FragmentHomeBinding::class.java).apply {
                                    putExtra("modify",MODIFY)
                                    putExtra("title", "${etTitle.text}")
                                    putExtra("note", "${etMemo.text}")
                                    putExtra("day", "${tvDay.text}")
                                    putExtra("color",colorString)
                                    putExtra("favorite",favorite)
                                    if(memoId!=-1)
                                    {
                                        roomDB.memoDao().updateMemo(memoId,"${etTitle.text}","${etMemo.text}","${tvDay.text}",colorString,favorite)
                                        putExtra("memoId",memoId)
                                    }
                                    else
                                    {
                                        Log.e("추가로 들어옴","true")
                                        roomDB.memoDao().insert(MemoData("${etTitle.text}","${etMemo.text}","${tvDay.text}",colorString,favorite))
                                        Log.e("모든 값","${roomDB.memoDao().selectAllMemo()}")
                                    }
                                }
                            setResult(RESULT_OK, mIntent)
                            if (!isFinishing) finish()
                        }
                        override fun onClose() {
                            Log.e("summer", "save dialog onClose로 들어옴")
                        }
                    })
                    show()
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()

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
                                    etTitle.setTextColor(getColor(R.color.black))
                                    colorString="#FF000000"

                                }
                                R.id.btn_navy->{
                                    Log.e("summer","navy true")
                                    etTitle.setTextColor(getColor(R.color.navy))
                                    colorString="#0B22B7"
                                }
                                R.id.btn_blue->{
                                    etTitle.setTextColor(getColor(R.color.blue))
                                    colorString="#0066FF"
                                }
                                R.id.btn_skyBlue->{
                                    etTitle.setTextColor(getColor(R.color.sky_blue))
                                    colorString="#5ECFFF"
                                }
                                R.id.btn_purple->{
                                    etTitle.setTextColor(getColor(R.color.purple))
                                    colorString="#4A05EB"
                                }
                                R.id.btn_lightPurple->{
                                    etTitle.setTextColor(getColor(R.color.light_purple))
                                    colorString="#C495FF"
                                }
                                R.id.btn_pink->{
                                    etTitle.setTextColor(getColor(R.color.pink))
                                    colorString="#FD6ED2"
                                }
                                R.id.btn_peach->{
                                    etTitle.setTextColor(getColor(R.color.peach))
                                    colorString="#FF9797"
                                }
                                R.id.btn_yellow->{
                                    etTitle.setTextColor(getColor(R.color.yellow))
                                    colorString="#FFDE00"
                                }
                                R.id.btn_lightGreen->{
                                    etTitle.setTextColor(getColor(R.color.light_green))
                                    colorString="#A5FF55"
                                }
                            }
                        }
                    })
                    show()
                }

                }
            }
    }

    private fun toggleFavorite(star : Boolean)
    {
        // black->yellow
        if (star)
        {
            binding.btnFalseStar.isInvisible=true
            binding.btnTrueStar.visibility = View.VISIBLE
            favorite=true
        }
        // yellow->black
        else
        {
            binding.btnTrueStar.isInvisible=true
            binding.btnFalseStar.visibility = View.VISIBLE
            favorite=false
        }
    }
}
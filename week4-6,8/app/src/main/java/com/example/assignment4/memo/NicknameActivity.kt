package com.example.assignment4.memo

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.example.assignment4.R
import com.example.assignment4.databinding.ActivityNicknameBinding

class NicknameActivity : AppCompatActivity(), View.OnClickListener{
    private lateinit var binding:ActivityNicknameBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=DataBindingUtil.setContentView(this,R.layout.activity_nickname)
    }

    override fun onClick(v: View?) {
        when(v?.id)
        {
            R.id.btn_confirm->{
                Log.e("summer","입력완료 버튼 눌러짐")
                with(binding)
                {
                    if (etNickname.text.isEmpty())
                    {
                        Toast.makeText(this@NicknameActivity,"닉네임을 반드시 입력해주세요",Toast.LENGTH_SHORT).show()
                    }
                    else
                    {
                        Nickname.setNickName(etNickname.text.toString())
                        val intent = Intent(this@NicknameActivity,MemoListActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }
}
package com.example.umc_w7

import android.media.MediaPlayer
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.example.umc_w7.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPlayerBinding
    var songTime =0
    var songEnd = 223
    private var mediaPlayer : MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)

//        with(binding)
//        {
//            btnPlay.setOnClickListener {
//                toggleBtn(true)
//                songPlay()
//            }
//        }
    }


    private fun toggleBtn(flag:Boolean)
    {
        if (flag)
        {
            binding.btnPlay.isInvisible= false
            binding.btnPause.visibility= View.VISIBLE
        }
        else
        {
            binding.btnPlay.visibility= View.VISIBLE
            binding.btnPause.isInvisible= false
        }
    }

    private fun songPlay()
    {
        binding.tvStartTime.text = String.format("%02d:%02d",songTime/60, songTime%60)
        binding.progressPlayer.progress = (songTime * 1000/songEnd)
      //  mediaPlayer = MediaPlayer.create(this,R.raw.)
    }
}
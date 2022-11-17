package com.example.umc_w7

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isInvisible
import com.example.umc_w7.databinding.ActivityPlayerBinding

class PlayerActivity : AppCompatActivity(){
    private lateinit var binding: ActivityPlayerBinding
    var songTime =0
    var songEnd = 223
    lateinit var timer:Timer
    private var mediaPlayer : MediaPlayer? = null

    inner class Timer (private val playTime:Int, var isPlaying:Boolean = true) : Thread() {
        private var second: Int = 0
        private var mills: Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime) {
                        break;
                    }
                    if (isPlaying) {
                        sleep(50)
                        mills += 50
                        runOnUiThread {
                            binding.progressPlayer.progress = ((mills / playTime) * 100).toInt()
                        }
                        if (mills % 1000 == 0f) {
                            runOnUiThread {
                                binding.tvStartTime.text =
                                    String.format("%02d:%02d", second / 60, second % 60)
                            }
                        }
                        second++
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다. ${e.message}")
            }
        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPlayerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.tvStartTime.text = String.format("%02d:%02d",songTime/60, songTime%60)

        with(binding)
        {
            btnPlay.setOnClickListener {
                songPlay()
                toggleBtn(true)
            }
            btnPause.setOnClickListener {
                toggleBtn(false)
            }
        }
    }


    private fun toggleBtn(flag:Boolean)
    {
        if (flag)
        {
            binding.btnPlay.isInvisible= true
            binding.btnPause.visibility= View.VISIBLE
            mediaPlayer?.start()

        }
        else
        {
            binding.btnPlay.visibility= View.VISIBLE
            binding.btnPause.isInvisible= true
            if (mediaPlayer?.isPlaying == true)
            {
                mediaPlayer?.pause()
            }
        }
    }

    private fun songPlay()
    {
        binding.progressPlayer.progress = (songTime * 1000/songEnd)
        val music = resources.getIdentifier("day6","raw",this.packageName)
        mediaPlayer = MediaPlayer.create(this,music)
        timer = Timer(songEnd)
        timer.start()
    }
}
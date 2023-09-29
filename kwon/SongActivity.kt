package com.example.umc_5th_1week

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.umc_5th_1week.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {
    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.songDownIb.setOnClickListener {
            finish()
        }

        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songRepeatIv.setOnClickListener {
            setPlayerStatus2(false)
        }
        binding.songRepeatIv2.setOnClickListener {
            setPlayerStatus2(true)
        }
        binding.songRandomIv.setOnClickListener {
            setPlayerStatus3(false)
        }
        binding.songRandomIv2.setOnClickListener {
            setPlayerStatus3(true)
        }
        if(intent.hasExtra("title")&&intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
    }

    fun setPlayerStatus (isPlaying : Boolean){
        if(isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        } else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
    fun setPlayerStatus2  (isPlaying : Boolean){
        if(isPlaying){
            binding.songRepeatIv.visibility = View.VISIBLE
            binding.songRepeatIv2.visibility = View.GONE
        } else {
            binding.songRepeatIv.visibility = View.GONE
            binding.songRepeatIv2.visibility = View.VISIBLE
        }
    }

    fun setPlayerStatus3  (isPlaying : Boolean){
        if(isPlaying){
            binding.songRandomIv.visibility = View.VISIBLE
            binding.songRandomIv2.visibility = View.GONE
        } else {
            binding.songRandomIv.visibility = View.GONE
            binding.songRandomIv2.visibility = View.VISIBLE
        }
    }
}
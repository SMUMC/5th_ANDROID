package com.example.floclone

import android.content.Context
import android.os.Bundle
import android.os.PersistableBundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
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
            songRepeatStatus()
        }
        binding.songRandomIv.setOnClickListener {
            songRandomStatus()
        }
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
        }
    }

    // 음악이 재생중일 때와 아닐 때를 구분
    fun setPlayerStatus(isPlaying : Boolean){
        if (isPlaying){
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }

    fun songRepeatStatus(){
        if (binding.songRepeatIv.colorFilter == null){
            binding.songRepeatIv.setColorFilter(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        }
        else {
            binding.songRepeatIv.clearColorFilter()
        }
    }

    fun songRandomStatus(){
        if (binding.songRandomIv.colorFilter == null){
            binding.songRandomIv.setColorFilter(ContextCompat.getColor(this, androidx.appcompat.R.color.material_blue_grey_800))
        }
        else {
            binding.songRandomIv.clearColorFilter()
        }
    }

}
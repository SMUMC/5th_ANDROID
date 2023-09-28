package com.example.flo

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding

//kotlin에서는 콜론(:)을 사용하면 상속을 받는다는 뜻, 소괄호는 클래스를 상속받을 때 무조건 써주어야 함
class SongActivity : AppCompatActivity() {
    //lateinit: 나중에 초기화 한다는 뜻
    lateinit var binding : ActivitySongBinding

    //반드시 실행시켜주어야 하는 함수가 있음 -> onCreate()
    //이 함수는 액티비티가 실행될 때 처음으로 실행되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate: xml에 표기된 레이아웃들을 메모리에 객체화
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

        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
            binding.songMusicTitleTv.text = intent.getStringExtra("title")
            binding.songSingerNameTv.text = intent.getStringExtra("singer")
            Toast.makeText(this.applicationContext, intent.getStringExtra("title") + " " +  intent.getStringExtra("singer"), Toast.LENGTH_SHORT).show()
        }
    }

    fun setPlayerStatus(isPlaying : Boolean) {
        if(isPlaying) {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
        else {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
    }
}
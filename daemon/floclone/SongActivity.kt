package com.example.floclone

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.floclone.databinding.ActivitySongBinding

class SongActivity : AppCompatActivity() {

    lateinit var binding: ActivitySongBinding
    lateinit var song : Song
    lateinit var timer : Timer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySongBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initSong()
        setPlayer(song)

        binding.songDownIb.setOnClickListener {
            finish()
        }
        binding.songMiniplayerIv.setOnClickListener {
            setPlayerStatus(true)
        }
        binding.songPauseIv.setOnClickListener {
            setPlayerStatus(false)
        }
        binding.songRepeatIv.setOnClickListener {
            songRepeatStatus()
        }
        binding.songRandomIv.setOnClickListener {
            songRandomStatus()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
    }

    private fun initSong() {
        if (intent.hasExtra("title") && intent.hasExtra("singer")){
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false)
            )
        }
        startTimer()
    }

    private fun setPlayer(song : Song){
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)

        setPlayerStatus(song.isPlaying)

    }

    // 음악이 재생중일 때와 아닐 때를 구분
    private fun setPlayerStatus(isPlaying : Boolean){
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if (isPlaying){
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
        }
    }

    private fun songRepeatStatus(){
        if (binding.songRepeatIv.colorFilter == null){
            binding.songRepeatIv.setColorFilter(ContextCompat.getColor(this, R.color.song_player))
        }
        else {
            binding.songRepeatIv.clearColorFilter()
        }
    }

    private fun songRandomStatus(){
        if (binding.songRandomIv.colorFilter == null){
            binding.songRandomIv.setColorFilter(ContextCompat.getColor(this, R.color.song_player))
        }
        else {
            binding.songRandomIv.clearColorFilter()
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    inner class Timer(private val playTime: Int, var isPlaying: Boolean = true): Thread() {
        private var second : Int = 0
        private var millis : Float = 0f

        override fun run() {
            super.run()
            try {
                while (true) {
                    if (second >= playTime){
                        break
                    }

                    if (isPlaying){
                        sleep(50)
                        millis += 50

                        runOnUiThread {
                            binding.songProgressSb.progress = ((millis / playTime)*100).toInt()
                        }

                        if ((millis % 1000).toInt() == 0) {
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second++
                        }
                    }
                }

            }catch (e: InterruptedException) {
                Log.d("song", "Thread Disable, ${e.message}")

            }
        }

    }

}
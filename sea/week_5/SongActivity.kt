package com.example.flo

import android.media.MediaPlayer
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.flo.databinding.ActivityMainBinding
import com.example.flo.databinding.ActivitySongBinding
import com.google.gson.Gson

//kotlin에서는 콜론(:)을 사용하면 상속을 받는다는 뜻, 소괄호는 클래스를 상속받을 때 무조건 써주어야 함
class SongActivity : AppCompatActivity() {
    //lateinit: 나중에 초기화 한다는 뜻
    lateinit var binding : ActivitySongBinding
    lateinit var song : Song
    lateinit var timer: Timer
    private var mediaPlayer: MediaPlayer? = null
    private var gson: Gson = Gson()

    //반드시 실행시켜주어야 하는 함수가 있음 -> onCreate()
    //이 함수는 액티비티가 실행될 때 처음으로 실행되는 함수
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //inflate: xml에 표기된 레이아웃들을 메모리에 객체화
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

//        if (intent.hasExtra("title") && intent.hasExtra("singer")) {
//            binding.songMusicTitleTv.text = intent.getStringExtra("title")
//            binding.songSingerNameTv.text = intent.getStringExtra("singer")
//            Toast.makeText(this.applicationContext, intent.getStringExtra("title") + " " +  intent.getStringExtra("singer"), Toast.LENGTH_SHORT).show()
//        }
    }


    private fun initSong() {
        if(intent.hasExtra("title") && intent.hasExtra("singer")) {
            song = Song(
                intent.getStringExtra("title")!!,
                intent.getStringExtra("singer")!!,
                intent.getIntExtra("second", 0),
                intent.getIntExtra("playTime", 0),
                intent.getBooleanExtra("isPlaying", false),
                intent.getStringExtra("music")!!
            )
        }
        startTimer()
    }

    private fun setPlayer(song: Song) {
        binding.songMusicTitleTv.text = intent.getStringExtra("title")!!
        binding.songSingerNameTv.text = intent.getStringExtra("singer")!!
        binding.songStartTimeTv.text = String.format("%02d:%02d", song.second / 60, song.second % 60)
        binding.songEndTimeTv.text = String.format("%02d:%02d", song.playTime / 60, song.playTime % 60)
        binding.songProgressSb.progress = (song.second * 1000 / song.playTime)
        val music = resources.getIdentifier(song.music, "raw", this.packageName)
        mediaPlayer = MediaPlayer.create(this, music)
        setPlayerStatus(song.isPlaying)
    }



    private fun setPlayerStatus(isPlaying : Boolean) {
        song.isPlaying = isPlaying
        timer.isPlaying = isPlaying

        if(isPlaying) {
            binding.songMiniplayerIv.visibility = View.GONE
            binding.songPauseIv.visibility = View.VISIBLE
            mediaPlayer?.start()
        }
        else {
            binding.songMiniplayerIv.visibility = View.VISIBLE
            binding.songPauseIv.visibility = View.GONE
            if(mediaPlayer?.isPlaying == true) {
                mediaPlayer?.pause()
            }
        }
    }

    private fun startTimer() {
        timer = Timer(song.playTime, song.isPlaying)
        timer.start()
    }

    //inner class : 내부의 클래스
    inner class Timer(private val playTime : Int, var isPlaying : Boolean = true): Thread() {
        private var second : Int = 0
        private var mills : Float = 0f
        override fun run() {
            super.run()
            try {
                while(true) {
                    if (second >= playTime) {
                        break
                    }
                    if (isPlaying) {
                        sleep(50)
                        mills += 50
                        runOnUiThread {
                            binding.songProgressSb.progress = ((mills / playTime)*100).toInt()
                        }
                        if(mills % 1000 == 0f){
                            runOnUiThread {
                                binding.songStartTimeTv.text = String.format("%02d:%02d", second / 60, second % 60)
                            }
                            second ++
                        }
                    }
                }
            }catch (e : InterruptedException) {
                Log.d("Song", "쓰레드가 죽었습니다.${e.message}")
            }
        }
    }
    override fun onPause() {
        super.onPause()
        setPlayerStatus(false)
        song.second = ((binding.songProgressSb.progress * song.playTime)/100)/1000
        //MODE_PRIVATE: 이 앱에서만 사용가능하게 하는 것 - 가시성 제어
        val sharedPreferences = getSharedPreferences("song", MODE_PRIVATE)
        val editor = sharedPreferences.edit() //에디터
//        editor.putString("title", song.title)
//        editor.putString("singer", song.singer) //Gson을 통해 Json으로 변환
        val songJson = gson.toJson(song)
        editor.putString("songData", songJson)
        editor.apply() //git의 commit,put 기능과 동일
    }

    override fun onDestroy() {
        super.onDestroy()
        timer.interrupt()
        mediaPlayer?.release()
        mediaPlayer = null
    }



}
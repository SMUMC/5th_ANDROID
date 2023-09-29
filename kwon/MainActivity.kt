package com.example.umc_5th_1week

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.FragmentManager
import com.example.umc_5th_1week.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var homefragment: HomeFragment
    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val song = Song(binding.mainMiniplayerTitleTv.text.toString(),binding.mainMiniplayerSingerTv.text.toString())

        binding.mainPlayerCl.setOnClickListener {
            val intent = Intent(this,SongActivity::class.java)
            intent.putExtra("title",song.title)
            intent.putExtra("singer",song.singer)
            startActivity(intent)
        }
        homefragment = HomeFragment()
        supportFragmentManager.beginTransaction().replace(R.id.main_frm, homefragment).commit()
    }

}
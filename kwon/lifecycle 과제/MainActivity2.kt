package com.example.umc_4

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.umc_4.databinding.ActivityMain2Binding
import com.example.umc_4.databinding.ActivityMainBinding

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textview.text = intent.getStringExtra("string")

    }
}
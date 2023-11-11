package com.example.umc_4

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.method.TextKeyListener.clear
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.umc_4.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val TAG = "MainActivity"

    private lateinit var binding:ActivityMainBinding
    private var savedata: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        Log.d(TAG, "onCreate called")

        binding.btn1.setOnClickListener {
            val intent = Intent(this, MainActivity2::class.java)
            intent.putExtra("string",binding.Edit.text.toString())
            startActivity(intent)
        }
    }

    override fun onResume() {
        super.onResume()
        binding.Edit.setText(savedata)
        Log.d(TAG, "onResume called")
    }

    override fun onPause() {
        super.onPause()
        savedata = binding.Edit.text.toString()
        Log.d(TAG, "onPause called")
    }

    override fun onRestart() {
        super.onRestart()
        val dlg = AlertDialog.Builder(this)
        dlg.setTitle("Message")
        dlg.setMessage("다시 작성 하시겠습니까?")
        dlg.setIcon(R.drawable.ic_launcher_background)
        dlg.setPositiveButton("예", DialogInterface.OnClickListener(){dialog,id ->
            savedata = ""
            binding.Edit.setText(savedata)
        })
        dlg.setNegativeButton("아니오", DialogInterface.OnClickListener(){dialog,id ->
            null
        })
        dlg.create()
        dlg.show()
        Log.d(TAG, "onRestart called")
    }

    override fun onStop() {
        super.onStop()
        Toast.makeText(this, "다음으로", Toast.LENGTH_SHORT).show()

        Log.d(TAG, "onStop called")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart called")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "onDestroy called")
    }

}
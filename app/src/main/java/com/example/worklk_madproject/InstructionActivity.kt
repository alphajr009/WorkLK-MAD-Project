package com.example.worklk_madproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class InstructionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.setuppage1)

        val skip = findViewById<TextView>(R.id.textView4)
        skip.setOnClickListener{
            startActivity(Intent(this, LoginActivity::class.java))
        }

        val button = findViewById<TextView>(R.id.button)
        button.setOnClickListener{
            startActivity(Intent(this, InstructionActivity2::class.java))
        }
    }

    override fun onStop() {
        super.onStop()

        val sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putBoolean("isFirstLaunch", false)
        editor.apply()
    }

}

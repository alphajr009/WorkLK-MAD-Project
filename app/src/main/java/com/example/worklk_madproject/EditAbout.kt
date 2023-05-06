package com.example.worklk_madproject

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class EditAbout : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_about)


        val button = findViewById<TextView>(R.id.button)
        button.setOnClickListener{
            startActivity(Intent(this, InstructionActivity2::class.java))
        }

    }

}
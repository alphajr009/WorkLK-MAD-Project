package com.example.worklk_madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner

class SignUpActivitySucess : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signupsucesspopup)



        val button = findViewById<Button>(R.id.buttonsign)
        button.setOnClickListener{
            startActivity(Intent(this, JobSeekerRegisterActivity::class.java))
        }
    }
}
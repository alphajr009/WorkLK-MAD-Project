package com.example.worklk_madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup1)

        val signin = findViewById<TextView>(R.id.textView)
        signin.setOnClickListener{
            startActivity(Intent(this, JobSeekerRegisterActivity::class.java))
        }


    }
}
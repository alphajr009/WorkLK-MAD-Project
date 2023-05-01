package com.example.worklk_madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.WindowManager
import android.widget.TextView

class JobSeekerRegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        val signup = findViewById<TextView>(R.id.textView)
        signup.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

    }
}
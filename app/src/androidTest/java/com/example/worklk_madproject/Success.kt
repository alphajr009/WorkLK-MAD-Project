package com.example.Madjobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class Success : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_success_company)
    }
    override fun onBackPressed() {
        // Do nothing to disable the back button
    }

    fun backToHome(view: View) {
        val intent = Intent(this,MainActivity::class.java)
        startActivity(intent)
    }


}
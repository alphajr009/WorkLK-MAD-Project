package com.example.Madjobs

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun myFunction(view: View) {
        val intent = Intent(this,AddFirstActivity::class.java)
        startActivity(intent)
    }

    fun ViewData(view: View) {
        val intent = Intent(this,FetchingActivity::class.java)
        startActivity(intent)
    }
}
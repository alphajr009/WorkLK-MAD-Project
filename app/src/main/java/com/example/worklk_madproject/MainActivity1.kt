package com.example.worklk_madproject

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.widget.Button
//import com.example.financeManagement.R

class MainActivity1 : AppCompatActivity() {

    private lateinit var btnInsertData: Button
    private lateinit var btnFetchData: Button


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        btnInsertData = findViewById(R.id.btnInsertData)
        btnFetchData  = findViewById(R.id.btnFetchData)


        btnInsertData.setOnClickListener{
            val intent = Intent(this, InsertionActivity::class.java)
            startActivity(intent)
        }

        btnFetchData.setOnClickListener{
            val intent = Intent(this, FetchingActivity::class.java)
            startActivity(intent)
        }


//        val firebase : DatabaseReference = FirebaseDatabase.getInstance().reference
        supportActionBar?.hide();
    }
}
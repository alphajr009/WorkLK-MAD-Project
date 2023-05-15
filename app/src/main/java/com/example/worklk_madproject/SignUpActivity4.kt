package com.example.worklk_madproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*

class SignUpActivity4 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup4)

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val mobile = intent.getStringExtra("mobile")
        val password = intent.getStringExtra("password")
        val confirmPassword = intent.getStringExtra("confirmPassword")
        val gender = intent.getStringExtra("gender")
        val birthDay = intent.getStringExtra("birthDay")
        val birthMonth = intent.getStringExtra("birthMonth")
        val birthYear = intent.getStringExtra("birthYear")
        val province = intent.getStringExtra("province")
        val district = intent.getStringExtra("district")
        val city = intent.getStringExtra("city")
        val postalCode = intent.getStringExtra("postalCode")


        val schoolUniEditText = findViewById<EditText>(R.id.editTextScholUni)
        val locationEditText = findViewById<EditText>(R.id.editTextlocation)
        val degreeEditText = findViewById<EditText>(R.id.editTextDegree)
        val startDateEditText = findViewById<EditText>(R.id.editTextStartdate)
        val endDateEditText = findViewById<EditText>(R.id.editTextEndDate)



        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val schoolUni = schoolUniEditText.text.toString()
            val location = locationEditText.text.toString()
            val degree = degreeEditText.text.toString()
            val startDate = startDateEditText.text.toString()
            val endDate = endDateEditText.text.toString()

            if (schoolUni.isEmpty() || location.isEmpty() || degree.isEmpty() || startDate.isEmpty() || endDate.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SignUpActivity5::class.java)

                // Pass data from previous activities
                intent.putExtra("name", name)
                intent.putExtra("email", email)
                intent.putExtra("mobile", mobile)
                intent.putExtra("password", password)
                intent.putExtra("confirmPassword", confirmPassword)
                intent.putExtra("gender", gender)
                intent.putExtra("birthDay", birthDay)
                intent.putExtra("birthMonth", birthMonth)
                intent.putExtra("birthYear", birthYear)
                intent.putExtra("province", province)
                intent.putExtra("district", district)
                intent.putExtra("city", city)
                intent.putExtra("postalCode", postalCode)

                // Pass data from SignUpActivity4.kt
                intent.putExtra("schoolUni", schoolUni)
                intent.putExtra("location", location)
                intent.putExtra("degree", degree)
                intent.putExtra("startDate", startDate)
                intent.putExtra("endDate", endDate)

                startActivity(intent)
            }
        }

    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}
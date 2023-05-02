package com.example.worklk_madproject

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast

class SignUpActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup2)

        // Get the data from SignUpActivity
        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val mobile = intent.getStringExtra("mobile")
        val password = intent.getStringExtra("password")
        val confirmPassword = intent.getStringExtra("confirmPassword")

        // Get references to the EditText fields and Spinner
        val spinnerGender = findViewById<Spinner>(R.id.spinnerGender)
        val editTextBirthDay = findViewById<EditText>(R.id.editTextBirthDay)
        val editTextBirthMonth = findViewById<EditText>(R.id.editTextBirthMonth)
        val editTextBirthYear = findViewById<EditText>(R.id.editTextBirthYear)

        // Set up the Spinner adapter
        ArrayAdapter.createFromResource(
            this,
            R.array.gender_options,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerGender.adapter = adapter
        }


        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // Get the entered values
            val gender = spinnerGender.selectedItem.toString()
            val birthDay = editTextBirthDay.text.toString()
            val birthMonth = editTextBirthMonth.text.toString()
            val birthYear = editTextBirthYear.text.toString()

            // Check if all fields are filled
            if (gender.isNotEmpty() && birthDay.isNotEmpty() && birthMonth.isNotEmpty() && birthYear.isNotEmpty()) {
                // Create an Intent and put the entered values and the values from the previous activity as extras
                val intent = Intent(this, SignUpActivity3::class.java)
                intent.putExtra("name", name)
                intent.putExtra("email", email)
                intent.putExtra("mobile", mobile)
                intent.putExtra("password", password)
                intent.putExtra("confirmPassword", confirmPassword)
                intent.putExtra("gender", gender)
                intent.putExtra("birthDay", birthDay)
                intent.putExtra("birthMonth", birthMonth)
                intent.putExtra("birthYear", birthYear)

                // Start SignUpActivity3 with the modified Intent
                startActivity(intent)
            } else {
                // Show a warning that all fields must be filled
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }
}

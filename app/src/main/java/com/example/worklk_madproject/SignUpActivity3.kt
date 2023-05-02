package com.example.worklk_madproject

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class SignUpActivity3 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup3)

        // Receiving data from SignUpActivity2.kt
        val previousIntent = intent
        val name = previousIntent.getStringExtra("name")
        val email = previousIntent.getStringExtra("email")
        val mobile = previousIntent.getStringExtra("mobile")
        val password = previousIntent.getStringExtra("password")
        val confirmPassword = previousIntent.getStringExtra("confirmPassword")
        val gender = previousIntent.getStringExtra("gender")
        val birthDay = previousIntent.getStringExtra("birthDay")
        val birthMonth = previousIntent.getStringExtra("birthMonth")
        val birthYear = previousIntent.getStringExtra("birthYear")

        // Set up the province Spinner
        val provinceSpinner = findViewById<Spinner>(R.id.provinceSpinner)
        val provinceAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.province_options,
            android.R.layout.simple_spinner_item
        )
        provinceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        provinceSpinner.adapter = provinceAdapter

        // Set up the district Spinner
        val districtSpinner = findViewById<Spinner>(R.id.districtSpinner)
        val districtCentralAdapter = ArrayAdapter.createFromResource(
            this,
            R.array.districtCentral_options,
            android.R.layout.simple_spinner_item
        )
        districtCentralAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        districtSpinner.adapter = districtCentralAdapter

        // Find city and postal code EditText views
        val cityEditText = findViewById<EditText>(R.id.city)
        val postalCodeEditText = findViewById<EditText>(R.id.postal_code)

        // Handle province spinner item selection
        provinceSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parent: AdapterView<*>?,
                view: View?,
                position: Int,
                id: Long
            ) {
                val selectedProvince = parent?.getItemAtPosition(position).toString()

                // Update district spinner based on selected province
                val districtArrayId = when (selectedProvince) {
                    "Central" -> R.array.districtCentral_options
                    "Eastern" -> R.array.districtEastern_options
                    "North Central" -> R.array.districtNorthCentral_options
                    "North Western" -> R.array.districtNorthWestern_options
                    "Northern" -> R.array.districtNorthern_options
                    "Sabaragamuwa" -> R.array.districtSabaragamuwa_options
                    "Southern" -> R.array.districtSouthern_options
                    "Uva" -> R.array.districtUva_options
                    "Western" -> R.array.districtWestern_options
                    else -> R.array.districtCentral_options
                }

                val districtAdapter = ArrayAdapter.createFromResource(
                    this@SignUpActivity3,
                    districtArrayId,
                    android.R.layout.simple_spinner_item
                )
                districtAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                districtSpinner.adapter = districtAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // Do nothing
            }
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val province = provinceSpinner.selectedItem.toString()
            val district = districtSpinner.selectedItem.toString()
            val city = cityEditText.text.toString()
            val postalCode = postalCodeEditText.text.toString()

            if (city.isEmpty() || postalCode.isEmpty()) {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            } else {
                val intent = Intent(this, SignUpActivity4::class.java)

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

                // Pass data from SignUpActivity3.kt
                intent.putExtra("province", province)
                intent.putExtra("district", district)
                intent.putExtra("city", city)
                intent.putExtra("postalCode", postalCode)

                startActivity(intent)
            }
        }
    }
}

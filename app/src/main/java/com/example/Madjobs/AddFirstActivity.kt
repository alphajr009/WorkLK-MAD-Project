package com.example.Madjobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class AddFirstActivity : AppCompatActivity() {

    private lateinit var comName: EditText
    private lateinit var comCountry: String
    private lateinit var comProvince: EditText
    private lateinit var comDistric: EditText
    private lateinit var comAddress: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_first)
        val countries = arrayOf("Select country","Sri lanka", "India", "Pakistan", "Nepal", "Bangladesh")
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, countries)

        val spinner = findViewById<Spinner>(R.id.country_spinner)
        spinner.adapter = adapter


        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                 comCountry = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Do nothing
            }
        }

        comName = findViewById(R.id.name)
        comProvince = findViewById(R.id.province)
        comDistric = findViewById(R.id.district)
        comAddress = findViewById(R.id.address)

    }

    fun goSecondStep(view: View) {
        val name = comName.text.toString()
        val province = comProvince.text.toString()
        val district = comDistric.text.toString()
        val address = comAddress.text.toString()

        if (comCountry.isNotEmpty() && comCountry != "Select country"  && province.isNotEmpty() && district.isNotEmpty() && name.isNotEmpty() && address.isNotEmpty()){
            val intent = Intent(this,AddSecondActivity::class.java)
            intent.putExtra("name", name)
            intent.putExtra("country", comCountry)
            intent.putExtra("province", province)
            intent.putExtra("district", district)
            intent.putExtra("address", address)
            startActivity(intent)
        } else{
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }

    }
}



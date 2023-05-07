package com.example.Madjobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import org.checkerframework.checker.units.qual.C

class AddSecondActivity : AppCompatActivity() {

    private lateinit var comContact: EditText
    private lateinit var comType: EditText
    private lateinit var comRegNumber: EditText
    private lateinit var comRegDate: EditText

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_second)


        comContact = findViewById(R.id.contactNumber)
        comType = findViewById(R.id.businessType)
        comRegNumber = findViewById(R.id.comRegNumber)
        comRegDate = findViewById(R.id.comRegDate)
        dbRef = FirebaseDatabase.getInstance().getReference("company")


    }
    fun addRecord(view: View) {
        val name = intent.getStringExtra("name")
        val country = intent.getStringExtra("country")
        val province = intent.getStringExtra("province")
        val district = intent.getStringExtra("district")
        val address = intent.getStringExtra("address")
        val contact = comContact.text.toString()
        val type = comType.text.toString()
        val reqNumber = comRegNumber.text.toString()
        val reqDate = comRegDate.text.toString()

        if (contact.isNotEmpty() && type.isNotEmpty() && reqNumber.isNotEmpty() && reqDate.isNotEmpty() ){
            val intent = Intent(this,Success::class.java)
            val comId = dbRef.push().key!!

            val company = ComanyModel(
                comId = comId ?: "",
                name = name ?: "",
                country = country ?: "",
                province = province ?: "",
                distric = district ?: "",
                address = address ?: "",
                mobile = contact ,
                type = type,
                regNumber = reqNumber,
                regDate = reqDate

            )

            dbRef.child(comId).setValue(company)
                .addOnCompleteListener {
                    Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
            startActivity(intent)
        } else{
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }



}
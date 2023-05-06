package com.example.avishkajobmanagement.activities

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.avishkajobmanagement.models.JobManagement
import com.example.avishkajobmanagement.R
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity : AppCompatActivity() {

    private lateinit var etJobTitle: EditText
    private lateinit var etContactNo: EditText
    private lateinit var etDescription: EditText
    private lateinit var btnSaveData: Button

    private lateinit var dbRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etJobTitle = findViewById(R.id.etJobTitle)
        etContactNo = findViewById(R.id.etContactNo)
        etDescription = findViewById(R.id.etDescription)
        btnSaveData = findViewById(R.id.btnSave)

        dbRef = FirebaseDatabase.getInstance().getReference("JobManagement")

        btnSaveData.setOnClickListener {
            saveEmployeeData()
        }

        supportActionBar?.hide();

    }

    private fun saveEmployeeData() {

        //getting values
        val jobTitle = etJobTitle.text.toString()
        val contactNo = etContactNo.text.toString()
        val description = etDescription.text.toString()

        if (jobTitle.isEmpty()) {
            etJobTitle.error = "Please enter Job Title"
        }
        if (contactNo.isEmpty()) {
            etContactNo.error = "Please enter Contact Number"
        }
        if (description.isEmpty()) {
            etDescription.error = "Please enter Description"
        }

        val jobId = dbRef.push().key!!

        val job = JobManagement(jobId ,jobTitle, contactNo, description )

        dbRef.child(jobId).setValue(job)
            .addOnCompleteListener {
                Toast.makeText(this, "Data inserted successfully", Toast.LENGTH_LONG).show()

                etJobTitle.text.clear()
                etContactNo.text.clear()
                etDescription.text.clear()


            }.addOnFailureListener { err ->
                Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
            }

    }

}
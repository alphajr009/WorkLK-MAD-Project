package com.example.worklk_madproject

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class InsertionActivity  : AppCompatActivity(){

    private lateinit var dbRef: DatabaseReference
    private lateinit var etEmpName: EditText
    private lateinit var etEmpAge: EditText
    private lateinit var etEmpSalary: EditText
    private lateinit var btnSaveData: Button

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_insertion)

        etEmpName = findViewById(R.id.etEmpName)
        etEmpAge = findViewById(R.id.etEmpAge)
        etEmpSalary = findViewById(R.id.etEmpSalary)
        btnSaveData = findViewById(R.id.btnSaveData)

        dbRef = FirebaseDatabase.getInstance().getReference("FinanceManagement")

        btnSaveData.setOnClickListener{
            saveEmployeeData()
        }
        supportActionBar?.hide();
    }
    private fun saveEmployeeData(){
        val policy = etEmpName.text.toString()
        val adminId = etEmpAge.text.toString()
        val description= etEmpSalary.text.toString()

        if(policy.isEmpty()){
            etEmpName.error = "Please enter policy"
        }
        if(adminId.isEmpty()){
            etEmpAge.error = "Please enter Admin ID"
        }
        if(description.isEmpty()){
            etEmpSalary.error = "Please enter Description"
        }

        val empId = dbRef.push().key!!

         val employee = FinanceManagement(empId, policy, adminId, description)

        dbRef.child(empId).setValue(employee)
            .addOnCompleteListener{
                Toast.makeText(this,"Data inserted successfully", Toast.LENGTH_LONG).show()

                etEmpName.text.clear()
                etEmpAge.text.clear()
                etEmpSalary.text.clear()

            }.addOnFailureListener{ err ->
                Toast.makeText( this,"Error ${err.message}", Toast.LENGTH_LONG).show()
            }


    }
}










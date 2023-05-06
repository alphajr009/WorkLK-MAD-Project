package com.example.avishkajobmanagement.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.avishkajobmanagement.R
import com.example.avishkajobmanagement.models.JobManagement
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvJobID: TextView
    private lateinit var tvJobTitle: TextView
    private lateinit var tvContactNo: TextView
    private lateinit var tvDescription: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("jobId").toString(),
                intent.getStringExtra("jobTitle").toString()
            )
        }

        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("jobId").toString()
            )
        }

        supportActionBar?.hide();

    }

    private fun initView() {
        tvJobID = findViewById(R.id.tvJobID)
        tvJobTitle = findViewById(R.id.tvJobTitle)
        tvContactNo = findViewById(R.id.tvContactNo)
        tvDescription = findViewById(R.id.tvDescription)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvJobID.text = intent.getStringExtra("jobId")
        tvJobTitle.text = intent.getStringExtra("jobTitle")
        tvContactNo.text = intent.getStringExtra("contactNo")
        tvDescription.text = intent.getStringExtra("description")

    }

    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("JobManagement").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Job data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }

    private fun openUpdateDialog(
        jobId: String,
        jobTitle: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etJobTitle = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etContactNo = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etDescription = mDialogView.findViewById<EditText>(R.id.etEmpSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etJobTitle.setText(intent.getStringExtra("jobTitle").toString())
        etContactNo.setText(intent.getStringExtra("contactNo").toString())
        etDescription.setText(intent.getStringExtra("description").toString())

        mDialog.setTitle("Updating $jobTitle Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                jobId,
                etJobTitle.text.toString(),
                etContactNo.text.toString(),
                etDescription.text.toString()
            )

            Toast.makeText(applicationContext, "Job Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvJobTitle.text = etJobTitle.text.toString()
            tvContactNo.text = etContactNo.text.toString()
            tvDescription.text = etDescription.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        title: String,
        contact: String,
        description: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("JobManagement").child(id)
        val empInfo = JobManagement(id, title, contact, description)
        dbRef.setValue(empInfo)
    }
}
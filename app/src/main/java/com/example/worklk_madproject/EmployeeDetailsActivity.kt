package com.example.worklk_madproject

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.worklk_madproject.FinanceManagement
import com.example.worklk_madproject.R
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.FirebaseDatabase

class EmployeeDetailsActivity : AppCompatActivity() {

    private lateinit var tvEmpId: TextView
    private lateinit var tvEmpName: TextView
    private lateinit var tvEmpAge: TextView
    private lateinit var tvEmpSalary: TextView
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_employee_details)

        initView()
        setValuesToViews()

        btnUpdate.setOnClickListener {
            openUpdateDialog(
                intent.getStringExtra("policyId").toString(),
                intent.getStringExtra("policy").toString()
            )
        }
        btnDelete.setOnClickListener {
            deleteRecord(
                intent.getStringExtra("policyId").toString()
            )
        }
    }
    private fun deleteRecord(
        id: String
    ){
        val dbRef = FirebaseDatabase.getInstance().getReference("FinanceManagement").child(id)
        val mTask = dbRef.removeValue()

        mTask.addOnSuccessListener {
            Toast.makeText(this, "Policy data deleted", Toast.LENGTH_LONG).show()

            val intent = Intent(this, FetchingActivity::class.java)
            finish()
            startActivity(intent)
        }.addOnFailureListener{ error ->
            Toast.makeText(this, "Deleting Err ${error.message}", Toast.LENGTH_LONG).show()
        }
    }
    private fun initView() {
        tvEmpId = findViewById(R.id.tvEmpId)
        tvEmpName = findViewById(R.id.tvEmpName)
        tvEmpAge = findViewById(R.id.tvEmpAge)
        tvEmpSalary = findViewById(R.id.tvEmpSalary)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        tvEmpId.text = intent.getStringExtra("policyId")
        tvEmpName.text = intent.getStringExtra("policy")
        tvEmpAge.text = intent.getStringExtra("adminId")
        tvEmpSalary.text = intent.getStringExtra("description")

    }

    private fun openUpdateDialog(
        policyId: String,
        policy: String
    ) {
        val mDialog = AlertDialog.Builder(this)
        val inflater = layoutInflater
        val mDialogView = inflater.inflate(R.layout.update_dialog, null)

        mDialog.setView(mDialogView)

        val etpolicy = mDialogView.findViewById<EditText>(R.id.etEmpName)
        val etadminId = mDialogView.findViewById<EditText>(R.id.etEmpAge)
        val etdescription = mDialogView.findViewById<EditText>(R.id.etEmpSalary)

        val btnUpdateData = mDialogView.findViewById<Button>(R.id.btnUpdateData)

        etpolicy.setText(intent.getStringExtra("policy").toString())
        etadminId.setText(intent.getStringExtra("adminId").toString())
        etdescription.setText(intent.getStringExtra("description").toString())

        mDialog.setTitle("Updating $policy Record")

        val alertDialog = mDialog.create()
        alertDialog.show()

        btnUpdateData.setOnClickListener {
            updateEmpData(
                policyId,
                etpolicy.text.toString(),
                etadminId.text.toString(),
                etdescription.text.toString()
            )

            Toast.makeText(applicationContext, "Policy Data Updated", Toast.LENGTH_LONG).show()

            //we are setting updated data to our textviews
            tvEmpName.text = etpolicy.text.toString()
            tvEmpAge.text = etadminId.text.toString()
            tvEmpSalary.text = etdescription.text.toString()

            alertDialog.dismiss()
        }
    }

    private fun updateEmpData(
        id: String,
        policy: String,
        adminId: String,
        description: String
    ) {
        val dbRef = FirebaseDatabase.getInstance().getReference("FinanceManagement").child(id)
        val empInfo = FinanceManagement(id, policy, adminId, description)
        dbRef.setValue(empInfo)
    }

}




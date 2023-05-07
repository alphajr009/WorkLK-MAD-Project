package com.example.Madjobs

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class CompanyDetailsActivity : AppCompatActivity(){
    private lateinit var cName: EditText
    private lateinit var cCountry: EditText
    private lateinit var cProvince: EditText
    private lateinit var cDistrict: EditText
    private lateinit var cAddress: EditText
    private lateinit var cType: EditText
    private lateinit var cMobile: EditText
    private lateinit var btnUpdate: Button
    private lateinit var btnDelete: Button
    private lateinit var dbRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_company_details)
        dbRef = FirebaseDatabase.getInstance().getReference("company")
        initView()
        setValuesToViews()

    }

    private fun initView() {
        cName = findViewById(R.id.editName)
        cCountry = findViewById(R.id.editCountry)
        cProvince = findViewById(R.id.editProvince)
        cAddress = findViewById(R.id.editAddress)
        cDistrict = findViewById(R.id.editDistric)
        cType = findViewById(R.id.editType)
        cMobile = findViewById(R.id.editMobile)

        btnUpdate = findViewById(R.id.btnUpdate)
        btnDelete = findViewById(R.id.btnDelete)
    }

    private fun setValuesToViews() {
        cName.setText(intent.getStringExtra("name"))
        cCountry.setText(intent.getStringExtra("country"))
        cProvince.setText(intent.getStringExtra("province"))
        cDistrict.setText(intent.getStringExtra("district"))
        cAddress.setText(intent.getStringExtra("address"))
        cType.setText(intent.getStringExtra("type"))
        cMobile.setText(intent.getStringExtra("mobile"))

    }

    fun updateCompany(view: View) {
        val name = cName.text.toString()
        val country = cCountry.text.toString()
        val province = cProvince.text.toString()
        val district = cDistrict.text.toString()
        val address = cAddress.text.toString()
        val contact = cMobile.text.toString()
        val type = cType.text.toString()
        val reqNumber = intent.getStringExtra("regNumber").toString()
        val reqDate = intent.getStringExtra("regDate").toString()
        val comId = intent.getStringExtra("id").toString()
        if (contact.isNotEmpty() && type.isNotEmpty() && reqNumber.isNotEmpty() && reqDate.isNotEmpty() ){



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
                    Toast.makeText(this, "Company Data Updated successfully", Toast.LENGTH_LONG).show()

                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }

        } else{
            Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
        }
    }
    fun deleteCompany(view: View) {
        val comId = intent.getStringExtra("id").toString()
        if (comId.isNotEmpty()){
            val intent = Intent(this,FetchingActivity::class.java)
            dbRef.child(comId).setValue(null)
                .addOnCompleteListener {
                    Toast.makeText(this, "Company Removed successfully", Toast.LENGTH_LONG).show()
                startActivity(intent)
                }.addOnFailureListener { err ->
                    Toast.makeText(this, "Error ${err.message}", Toast.LENGTH_LONG).show()
                }
        }

    }

}
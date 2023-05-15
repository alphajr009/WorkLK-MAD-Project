package com.example.worklk_madproject

import android.content.ContentValues.TAG
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

class SignUpActivity5 : AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup5)

        auth = FirebaseAuth.getInstance()
        db = Firebase.firestore

        val name = intent.getStringExtra("name")
        val email = intent.getStringExtra("email")
        val mobile = intent.getStringExtra("mobile")
        val password = intent.getStringExtra("password")
        val gender = intent.getStringExtra("gender")
        val birthDay = intent.getStringExtra("birthDay")
        val birthMonth = intent.getStringExtra("birthMonth")
        val birthYear = intent.getStringExtra("birthYear")
        val province = intent.getStringExtra("province")
        val district = intent.getStringExtra("district")
        val city = intent.getStringExtra("city")
        val adress = intent.getStringExtra("postalCode")
        val schoolUni = intent.getStringExtra("schoolUni")
        val location = intent.getStringExtra("location")
        val degree = intent.getStringExtra("degree")
        val startDate = intent.getStringExtra("startDate")
        val endDate = intent.getStringExtra("endDate")

        val jobtitleedittext = findViewById<EditText>(R.id.editTextJobtitle)
        val companynameedittext = findViewById<EditText>(R.id.editTextCompany)
        val companylocationedittext = findViewById<EditText>(R.id.editcomapnylocation)
        val companystartdateedittext = findViewById<EditText>(R.id.editCompanyStartdate)
        val companyenddateedittext = findViewById<EditText>(R.id.editCompmayEndDate)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            val user = User(
                name = name ?: "",
                email = email ?: "",
                mobile = mobile ?: "",
                password = password ?: "",
                gender = gender ?: "",
                birthDay = birthDay ?: "",
                birthMonth = birthMonth ?: "",
                birthYear = birthYear ?: "",
                province = province ?: "",
                district = district ?: "",
                city = city ?: "",
                adress = adress ?: "",
                schoolUni = schoolUni ?: "",
                location = location ?: "",
                degree = degree ?: "",
                startDate = startDate ?: "",
                endDate = endDate ?: "",
                jobTitle = jobtitleedittext.text.toString(),
                companyName = companynameedittext.text.toString(),
                companyLocation = companylocationedittext.text.toString(),
                companyStartDate = companystartdateedittext.text.toString(),
                companyEndDate = companyenddateedittext.text.toString()
            )

            if (email != null && password != null) {
                createUserWithEmailAndPassword(user, email, password)
            } else {
                Toast.makeText(this, "Invalid email or password", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun createUserWithEmailAndPassword(user: User, email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    saveUserToFirestore(user)
                } else {
                    Toast.makeText(this, "Registration failed: ${task.exception?.localizedMessage}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun saveUserToFirestore(user: User) {
        db.collection("users")
            .add(user)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "User added with ID: ${documentReference.id}")
                startActivity(Intent(this, SignUpActivitySucess::class.java))
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding user", e)
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


data class User(
    val name: String,
    val email: String,
    val mobile: String,
    val password: String,
    val gender: String,
    val birthDay: String,
    val birthMonth: String,
    val birthYear: String,
    val province: String,
    val district: String,
    val city: String,
    val adress: String,
    val schoolUni: String,
    val location: String,
    val degree: String,
    val startDate: String,
    val endDate: String,
    val jobTitle: String,
    val companyName: String,
    val companyLocation: String,
    val companyStartDate: String,
    val companyEndDate: String
)

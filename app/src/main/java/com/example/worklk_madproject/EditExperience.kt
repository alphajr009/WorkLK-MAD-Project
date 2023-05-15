package com.example.worklk_madproject

import SharedPrefManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditExperience: AppCompatActivity() {
    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_experience)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()


        val JobTitle = findViewById<EditText>(R.id.JobTitle)
        val Company = findViewById<EditText>(R.id.Company)
        val Location = findViewById<EditText>(R.id.Location)
        val StartDate = findViewById<EditText>(R.id.StartDate)
        val EndDate = findViewById<EditText>(R.id.EndDate)

        val sharedPrefManager = SharedPrefManager(this)
        val userId = sharedPrefManager.getUserId()

        val saveButton = findViewById<Button>(R.id.button)
        val imageView = findViewById<ImageView>(R.id.imageView14)


        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        Log.d("EditExperience", "Document data: ${document.data}")
                        JobTitle.setText(document.getString("jobTitle") ?: "")
                        Company.setText(document.getString("companyName") ?: "")
                        Location.setText(document.getString("companyLocation") ?: "")
                        StartDate.setText(document.getString("companyStartDate") ?: "")
                        EndDate.setText(document.getString("companyEndDate") ?: "")

                    } else {
                        Log.d("EditExperience", "User ID: $userId")

                        Log.d("EditExperience", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("EditExperience", "Error fetching user data", e)
                }
        }

        imageView.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val updatedData = hashMapOf<String, Any>(
                "jobTitle" to JobTitle.text.toString(),
                "companyName" to Company.text.toString(),
                "companyLocation" to Location.text.toString(),
                "companyStartDate" to StartDate.text.toString(),
                "companyEndDate" to EndDate.text.toString(),
            )

            if (userId != null) {
                firestore.collection("users").document(userId)
                    .update(updatedData)
                    .addOnSuccessListener {
                        Toast.makeText(this, "Profile updated successfully", Toast.LENGTH_SHORT).show()
                        finish()
                    }
                    .addOnFailureListener { e ->
                        Toast.makeText(this, "Error updating profile: ${e.message}", Toast.LENGTH_SHORT).show()
                    }
            }
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
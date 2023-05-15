package com.example.worklk_madproject

import SharedPrefManager
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class EditEducation: AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_education)


        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()

        val imageView = findViewById<ImageView>(R.id.imageView142)
        val saveButton = findViewById<Button>(R.id.button)

        val SchoolorUni = findViewById<EditText>(R.id.SchoolorUni)
        val Location = findViewById<EditText>(R.id.Location)
        val Degree = findViewById<EditText>(R.id.Degree)
        val StartDate = findViewById<EditText>(R.id.StartDate)
        val EndDate = findViewById<EditText>(R.id.EndDate)

        val sharedPrefManager = SharedPrefManager(this)
        val userId = sharedPrefManager.getUserId()

        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        Log.d("EditEducation", "Document data: ${document.data}")
                        SchoolorUni.setText(document.getString("schoolUni") ?: "")
                        Location.setText(document.getString("location") ?: "")
                        Degree.setText(document.getString("degree") ?: "")
                        StartDate.setText(document.getString("startDate") ?: "")
                        EndDate.setText(document.getString("endDate") ?: "")

                    } else {
                        Log.d("EditEducation", "User ID: $userId")

                        Log.d("EditEducation", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("EditEducation", "Error fetching user data", e)
                }
        }



        imageView.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val updatedData = hashMapOf<String, Any>(
                "schoolUni" to SchoolorUni.text.toString(),
                "location" to Location.text.toString(),
                "degree" to Degree.text.toString(),
                "startDate" to StartDate.text.toString(),
                "endDate" to EndDate.text.toString(),
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
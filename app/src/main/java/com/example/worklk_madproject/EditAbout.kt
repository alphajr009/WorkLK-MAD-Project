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

class EditAbout : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var firestore: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.edit_about)

        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()



        val imageView = findViewById<ImageView>(R.id.imageView14)
        val saveButton = findViewById<Button>(R.id.button)

        val name = findViewById<EditText>(R.id.Name)
        val phone = findViewById<EditText>(R.id.Phone)
        val gender = findViewById<EditText>(R.id.Gender)
        val dobDay = findViewById<EditText>(R.id.DOBDay)
        val dobMonth = findViewById<EditText>(R.id.DOBMonth)
        val dobYear = findViewById<EditText>(R.id.DOBYear)
        val addressLine = findViewById<EditText>(R.id.AddressLine)
        val city = findViewById<EditText>(R.id.City)
        val district = findViewById<EditText>(R.id.District)

        val sharedPrefManager = SharedPrefManager(this)
        val userId = sharedPrefManager.getUserId()

        if (userId != null) {
            firestore.collection("users").document(userId)
                .get()
                .addOnSuccessListener { document ->
                    if (document != null && document.exists()) {
                        Log.d("EditAbout", "Document data: ${document.data}")
                        name.setText(document.getString("name") ?: "")
                        phone.setText(document.getString("mobile") ?: "")
                        gender.setText(document.getString("gender") ?: "")
                        dobDay.setText(document.getString("birthDay") ?: "")
                        dobMonth.setText(document.getString("birthMonth") ?: "")
                        dobYear.setText(document.getString("birthYear") ?: "")
                        addressLine.setText(document.getString("adress") ?: "")
                        city.setText(document.getString("city") ?: "")
                        district.setText(document.getString("district") ?: "")
                    } else {
                        Log.d("EditAbout", "User ID: $userId")

                        Log.d("EditAbout", "No such document")
                    }
                }
                .addOnFailureListener { e ->
                    Log.e("EditAbout", "Error fetching user data", e)
                }
        }



        imageView.setOnClickListener {
            finish()
        }

        saveButton.setOnClickListener {
            val updatedData = hashMapOf<String, Any>(
                "name" to name.text.toString(),
                "mobile" to phone.text.toString(),
                "gender" to gender.text.toString(),
                "birthDay" to dobDay.text.toString(),
                "birthMonth" to dobMonth.text.toString(),
                "birthYear" to dobYear.text.toString(),
                "address" to addressLine.text.toString(),
                "city" to city.text.toString(),
                "district" to district.text.toString()
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

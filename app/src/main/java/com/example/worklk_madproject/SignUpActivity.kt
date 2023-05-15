package com.example.worklk_madproject

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.firebase.firestore.FirebaseFirestore

class SignUpActivity : AppCompatActivity() {
    private val db = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.signup1)

        // Get references to the EditText fields
        val editTextName = findViewById<EditText>(R.id.editTextName)
        val editTextEmail = findViewById<EditText>(R.id.editTextEmail)
        val editTextMobile = findViewById<EditText>(R.id.editTextMobile)
        val editTextPassword = findViewById<EditText>(R.id.editTextPassword)
        val editTextConfirmPassword = findViewById<EditText>(R.id.editTextConfirmPassword)

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            // Get the entered values
            val name = editTextName.text.toString()
            val email = editTextEmail.text.toString()
            val mobile = editTextMobile.text.toString()
            val password = editTextPassword.text.toString()
            val confirmPassword = editTextConfirmPassword.text.toString()

            // Check if all fields are filled
            if (name.isNotEmpty() && email.isNotEmpty() && mobile.isNotEmpty() && password.isNotEmpty() && confirmPassword.isNotEmpty()) {
                // Validate email address
                if (isEmailValid(email)) {
                    // Validate password
                    if (isPasswordValid(password)) {
                        // Check if user already exists
                        checkUserExists(email) { exists ->
                            if (!exists) {
                                // Check if the password and confirm password match
                                if (password == confirmPassword) {
                                    // Create an Intent and put the entered values as extras
                                    val intent = Intent(this, SignUpActivity2::class.java)
                                    intent.putExtra("name", name)
                                    intent.putExtra("email", email)
                                    intent.putExtra("mobile", mobile)
                                    intent.putExtra("password", password)
                                    intent.putExtra("confirmPassword", confirmPassword)

                                    // Start SignUpActivity2 with the modified Intent
                                    startActivity(intent)
                                } else {
                                    // Show a warning that the passwords must match
                                    Toast.makeText(this, "Passwords do not match", Toast.LENGTH_SHORT).show()
                                }
                            } else {
                                Toast.makeText(this, "User already exists, Sign In", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        Toast.makeText(this, "Password must be at least 6 characters", Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                }
            } else {
                // Show a warning that all fields must be filled
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }

        val signin = findViewById<TextView>(R.id.textView)
        signin.setOnClickListener{
            startActivity(Intent(this, JobSeekerRegisterActivity::class.java))
        }
    }

    private fun isEmailValid(email: String): Boolean {
        val emailRegex = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,8}$".toRegex(RegexOption.IGNORE_CASE)
        return emailRegex.matches(email)
    }

    private fun isPasswordValid(password: String): Boolean {
        return password.length >= 6
    }

    private fun checkUserExists(email: String, callback: (Boolean) -> Unit) {
        db.collection("users")
            .whereEqualTo("email", email)
            .get()
            .addOnSuccessListener { querySnapshot ->
                callback(querySnapshot.size() > 0)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this, "Error checking user existence: $exception", Toast.LENGTH_SHORT).show()
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


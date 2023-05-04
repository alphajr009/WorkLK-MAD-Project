package com.example.worklk_madproject

import SharedPrefManager
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore

class JobSeekerRegisterActivity : AppCompatActivity() {

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login2)

        // Check if user is already logged in
        val sharedPrefManager = SharedPrefManager(this)
        if (sharedPrefManager.isLoggedIn()) {
            startActivity(Intent(this, UserLandingPage::class.java))
            finish() // Close the LoginActivity
        }

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        val emailEditText = findViewById<EditText>(R.id.emailEditText)
        val passwordEditText = findViewById<EditText>(R.id.passwordEditText)

        val signup = findViewById<TextView>(R.id.textView)
        signup.setOnClickListener{
            startActivity(Intent(this, SignUpActivity::class.java))
        }

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener{
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                loginUser(email, password)
            } else {
                Toast.makeText(this, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun loginUser(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task: Task<AuthResult> ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    val user = auth.currentUser
                    val userId = user?.uid

                    if (userId != null) {
                        db.collection("users").document(userId)
                            .get()
                            .addOnCompleteListener { userTask: Task<DocumentSnapshot> ->
                                if (userTask.isSuccessful) {
                                    // Save login status
                                    val sharedPrefManager = SharedPrefManager(this)
                                    sharedPrefManager.setLoggedIn(true)

                                    // Navigate to the UserLandingPage
                                    startActivity(Intent(this, UserLandingPage::class.java))
                                } else {
                                    // No matching document, show user not found message
                                    Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                                }
                            }
                    }
                } else {
                    // If sign in fails, display a message to the user.
                    val errorCode = (task.exception as FirebaseAuthException).errorCode
                    when (errorCode) {
                        "ERROR_USER_NOT_FOUND" -> {
                            Toast.makeText(this, "User not found", Toast.LENGTH_SHORT).show()
                        }
                        "ERROR_WRONG_PASSWORD" -> {
                            Toast.makeText(this, "Incorrect Password", Toast.LENGTH_SHORT).show()
                        }
                        else -> {
                            Toast.makeText(this, "Login failed", Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
    }
}

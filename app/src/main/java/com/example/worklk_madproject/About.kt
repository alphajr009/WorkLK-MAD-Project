package com.example.worklk_madproject

import SharedPrefManager
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot

class About : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_about, container, false)

        db = FirebaseFirestore.getInstance()

        // Retrieve user information and update TextViews
        val sharedPrefManager = SharedPrefManager(requireContext())
        val userId = sharedPrefManager.getUserId()
        Log.d("About", "User ID: $userId")
        getUserInfoAndUpdateUI(userId, view)

        return view
    }

    private fun getUserInfoAndUpdateUI(userId: String, view: View) {
        db.collection("users").document(userId)
            .get()
            .addOnSuccessListener { documentSnapshot: DocumentSnapshot ->
                Log.d("About", "Document snapshot: $documentSnapshot")
                val name = documentSnapshot.getString("name") ?: ""
                val mobile = documentSnapshot.getString("mobile") ?: ""
                val gender = documentSnapshot.getString("gender") ?: ""
                val birthDay = documentSnapshot.getString("birthDay") ?: ""
                val birthMonth = documentSnapshot.getString("birthMonth") ?: ""
                val birthYear = documentSnapshot.getString("birthYear") ?: ""
                val adress = documentSnapshot.getString("adress") ?: ""
                val district = documentSnapshot.getString("district") ?: ""
                val city = documentSnapshot.getString("city") ?: ""

                val nameTextView = view.findViewById<TextView>(R.id.Name)
                val phoneTextView = view.findViewById<TextView>(R.id.Phone)
                val genderTextView = view.findViewById<TextView>(R.id.Gender)
                val dateOfBirthTextView = view.findViewById<TextView>(R.id.DateofBirth)
                val addressTextView = view.findViewById<TextView>(R.id.Address)

                nameTextView.text = name
                phoneTextView.text = mobile
                genderTextView.text = gender
                dateOfBirthTextView.text = "$birthDay/$birthMonth/$birthYear"
                addressTextView.text = "$adress,\n$district,\n$city."

            }
            .addOnFailureListener { e ->
                Log.e("About", "Error fetching user data", e)
            }
    }
}

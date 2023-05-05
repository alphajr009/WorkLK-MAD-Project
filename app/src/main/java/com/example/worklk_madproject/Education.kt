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


class Education : Fragment() {

    private lateinit var db: FirebaseFirestore

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_education, container, false)

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
                val schooluni = documentSnapshot.getString("schoolUni") ?: ""
                val location = documentSnapshot.getString("location") ?: ""
                val degree = documentSnapshot.getString("degree") ?: ""
                val startdate = documentSnapshot.getString("startDate") ?: ""
                val enddate = documentSnapshot.getString("endDate") ?: ""


                val nameTextView = view.findViewById<TextView>(R.id.SchoolorUni)
                val locationTextView = view.findViewById<TextView>(R.id.Location)
                val degreeTextView = view.findViewById<TextView>(R.id.Degree)
                val startdateTextView = view.findViewById<TextView>(R.id.StartDate)
                val enddateTextView = view.findViewById<TextView>(R.id.EndDate)

                nameTextView.text = schooluni
                locationTextView.text = location
                degreeTextView.text = degree
                startdateTextView.text = startdate
                enddateTextView.text = enddate

            }
            .addOnFailureListener { e ->
                Log.e("Education", "Error fetching user data", e)
            }
    }

}
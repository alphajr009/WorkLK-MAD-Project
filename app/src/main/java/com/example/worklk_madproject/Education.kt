package com.example.worklk_madproject

import SharedPrefManager
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.DocumentSnapshot

class Education : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var sharedPrefManager: SharedPrefManager
    private var userId: String? = null
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_education, container, false)

        db = FirebaseFirestore.getInstance()

        // Retrieve user information and update TextViews
        sharedPrefManager = SharedPrefManager(requireContext())
        userId = sharedPrefManager.getUserId()
        Log.d("About", "User ID: $userId")

        val button = rootView.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val intent = Intent(requireContext(), EditEducation::class.java)
            startActivity(intent)
        }

        return rootView
    }

    override fun onResume() {
        super.onResume()
        // Refresh data when fragment is resumed
        getUserInfoAndUpdateUI(userId, rootView)
    }

    private fun getUserInfoAndUpdateUI(userId: String?, view: View) {
        if (userId != null) {
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
}

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

class Experience : Fragment() {

    private lateinit var db: FirebaseFirestore
    private lateinit var sharedPrefManager: SharedPrefManager
    private var userId: String? = null
    private lateinit var rootView: View

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_experience, container, false)

        db = FirebaseFirestore.getInstance()

        // Retrieve user information and update TextViews
        sharedPrefManager = SharedPrefManager(requireContext())
        userId = sharedPrefManager.getUserId()
        Log.d("Experience", "User ID: $userId")

        val button = rootView.findViewById<Button>(R.id.button)

        button.setOnClickListener {
            val intent = Intent(requireContext(), EditExperience::class.java)
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
                    Log.d("Experience", "Document snapshot: $documentSnapshot")
                    val jobTitle = documentSnapshot.getString("jobTitle") ?: ""
                    val companyName = documentSnapshot.getString("companyName") ?: ""
                    val companyLocation = documentSnapshot.getString("companyLocation") ?: ""
                    val startdate = documentSnapshot.getString("companyStartDate") ?: ""
                    val enddate = documentSnapshot.getString("companyEndDate") ?: ""

                    val nameTextView = view.findViewById<TextView>(R.id.JobTitle)
                    val companyTextView = view.findViewById<TextView>(R.id.Company)
                    val locationTextView = view.findViewById<TextView>(R.id.Location)
                    val startdateTextView = view.findViewById<TextView>(R.id.StartDate)
                    val enddateTextView = view.findViewById<TextView>(R.id.EndDate)

                    nameTextView.text = jobTitle
                    companyTextView.text = companyName
                    locationTextView.text = companyLocation
                    startdateTextView.text = startdate
                    enddateTextView.text = enddate

                }
                .addOnFailureListener { e ->
                    Log.e("Experience", "Error fetching user data", e)
                }
        }
    }


}

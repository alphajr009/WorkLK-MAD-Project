package com.example.worklk_madproject

import SharedPrefManager
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class Security : Fragment() {

    private lateinit var mAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_security, container, false)

        mAuth = FirebaseAuth.getInstance()

        val floatingActionButton: FloatingActionButton =
            view.findViewById(R.id.floatingActionButton)
        floatingActionButton.setOnClickListener {
            showDeleteAccountDialog()
        }

        return view
    }

    private fun showDeleteAccountDialog() {
        val builder = AlertDialog.Builder(requireContext())

        // Inflate the custom layout
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.delete_account_dialog, null)

        // Set the custom layout to the AlertDialog
        builder.setView(dialogView)

        val confirmationCheckbox = dialogView.findViewById<CheckBox>(R.id.confirmation_checkbox)

        builder.setPositiveButton("Yes") { _, _ ->
            // Retrieve user ID from SharedPrefManager
            val sharedPrefManager = SharedPrefManager(requireContext())
            val userId = sharedPrefManager.getUserId()

            // Call deleteUserAccount() with the retrieved user ID
            deleteUserAccount(userId)
        }.apply {
            // Disable the "Yes" button initially
            setCancelable(false)
        }

        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }

        val alertDialog = builder.create()
        alertDialog.show()

        // Disable the "Yes" button initially
        val positiveButton = alertDialog.getButton(DialogInterface.BUTTON_POSITIVE)
        positiveButton.isEnabled = false

        // Set the initial "Yes" button color to grey
        positiveButton.setTextColor(resources.getColor(R.color.grey))

        // Customize the negative button text color
        val negativeButton = alertDialog.getButton(DialogInterface.BUTTON_NEGATIVE)
        negativeButton.setTextColor(resources.getColor(R.color.background))

        // Add an OnCheckedChangeListener to the checkbox
        confirmationCheckbox.setOnCheckedChangeListener { _, isChecked ->
            positiveButton.isEnabled = isChecked

            // Update the "Yes" button color based on the checkbox state
            if (isChecked) {
                positiveButton.setTextColor(resources.getColor(R.color.background))
            } else {
                positiveButton.setTextColor(resources.getColor(R.color.grey))
            }
        }
    }

    private fun deleteUserAccount(userId: String) {
        val db = FirebaseFirestore.getInstance()
        db.collection("users")
            .document(userId)
            .delete()
            .addOnSuccessListener {
                val sharedPrefManager = SharedPrefManager(requireContext())
                val userUID = sharedPrefManager.getUserUID()

                // Call deleteUserAuthentication() with the retrieved user UID
                deleteUserAuthentication(userUID)

                sharedPrefManager.setLoggedIn(false)

                val intent = Intent(requireActivity(), DivideBegin::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
            .addOnFailureListener { e ->
                Toast.makeText(
                    requireContext(),
                    "Failed to delete user account: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
    }

    private fun deleteUserAuthentication(userUID: String) {
        val user = mAuth.currentUser
        if (user != null && user.uid == userUID) {
            user.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        Toast.makeText(
                            requireContext(),
                            "User account deleted successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Failed to delete user account: ${task.exception?.message}",
                            Toast.LENGTH_LONG
                        ).show()
                    }
                }
        }
    }
}



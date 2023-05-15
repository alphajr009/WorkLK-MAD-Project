package com.example.worklk_madproject

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.GradientDrawable
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.RadioButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout

class LoginActivity : AppCompatActivity() {
    lateinit var radioButton1: RadioButton
    lateinit var radioButton2: RadioButton
    lateinit var radioButton1Container: ConstraintLayout
    lateinit var radioButton2Container: ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_login)

        radioButton1 = findViewById(R.id.radioButton1)
        radioButton2 = findViewById(R.id.radioButton2)
        radioButton1Container = findViewById(R.id.radioButton1Container)
        radioButton2Container = findViewById(R.id.radioButton2Container)
        val continueButton = findViewById<Button>(R.id.button)

        // Set the initial border colors for the containers
        setContainerBorderColor(radioButton1Container, Color.GRAY)
        setContainerBorderColor(radioButton2Container, Color.GRAY)

        // Handle RadioButton clicks
        radioButton1.setOnClickListener {
            radioButton1.isChecked = true
            radioButton2.isChecked = false
            setContainerBorderColor(radioButton1Container, Color.BLUE)
            setContainerBorderColor(radioButton2Container, Color.GRAY)
        }

        radioButton2.setOnClickListener {
            radioButton1.isChecked = false
            radioButton2.isChecked = true
            setContainerBorderColor(radioButton1Container, Color.GRAY)
            setContainerBorderColor(radioButton2Container, Color.BLUE)
        }

        // Handle RadioButton container clicks
        radioButton1Container.setOnClickListener {
            radioButton1.performClick()
        }

        radioButton2Container.setOnClickListener {
            radioButton2.performClick()
        }

        // Handle Continue button click
        continueButton.setOnClickListener {
            if (radioButton1.isChecked) {
                // Navigate to JobSeekerRegisterActivity
                val intent = Intent(this, JobSeekerRegisterActivity::class.java)
                startActivity(intent)
            } else if (radioButton2.isChecked) {
                // Navigate to CompanyRegisterActivity
                val intent = Intent(this, CompanyRegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setContainerBorderColor(container: ConstraintLayout, color: Int) {
        val background = container.background as GradientDrawable
        background.setStroke(3, color)
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (currentFocus != null) {
            val imm = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(currentFocus!!.windowToken, 0)
        }
        return super.dispatchTouchEvent(ev)
    }
}




// ---------------------------------------------------------------------------
// File: CustomerLoginActivity.kt
// Author: IT21189494
// Date Created: 2024.09.20
// Description: BaseActivity
// Version: 1.0.0
// ---------------------------------------------------------------------------

package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ead_ecommerce_app.R
import com.google.android.material.textfield.TextInputEditText

class CustomerLoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customer_login)

        // Adjust for edge insets (for system bars)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Bind views
        val emailEt = findViewById<TextInputEditText>(R.id.emailET)
        val passwordEt = findViewById<TextInputEditText>(R.id.passET)
        val loginButton = findViewById<Button>(R.id.button)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

        // Frontend validation for email format
        loginButton.setOnClickListener {
            val email = emailEt.text.toString()
            if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            } else {
                // Here you'll add code to call your web service for sign-in authentication
                Toast.makeText(this, "Proceed with sign-in", Toast.LENGTH_SHORT).show()
            }
        }

        // Navigate to registration screen
        registerTextView.setOnClickListener {
            val intent = Intent(this, CustomerRegistrationActivity::class.java)
            startActivity(intent)
        }
    }

    // Function to validate email format
    private fun isValidEmail(email: String): Boolean {
        return email.isNotEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }

}
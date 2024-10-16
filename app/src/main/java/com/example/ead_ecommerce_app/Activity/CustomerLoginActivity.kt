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
import com.example.ead_ecommerce_app.Database.UserDatabaseHelper
import com.example.ead_ecommerce_app.R
import com.google.android.material.textfield.TextInputEditText
import com.example.ead_ecommerce_app.Repository.AuthRepository
import com.example.ead_ecommerce_app.Model.User

class CustomerLoginActivity : AppCompatActivity() {

    private val authRepository = AuthRepository()
    private lateinit var dbHelper: UserDatabaseHelper

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

        // Initialize SQLite DB Helper
        dbHelper = UserDatabaseHelper(this)

        // Bind views
        val emailEt = findViewById<TextInputEditText>(R.id.emailET)
        val passwordEt = findViewById<TextInputEditText>(R.id.passET)
        val loginButton = findViewById<Button>(R.id.button)
        val registerTextView = findViewById<TextView>(R.id.registerTextView)

//        // Frontend validation for email format
//        loginButton.setOnClickListener {
//            val email = emailEt.text.toString()
//            if (!isValidEmail(email)) {
//                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
//            } else {
//                // Here you'll add code to call your web service for sign-in authentication
//                Toast.makeText(this, "Proceed with sign-in", Toast.LENGTH_SHORT).show()
//            }
//        }

//        // Frontend validation for email format and login API call
//        loginButton.setOnClickListener {
//            val email = emailEt.text.toString().trim()
//            val password = passwordEt.text.toString().trim()
//
//            if (!isValidEmail(email)) {
//                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
//            } else if (password.isEmpty()) {
//                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
//            } else {
//                // Call the login API
//                authRepository.login(email, password) { isSuccess, message ->
//                    if (isSuccess) {
//
//                        // Save user details to SQLite after successful login
//                        val user = User(
//                            id = loginResponse.id,
//                            username = loginResponse.username,
//                            email = loginResponse.email,
//                            password = password,  // Save plain password here (or hashed)
//                            address = loginResponse.address
//                        )
//                        dbHelper.addUser(user)
//
//
//                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
//                        // Navigate to the customer profile
//                        val intent = Intent(
//                            this,
//                            CustomerProfileActivity::class.java
//                        ) // Replace with actual target activity
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        // Toast.makeText(this, "Login failed: $message", Toast.LENGTH_LONG).show()
//                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }

//        loginButton.setOnClickListener {
//            val email = emailEt.text.toString().trim()
//            val password = passwordEt.text.toString().trim()
//
//            if (!isValidEmail(email)) {
//                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
//            } else if (password.isEmpty()) {
//                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
//            } else {
//                // Call the login API
//                authRepository.login(email, password) { isSuccess, message, loginResponse ->
//                    if (isSuccess && loginResponse != null) {
//                        // Save user details to SQLite after successful login
//                        val user = User(
//                            id = loginResponse.id,
//                            username = loginResponse.username,
//                            email = loginResponse.email,
//                            password = password,  // Save plain password here (or hashed)
//                            address = loginResponse.address
//
//                        )
//                        dbHelper.addUser(user)
//
//                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
//                        // Navigate to the customer profile
//                        val intent = Intent(this, CustomerProfileActivity::class.java)
//                        startActivity(intent)
//                        finish()
//                    } else {
//                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
//                    }
//                }
//            }
//        }

        loginButton.setOnClickListener {
            val email = emailEt.text.toString().trim()
            val password = passwordEt.text.toString().trim()

            if (!isValidEmail(email)) {
                Toast.makeText(this, "Invalid email format", Toast.LENGTH_SHORT).show()
            } else if (password.isEmpty()) {
                Toast.makeText(this, "Password cannot be empty", Toast.LENGTH_SHORT).show()
            } else {
                // Call the login API
                authRepository.login(email, password) { isSuccess, message, loginResponse ->
                    if (isSuccess && loginResponse != null) {
                        // Save user details to SQLite after successful login
                        val user = User(
                            id = loginResponse.id,
                            username = loginResponse.username,
                            email = loginResponse.email,
                            password = password,  // Save plain password here (or hashed)
                            address = loginResponse.address,
                            profile_Picture = loginResponse.profile_Picture ?: ""  // Set as empty string if null
                        )
                        dbHelper.addUser(user)

                        Toast.makeText(this, "Login successful", Toast.LENGTH_SHORT).show()
                        // Navigate to the customer profile
                        val intent = Intent(this, CustomerProfileActivity::class.java)
                        startActivity(intent)
                        finish()
                    } else {
                        Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                    }
                }
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
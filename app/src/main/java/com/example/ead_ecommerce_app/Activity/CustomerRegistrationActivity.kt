package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.ead_ecommerce_app.R
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.widget.AppCompatButton

class CustomerRegistrationActivity : AppCompatActivity() {

    private lateinit var emailLayout: TextInputLayout
    private lateinit var passwordLayout: TextInputLayout
    private lateinit var confirmPasswordLayout: TextInputLayout
    private lateinit var emailEt: TextInputEditText
    private lateinit var passET: TextInputEditText
    private lateinit var confirmPassEt: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_customer_registration)

        // Bind views
        emailLayout = findViewById(R.id.emailLayout)
        passwordLayout = findViewById(R.id.passwordLayout)
        confirmPasswordLayout = findViewById(R.id.confirmPasswordLayout)
        emailEt = findViewById(R.id.emailEt)
        passET = findViewById(R.id.passET)
        confirmPassEt = findViewById(R.id.confirmPassEt)

        // Set edge insets listener
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        findViewById<AppCompatButton>(R.id.button).setOnClickListener {
            if (validateInput()) {
                // Implement API call here
                Toast.makeText(this, "Registration Request Successful\nYour account will be activated by Admin shortly.", Toast.LENGTH_LONG).show()
            }
        }

        // Navigate to login screen
        val loginTextView = findViewById<TextView>(R.id.loginTextView)
        loginTextView.setOnClickListener {
            val intent = Intent(this, CustomerLoginActivity::class.java)
            startActivity(intent)
        }

    }

    private fun validateInput(): Boolean {
        val email = emailEt.text.toString().trim()
        val password = passET.text.toString().trim()
        val confirmPassword = confirmPassEt.text.toString().trim()

        // Validate email
        if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            emailLayout.error = "Invalid email format"
            return false
        } else {
            emailLayout.error = null
        }

        // Validate password strength (e.g., minimum 8 characters)
        if (password.length < 8 || !password.matches(".*[A-Z].*".toRegex()) ||
            !password.matches(".*[0-9].*".toRegex()) ||
            !password.matches(".*[!@#\$%^&*].*".toRegex())) {
            passwordLayout.error =
                "Password must be at least 8 characters, include an uppercase letter, a number, and a special character"
            return false
        } else {
            passwordLayout.error = null
        }

        // Check password confirmation
        if (password != confirmPassword) {
            confirmPasswordLayout.error = "Passwords do not match"
            return false
        } else {
            confirmPasswordLayout.error = null
        }

        return true
    }
}
package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.databinding.ActivityCustomerProfileBinding
import com.google.android.material.textview.MaterialTextView
import com.example.ead_ecommerce_app.Database.UserDatabaseHelper
import com.example.ead_ecommerce_app.Model.User
import com.example.ead_ecommerce_app.Repository.CustomerRepository
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

//class CustomerProfileActivity : AppCompatActivity() {
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        enableEdgeToEdge()
//        setContentView(R.layout.activity_customer_profile)
//        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
//            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
//            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
//            insets
//        }
//    }
//}
class CustomerProfileActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCustomerProfileBinding
    private lateinit var dbHelper: UserDatabaseHelper
    private lateinit var loggedInUser: User
    private val customerRepository = CustomerRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCustomerProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = UserDatabaseHelper(this)

        // Fetch user data from SQLite (user session)
        loggedInUser = dbHelper.getLoggedInUser() ?: run {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Populate the profile with user details
        populateUserProfile()


        // Edit Profile Button
        binding.editProfileLayout.setOnClickListener {
            // Navigate to Edit Profile activity
            val intent = Intent(this, EditProfileActivity::class.java)
            startActivity(intent)
        }

        // Deactivate Account Button
        binding.deactivateAccountButton.setOnClickListener {
            showDeactivateAccountConfirmationDialog()
        }

        // Log Out Button
        binding.logoutButton.setOnClickListener {
            logOutUser()
        }
    }

    // Reload user data when the activity is resumed
    override fun onResume() {
        super.onResume()

        dbHelper = UserDatabaseHelper(this)

        // Re-fetch the logged-in user from the SQLite database to get updated information
        loggedInUser = dbHelper.getLoggedInUser() ?: run {
            Toast.makeText(this, "User not logged in", Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        // Reload user data when returning to this activity
        populateUserProfile()
    }

    // Function to populate the profile with user data
    private fun populateUserProfile() {
        binding.userNameTextView.text = loggedInUser.username
        binding.emailTextView.text = loggedInUser.email

        // Load profile picture if available, else load a default picture
        if (loggedInUser.profile_Picture.isNotEmpty()) {
            Picasso.get().load(loggedInUser.profile_Picture).into(binding.profileImageView)
        } else {
            binding.profileImageView.setImageResource(R.drawable.default_profile_photo)
        }
    }

    // Function to show confirmation dialog for account deactivation
    private fun showDeactivateAccountConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Deactivate Account")
        builder.setMessage("Are you sure you want to Deactivate your account?")
        builder.setPositiveButton("Yes") { dialog, _ ->
            dialog.dismiss()
            deactivateAccount()
        }
        builder.setNegativeButton("No") { dialog, _ ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

//    // Placeholder function for account deactivation logic
//    private fun deactivateAccount() {
//        // In the future, the web service logic will be implemented here
//        Toast.makeText(this, "Account deactivation request sent", Toast.LENGTH_SHORT).show()
//    }

    // Function to call the /api/Customer/deactivate/{id} endpoint to deactivate the account
    private fun deactivateAccount() {
        customerRepository.deactivateCustomer(loggedInUser.id, object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    Toast.makeText(this@CustomerProfileActivity, "Account deactivated", Toast.LENGTH_SHORT).show()
                    logOutUser() // Automatically log the user out
                } else {
                    Toast.makeText(this@CustomerProfileActivity, "Failed to deactivate account", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@CustomerProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

//    // Function to log out the user and redirect to login screen
//    private fun logOutUser() {
//        // Clear any saved user session if applicable
//        // Redirect to LoginActivity
//        val intent = Intent(this, CustomerLoginActivity::class.java)
//        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
//        startActivity(intent)
//        finish() // End the current activity
//    }

    // Function to log out the user and redirect to login screen
    private fun logOutUser() {
        dbHelper.deleteUserSession()  // Clear user session from SQLite
        val intent = Intent(this, CustomerLoginActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
        finish()
    }
}

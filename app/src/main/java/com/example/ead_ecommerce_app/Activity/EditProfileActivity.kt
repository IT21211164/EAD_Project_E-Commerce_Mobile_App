package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ead_ecommerce_app.Api.CustomerApiService
import com.example.ead_ecommerce_app.Api.RetrofitClient
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.Database.UserDatabaseHelper
import com.example.ead_ecommerce_app.Model.User
import com.squareup.picasso.Picasso
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class EditProfileActivity : AppCompatActivity() {

    private lateinit var profilePicture: ImageView
    private lateinit var usernameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var addressEditText: EditText
    private lateinit var updateButton: Button
    private lateinit var userDatabaseHelper: UserDatabaseHelper
    private lateinit var loggedInUser: User


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_edit_profile)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Initialize views
        profilePicture = findViewById(R.id.profilePicture)
        usernameEditText = findViewById(R.id.usernameEditText)
        emailEditText = findViewById(R.id.emailEditText)
        addressEditText = findViewById(R.id.addressEditText)
        updateButton = findViewById(R.id.updateButton)

        // Get logged-in user from the SQLite database
        userDatabaseHelper = UserDatabaseHelper(this)
        loggedInUser = userDatabaseHelper.getLoggedInUser()!!

        // Populate the fields with user data
        populateFields()

        // Set update button click listener
        updateButton.setOnClickListener {
            updateUserProfile()
        }
    }

    private fun populateFields() {
        // Load the profile picture using Picasso (or Glide)
        if (loggedInUser.profile_Picture.isNotEmpty()) {
            Picasso.get()
                .load(loggedInUser.profile_Picture)
                .placeholder(R.drawable.default_profile_photo) // Add a default image
                .into(profilePicture)
        }

        // Populate the rest of the fields
        usernameEditText.setText(loggedInUser.username)
        emailEditText.setText(loggedInUser.email)
        addressEditText.setText(loggedInUser.address)
    }

    private fun updateUserProfile() {
        // Prepare updated user details
        val updatedUser = User(
            id = loggedInUser.id,
            username = usernameEditText.text.toString(),
            email = emailEditText.text.toString(),
            password = loggedInUser.password, // We do not change the password here
            address = addressEditText.text.toString(),
            profile_Picture = loggedInUser.profile_Picture // Assuming profile picture hasn't changed
        )

        // Call API to update profile
        val apiService = RetrofitClient.retrofit.create(CustomerApiService::class.java)
        val call = apiService.updateCustomer(loggedInUser.id, updatedUser)
        call.enqueue(object : Callback<Void> {
            override fun onResponse(call: Call<Void>, response: Response<Void>) {
                if (response.isSuccessful) {
                    // Update local SQLite database
                    // userDatabaseHelper.addUser(updatedUser)
                    userDatabaseHelper.updateUser(updatedUser)
                    Toast.makeText(this@EditProfileActivity, "Profile updated successfully!", Toast.LENGTH_SHORT).show()

                    // Redirect to CustomerProfileActivity
                    val intent = Intent(this@EditProfileActivity, CustomerProfileActivity::class.java)
                    intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
                    startActivity(intent)

                    // Finish the current activity so it doesn't stay in the back stack
                    finish()
                } else {
                    Toast.makeText(this@EditProfileActivity, "Failed to update profile", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Void>, t: Throwable) {
                Toast.makeText(this@EditProfileActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

}
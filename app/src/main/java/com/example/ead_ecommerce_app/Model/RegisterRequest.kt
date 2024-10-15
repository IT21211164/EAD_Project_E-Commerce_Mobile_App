package com.example.ead_ecommerce_app.Model

data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String,
    val address: String,
    val profile_Picture: String,
    val activation_Status: Boolean = false // Default value set to false
)

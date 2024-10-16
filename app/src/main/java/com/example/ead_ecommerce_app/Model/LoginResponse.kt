package com.example.ead_ecommerce_app.Model

data class LoginResponse(
    val id: String,
    val username: String,
    val email: String,
    val address: String?,
    val profile_Picture: String?,
    val activation_Status: Boolean
)

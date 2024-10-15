package com.example.ead_ecommerce_app.Model

data class User(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    val address: String?,
    val profile_Picture: String
)

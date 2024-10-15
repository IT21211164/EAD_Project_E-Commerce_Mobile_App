package com.example.ead_ecommerce_app.Model

data class CustomerModel(
    val id: String,
    val username: String,
    val email: String,
    val password: String,
    var address: String,
    val profile_Picture:String,
    val activation_Status: Boolean,
)

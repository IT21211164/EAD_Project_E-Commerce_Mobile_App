package com.example.ead_ecommerce_app.Model

data class CartModel(
    val id: String,
    val product_Name: String,
    val price: Double,
    var number_Of_Items: Int,
    val image: String,
)

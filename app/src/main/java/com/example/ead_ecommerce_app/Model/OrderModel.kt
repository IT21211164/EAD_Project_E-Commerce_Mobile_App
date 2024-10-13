package com.example.ead_ecommerce_app.Model

data class OrderModel(
    val id: String,
    val customer_Name: String,
    val customer_Id: String,
    val product_Id: String,
    var quantity: Int,
    val vendor_Id: String,
    val status: String,
    val total: Double,

)

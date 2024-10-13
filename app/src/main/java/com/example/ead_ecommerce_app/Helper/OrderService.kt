package com.example.ead_ecommerce_app.Helper

import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.Model.OrderModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface OrderService {
    @GET("order")
    fun getOrders(): Call<List<OrderModel>>

    @POST("order")
    fun createOrder(@Body order: OrderModel): Call<OrderModel>
}
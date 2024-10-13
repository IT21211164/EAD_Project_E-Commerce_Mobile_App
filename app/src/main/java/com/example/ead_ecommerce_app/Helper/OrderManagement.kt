package com.example.ead_ecommerce_app.Helper

import android.content.Context
import android.widget.Toast
import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.Model.OrderModel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class OrderManagement(val context:Context) {
    // Retrofit service setup
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.8.100:8082/api/")  // Base URL for the IIS server
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val orderService = retrofit.create(OrderService::class.java)

    fun placeOrder(order: OrderModel) {
        orderService.createOrder(order).enqueue(object : Callback<OrderModel> {
            override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(context,"Order created successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Failed to create order!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}
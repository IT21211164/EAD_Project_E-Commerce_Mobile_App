package com.example.ead_ecommerce_app.Helper

import android.content.Context
import android.widget.Toast
import com.example.ead_ecommerce_app.Model.OrderModel
import com.example.ead_ecommerce_app.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class OrderManagement(val context:Context) {

    fun placeOrder(order: OrderModel) {
        RetrofitClient.apiService.createOrder(order).enqueue(object : Callback<OrderModel> {
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
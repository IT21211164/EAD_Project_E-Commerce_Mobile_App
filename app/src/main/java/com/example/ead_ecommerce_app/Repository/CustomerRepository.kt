package com.example.ead_ecommerce_app.Repository

import com.example.ead_ecommerce_app.Api.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApiService {
    @PUT("Customer/deactivate/{id}")
    fun deactivateCustomer(@Path("id") id: String): Call<Void>
}

class CustomerRepository {
    private val customerApi = RetrofitClient.retrofit.create(CustomerApiService::class.java)

    fun deactivateCustomer(id: String, callback: Callback<Void>) {
        val call = customerApi.deactivateCustomer(id)
        call.enqueue(callback)
    }
}
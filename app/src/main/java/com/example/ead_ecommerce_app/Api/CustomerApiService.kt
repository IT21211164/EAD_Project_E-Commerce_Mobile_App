package com.example.ead_ecommerce_app.Api

import com.example.ead_ecommerce_app.Model.User
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.PUT
import retrofit2.http.Path

interface CustomerApiService {
    @PUT("Customer/update/{id}")
    fun updateCustomer(
        @Path("id") id: String,
        @Body updatedUser: User
    ): Call<Void>
}
package com.example.ead_ecommerce_app.Api
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val BASE_URL = "http://192.168.93.139:8082/api/" // base url

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create a singleton instance of AuthApiService
    val authApiService: AuthApiService = retrofit.create(AuthApiService::class.java)
}
package com.example.ead_ecommerce_app.Api
import com.example.ead_ecommerce_app.Model.LoginRequest
import com.example.ead_ecommerce_app.Model.LoginResponse
import com.example.ead_ecommerce_app.Model.RegisterRequest
import com.example.ead_ecommerce_app.Model.RegisterResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

//// Define data models for requests and responses
//data class LoginRequest(val email: String, val password: String)
//data class LoginResponse(val token: String, val userId: String)
//data class RegisterRequest(val email: String, val password: String)
//data class RegisterResponse(val message: String)

interface AuthApiService {
    // Login Endpoint
    @POST("CustomerAuth")
    fun login(@Body loginRequest: LoginRequest): Call<LoginResponse>

    // Register Endpoint (replace with actual URL from Swagger UI)
    @POST("Customer")
    fun register(@Body registerRequest: RegisterRequest): Call<RegisterResponse>
}
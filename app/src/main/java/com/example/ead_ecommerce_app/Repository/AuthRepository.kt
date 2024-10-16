package com.example.ead_ecommerce_app.Repository
import com.example.ead_ecommerce_app.Api.RetrofitClient
import com.example.ead_ecommerce_app.Model.LoginRequest
import com.example.ead_ecommerce_app.Model.LoginResponse
import com.example.ead_ecommerce_app.Model.RegisterRequest
import com.example.ead_ecommerce_app.Model.RegisterResponse

//import com.example.ead_ecommerce_app.Api.AuthApiService
//import com.example.ead_ecommerce_app.Api.LoginRequest
//import com.example.ead_ecommerce_app.Api.LoginResponse
//import com.example.ead_ecommerce_app.Api.RegisterRequest
//import com.example.ead_ecommerce_app.Api.RegisterResponse

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AuthRepository {
//    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
//        val loginRequest = LoginRequest(email, password)
//
//        // Call the login API
//        RetrofitClient.authApiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val token = response.body()!!.token
//                    onResult(true, token)  // Return success with token
//                } else {
//                    onResult(false, response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                onResult(false, t.message)
//            }
//        })
//    }

//    fun login(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
//        val loginRequest = LoginRequest(email, password)
//
//        RetrofitClient.authApiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
//            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
//                if (response.isSuccessful && response.body() != null) {
//                    val loginResponse = response.body()!!
//
//                    // Check if the user is activated
//                    if (loginResponse.activation_Status) {
//                        onResult(true, "Login successful")
//                    } else {
//                        onResult(false, "Your account is still not activated.")
//                    }
//                } else {
//                    // onResult(false, "Login failed: ${response.message()}")
//
//                    // Handling general login failures (Bad Request)
//                    val errorMessage = if (response.code() == 400) {
//                        "Your account is still not activated."
//                    } else {
//                        "Login failed: ${response.message()}"
//                    }
//                    onResult(false, errorMessage)
//                }
//            }
//
//            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
//                onResult(false, t.message)
//            }
//        })
//    }

    // Modify the callback to pass the LoginResponse object as well
    fun login(email: String, password: String, onResult: (Boolean, String, LoginResponse?) -> Unit) {
        val loginRequest = LoginRequest(email, password)

        RetrofitClient.authApiService.login(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body() != null) {
                    val loginResponse = response.body()!!

                    // Check if the user is activated
                    if (loginResponse.activation_Status) {
                        onResult(true, "Login successful", loginResponse)
                    } else {
                        onResult(false, "Your account is still not activated.", null)
                    }
                } else {
                    // Handling general login failures (Bad Request)
                    val errorMessage = if (response.code() == 400) {
                        "Your account is still not activated."
                    } else {
                        "Login failed: ${response.message()}"
                    }
                    onResult(false, errorMessage, null)
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                onResult(false, "Login failed: ${t.message}", null)
            }
        })
    }

//    fun register(email: String, password: String, onResult: (Boolean, String?) -> Unit) {
//        val registerRequest = RegisterRequest(email, password)
//
//        // Call the register API
//        RetrofitClient.authApiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
//            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
//                if (response.isSuccessful) {
//                    onResult(true, "Registration successful")
//                } else {
//                    onResult(false, response.message())
//                }
//            }
//
//            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
//                onResult(false, t.message)
//            }
//        })
//    }

    fun register(username: String, email: String, password: String, address: String, profilePicture: String, onResult: (Boolean, String?) -> Unit) {
        val registerRequest = RegisterRequest(username, email, password, address, profilePicture)

        RetrofitClient.authApiService.register(registerRequest).enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(call: Call<RegisterResponse>, response: Response<RegisterResponse>) {
                if (response.isSuccessful) {
                    onResult(true, "Registration request sent. Waiting for admin approval.")
                } else {
                    onResult(false, response.message())
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                onResult(false, t.message)
            }
        })
    }


}
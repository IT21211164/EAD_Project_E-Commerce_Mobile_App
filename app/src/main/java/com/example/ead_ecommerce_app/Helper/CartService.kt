package com.example.ead_ecommerce_app.Helper

import com.example.ead_ecommerce_app.Model.CartModel
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*

interface CartService {
    @GET("cart")
    fun getCart(): Call<List<CartModel>>

    @POST("cart")
    fun addItemToCart(@Body item: CartModel): Call<CartModel>

    @PUT("cart/{id}")
    fun updateCartItem(
        @Path("id") itemId: String, // MongoDB ObjectId
        @Body cartModel: CartModel
    ): Call<CartModel>

    @DELETE("cart/{id}")
    fun removeCartItem(@Path("id") id: String): Call<Void>
}
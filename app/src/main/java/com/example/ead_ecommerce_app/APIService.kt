package com.example.ead_ecommerce_app.ViewModel

import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.Model.CategoryModel
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.Model.OrderModel
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface APIService {

    //product
    @GET("product") // This corresponds to /api/item on the IIS server
    fun getRecommendedItems(): Call<List<ItemsModel>>

    @GET("product/{id}")
    fun getProduct(@Path("id") id: String): Call<ItemsModel>

    @GET("product/category/{product_Category}")
    fun getProductsByCategory(@Path("product_Category") product_Category: String): Call<List<ItemsModel>>


    //category
    @GET("category")  // The endpoint to get all items
    fun getCategories(): Call<List<CategoryModel>>


    //cart
    @GET("cart")  // The endpoint to get all items
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


    //order
    @POST("order")
    fun createOrder(@Body order: OrderModel): Call<OrderModel>

    @GET("order/customer/{customer_Id}")
    fun getOrders(@Path("customer_Id") customer_Id: String): Call<List<OrderModel>>
}
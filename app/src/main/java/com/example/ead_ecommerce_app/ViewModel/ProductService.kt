package com.example.ead_ecommerce_app.ViewModel

import com.example.ead_ecommerce_app.Model.CategoryModel
import com.example.ead_ecommerce_app.Model.ItemsModel
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ProductService {
    @GET("product") // This corresponds to /api/item on the IIS server
    fun getRecommendedItems(): Call<List<ItemsModel>>

    @GET("category")  // The endpoint to get all items
    fun getCategories(): Call<List<CategoryModel>>

    @GET("product/category/{product_Category}")
    fun getProductsByCategory(@Path("product_Category") product_Category: String): Call<List<ItemsModel>>
}
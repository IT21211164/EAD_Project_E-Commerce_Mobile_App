package com.example.ead_ecommerce_app.ViewModel

import com.example.ead_ecommerce_app.Model.ItemsModel
import retrofit2.Call
import retrofit2.http.GET

interface ProductService {
    @GET("product") // This corresponds to /api/item on the IIS server
    fun getRecommendedItems(): Call<List<ItemsModel>>
}
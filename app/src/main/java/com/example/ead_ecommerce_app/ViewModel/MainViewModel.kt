package com.example.ead_ecommerce_app.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ead_ecommerce_app.Model.CategoryModel
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.Model.SliderModel
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.Query
import com.google.firebase.database.ValueEventListener

import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

class MainViewModel():ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private  val _banner = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _recommended = MutableLiveData<MutableList<ItemsModel>>()

    val banners:LiveData<List<SliderModel>> = _banner
    val categories:LiveData<MutableList<CategoryModel>> = _category
    val recommended:LiveData<MutableList<ItemsModel>> = _recommended

    // Retrofit service setup
    private val retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.43.103:8082/api/")  // Base URL for the IIS server
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val itemService = retrofit.create(ProductService::class.java)

    fun loadRecommended() {
        // Make API call to fetch the recommended items
        itemService.getRecommendedItems().enqueue(object : Callback<List<ItemsModel>> {
            override fun onResponse(call: Call<List<ItemsModel>>, response: Response<List<ItemsModel>>) {
                if (response.isSuccessful) {
                    val items = response.body() ?: emptyList()
                    _recommended.value = items.toMutableList()
                } else {
                    Log.e("API", "Response not successful: ${response.errorBody()?.string()}")
                    _recommended.value = mutableListOf()  // Empty the list on failure
                }
            }

            override fun onFailure(call: Call<List<ItemsModel>>, t: Throwable) {
                Log.e("API", "API call failed: ${t.message}")
                _recommended.value = mutableListOf()  // Empty the list on failure
            }
        })
    }

    fun loadFiltered(product_Category: String) {
        // Make API call to fetch items filtered by category ID
        itemService.getProductsByCategory(product_Category).enqueue(object : Callback<List<ItemsModel>> {
            override fun onResponse(call: Call<List<ItemsModel>>, response: Response<List<ItemsModel>>) {
                if (response.isSuccessful) {
                    val items = response.body() ?: emptyList()

                    // Update LiveData with the fetched items
                    _recommended.value = items.toMutableList()
                } else {
                    Log.e("API", "Response not successful: ${response.errorBody()?.string()}")
                    _recommended.value = mutableListOf()  // Empty the list on failure
                }
            }

            override fun onFailure(call: Call<List<ItemsModel>>, t: Throwable) {
                Log.e("API", "API call failed: ${t.message}")
                _recommended.value = mutableListOf()  // Empty the list on failure
            }
        })
    }




    fun loadCategory() {
        // Make API call to fetch the categories
        itemService.getCategories().enqueue(object : Callback<List<CategoryModel>> {
            override fun onResponse(call: Call<List<CategoryModel>>, response: Response<List<CategoryModel>>) {
                if (response.isSuccessful) {
                    val categories = response.body() ?: emptyList()

                    // Update LiveData with the fetched categories
                    _category.value = categories.toMutableList()
                } else {
                    Log.e("API", "Response not successful: ${response.errorBody()?.string()}")
                    _category.value = mutableListOf()  // Empty the list on failure
                }
            }

            override fun onFailure(call: Call<List<CategoryModel>>, t: Throwable) {
                Log.e("API", "API call failed: ${t.message}")
                _category.value = mutableListOf()  // Empty the list on failure
            }
        })
    }



    fun loadBanners(){
        val Ref =  firebaseDatabase.getReference("Banner")
        Ref.addValueEventListener(object:ValueEventListener{
            override fun onDataChange(snapshot: DataSnapshot) {
                val lists = mutableListOf<SliderModel>()
                for(childSnapshot in snapshot.children){
                    val list = childSnapshot.getValue(SliderModel::class.java)
                    if(list != null){
                        lists.add(list)
                    }
                }
                _banner.value = lists
            }

            override fun onCancelled(error: DatabaseError) {
                TODO("Not yet implemented")
            }

        })
    }
}
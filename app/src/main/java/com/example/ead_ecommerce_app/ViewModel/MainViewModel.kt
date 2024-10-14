package com.example.ead_ecommerce_app.ViewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.Model.CategoryModel
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.Model.OrderModel
import com.example.ead_ecommerce_app.Model.SliderModel
import com.example.ead_ecommerce_app.RetrofitClient
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import retrofit2.*

class MainViewModel():ViewModel() {
    private val firebaseDatabase = FirebaseDatabase.getInstance()

    private  val _banner = MutableLiveData<List<SliderModel>>()
    private val _category = MutableLiveData<MutableList<CategoryModel>>()
    private val _recommended = MutableLiveData<MutableList<ItemsModel>>()
    private val _cart = MutableLiveData<MutableList<CartModel>>()
    private val _order = MutableLiveData<MutableList<OrderModel>>()

    val banners:LiveData<List<SliderModel>> = _banner
    val categories:LiveData<MutableList<CategoryModel>> = _category
    val recommended:LiveData<MutableList<ItemsModel>> = _recommended
    val cart:LiveData<MutableList<CartModel>> = _cart
    val order:LiveData<MutableList<OrderModel>> = _order

    fun loadRecommended() {
        // Make API call to fetch the recommended items
        RetrofitClient.apiService.getRecommendedItems().enqueue(object : Callback<List<ItemsModel>> {
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
        RetrofitClient.apiService.getProductsByCategory(product_Category).enqueue(object : Callback<List<ItemsModel>> {
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
        RetrofitClient.apiService.getCategories().enqueue(object : Callback<List<CategoryModel>> {
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
        val ref =  firebaseDatabase.getReference("Banner")
        ref.addValueEventListener(object:ValueEventListener{
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

    //Get art items
    fun loadCart() {
        // Make API call to fetch the categories
        RetrofitClient.apiService.getCart().enqueue(object : Callback<List<CartModel>> {
            override fun onResponse(call: Call<List<CartModel>>, response: Response<List<CartModel>>) {
                if (response.isSuccessful) {
                    val cart = response.body() ?: emptyList()

                    // Update LiveData with the fetched categories
                    _cart.value = cart.toMutableList()
                } else {
                    Log.e("API", "Response not successful: ${response.errorBody()?.string()}")
                    _cart.value = mutableListOf()  // Empty the list on failure
                }
            }

            override fun onFailure(call: Call<List<CartModel>>, t: Throwable) {
                Log.e("API", "API call failed: ${t.message}")
                _cart.value = mutableListOf()  // Empty the list on failure
            }
        })
    }

    //
    fun loadOrderList(customer_Id: String) {
        // Make API call to fetch the categories
        RetrofitClient.apiService.getOrders(customer_Id).enqueue(object : Callback<List<OrderModel>> {
            override fun onResponse(call: Call<List<OrderModel>>, response: Response<List<OrderModel>>) {
                if (response.isSuccessful) {
                    val order = response.body() ?: emptyList()

                    // Update LiveData with the fetched orders
                    _order.value = order.toMutableList()
                } else {
                    Log.e("API", "Response not successful: ${response.errorBody()?.string()}")
                    _order.value = mutableListOf()  // Empty the list on failure
                }
            }

            override fun onFailure(call: Call<List<OrderModel>>, t: Throwable) {
                Log.e("API", "API call failed: ${t.message}")
                _order.value = mutableListOf()  // Empty the list on failure
            }
        })
    }






}
package com.example.ead_ecommerce_app.Helper

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.Model.OrderModel
import com.example.ead_ecommerce_app.RetrofitClient
import com.example.project1762.Helper.ChangeNumberItemsListener
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CartManagement(val context: Context) {

    private val _cart = MutableLiveData<MutableList<CartModel>>()
    val cart: LiveData<MutableList<CartModel>> = _cart

    fun insertItem(item: CartModel) {
        RetrofitClient.apiService.addItemToCart(item).enqueue(object : Callback<CartModel> {
            override fun onResponse(call: Call<CartModel>, response: Response<CartModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(context,"Item created successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Failed to create item!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartModel>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


    fun minusItem(item: CartModel,listener: ChangeNumberItemsListener) {
        if (item.number_Of_Items == 1){
            RetrofitClient.apiService.removeCartItem(item.id).enqueue(object : Callback<Void> {
                override fun onResponse(call: Call<Void>, response: Response<Void>) {
                    if (response.isSuccessful) {
                        listener.onChanged()
                    } else {
                        Toast.makeText(context,"Failed to update!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Void>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }
        else{
            item.number_Of_Items--

            RetrofitClient.apiService.updateCartItem(item.id,item).enqueue(object : Callback<CartModel> {
                override fun onResponse(call: Call<CartModel>, response: Response<CartModel>) {
                    if (response.isSuccessful) {
                        listener.onChanged()
                    } else {
                        Toast.makeText(context,"Failed to update!", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<CartModel>, t: Throwable) {
                    Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
                }
            })
        }

    }

    fun plusItem(item: CartModel,listener: ChangeNumberItemsListener) {
        item.number_Of_Items++

        RetrofitClient.apiService.updateCartItem(item.id,item).enqueue(object : Callback<CartModel> {
            override fun onResponse(call: Call<CartModel>, response: Response<CartModel>) {
                if (response.isSuccessful) {
                    listener.onChanged()
                } else {
                    Toast.makeText(context,"Failed to update!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CartModel>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    //Get cart items
    fun loadCart() {
        // Make API call to fetch the categories
        RetrofitClient.apiService.getCart().enqueue(object : Callback<List<CartModel>> {
            override fun onResponse(
                call: Call<List<CartModel>>,
                response: Response<List<CartModel>>
            ) {
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

    //get total price for the items in the cart
    fun getTotalFee(): Double {
        val cartItems = _cart.value ?: return 0.0 // Ensure cart isn't null
        var fee = 0.0
        for (item in cartItems) {
            fee += item.price * item.number_Of_Items
        }
        return fee
    }

    //place an order
    fun placeOrder(order: OrderModel) {
        RetrofitClient.apiService.createOrder(order).enqueue(object : Callback<OrderModel> {
            override fun onResponse(call: Call<OrderModel>, response: Response<OrderModel>) {
                if (response.isSuccessful) {
                    Toast.makeText(context,"Order created successfully!", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(context,"Failed to create order!", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<OrderModel>, t: Throwable) {
                Toast.makeText(context, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun placeOrders() {
        val cartItems = _cart.value

        if (cartItems != null) {
            for (item in cartItems) {
                val newOrder = OrderModel(
                    id = "",
                    customer_Name = "Binod",
                    customer_Id = "670c2d64b513b7b6939980ad",
                    product_Id = item.product_Id,
                    quantity = item.number_Of_Items,
                    vendor_Id = item.vendor_Id,
                    status = "processing",
                    total = item.price*item.number_Of_Items
                )
                placeOrder(newOrder)

            }
        }
    }

}



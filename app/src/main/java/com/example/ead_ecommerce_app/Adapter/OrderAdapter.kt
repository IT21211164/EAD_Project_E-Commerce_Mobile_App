package com.example.ead_ecommerce_app.Adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.Model.OrderModel
import com.example.ead_ecommerce_app.RetrofitClient
import com.example.ead_ecommerce_app.databinding.ViewholderOrderBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class OrderAdapter(val orders:MutableList<OrderModel>):RecyclerView.Adapter<OrderAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderOrderBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): OrderAdapter.Viewholder {
        val binding=ViewholderOrderBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: OrderAdapter.Viewholder, position: Int) {
        val order = orders[position]

        with(holder.binding){
            total.text="Total: LKR ${order.total}0"
            status.text="Status: ${order.status}"
            numberOfItems.text="x${order.quantity}"

            // Fetch product details from the product API
            RetrofitClient.apiService.getProduct(order.product_Id).enqueue(object : Callback<ItemsModel> {
                override fun onResponse(call: Call<ItemsModel>, response: Response<ItemsModel>) {
                    if (response.isSuccessful) {
                        val product = response.body()
                        product?.let {
                            // Bind product name,price and image
                            titleText.text = it.product_Name
                            feeEachTime.text = "LKR ${it.price}0"
                            Glide.with(holder.itemView.context)
                                .load(it.image)
                                .into(pic)
                        }
                    }
                }
                override fun onFailure(call: Call<ItemsModel>, t: Throwable) {
                    Log.e("OrderAdapter", "Failed to load product details: ${t.message}")
                }
            })
        }
    }

    override fun getItemCount(): Int = orders.size

}
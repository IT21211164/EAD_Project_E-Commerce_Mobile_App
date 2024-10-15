package com.example.ead_ecommerce_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Helper.CartManagement
import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.databinding.ViewholderCartBinding
import com.example.ead_ecommerce_app.databinding.ViewholderCheckoutBinding
import com.example.project1762.Helper.ChangeNumberItemsListener

class CheckoutAdapter(
    val listItemsSelected:MutableList<CartModel>,
    private val context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener):
    RecyclerView.Adapter<CheckoutAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCheckoutBinding) : RecyclerView.ViewHolder(binding.root)

    private val cartManagement = CartManagement(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CheckoutAdapter.Viewholder {
        val binding=ViewholderCheckoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CheckoutAdapter.Viewholder, position: Int) {
        val item = listItemsSelected[position]

        holder.binding.titleText.text=item.product_Name
        holder.binding.feeEachTime.text="LKR ${item.price}0"
        holder.binding.totalEachItem.text="LKR ${Math.round(item.number_Of_Items*item.price)}.00"
        holder.binding.qty.text="Qty:${item.number_Of_Items}"

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.binding.pic)

    }

    override fun getItemCount(): Int = listItemsSelected.size

}
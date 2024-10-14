package com.example.ead_ecommerce_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Helper.CartManagement
import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.databinding.ViewholderCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener

class CartAdapter(
    val listItemsSelected:MutableList<CartModel>,
    private val context: Context,
    private val changeNumberItemsListener: ChangeNumberItemsListener):
    RecyclerView.Adapter<CartAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val cartManagement = CartManagement(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.Viewholder {
        val binding=ViewholderCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.Viewholder, position: Int) {
        val item = listItemsSelected[position]

        holder.binding.titleText.text=item.product_Name
        holder.binding.feeEachTime.text="LKR ${item.price}0"
        holder.binding.totalEachItem.text="LKR ${Math.round(item.number_Of_Items*item.price)}.00"
        holder.binding.noOfItemsText.text=item.number_Of_Items.toString()

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.binding.pic)

        holder.binding.plusBtn.setOnClickListener{
            cartManagement.plusItem(item, object : ChangeNumberItemsListener {
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }
            })
        }

        holder.binding.minusBtn.setOnClickListener{
            cartManagement.minusItem(item,object :ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }

            })
        }

    }

    override fun getItemCount(): Int = listItemsSelected.size

}
package com.example.ead_ecommerce_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.databinding.ViewholderCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener
import com.example.project1762.Helper.ManagmentCart

class CartAdapter(
    private val listItemsSelected:ArrayList<ItemsModel>,context: Context,
    var changeNumberItemsListener: ChangeNumberItemsListener):
    RecyclerView.Adapter<CartAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderCartBinding) : RecyclerView.ViewHolder(binding.root)

    private val managementCart=ManagmentCart(context)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartAdapter.Viewholder {
        val binding=ViewholderCartBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CartAdapter.Viewholder, position: Int) {
        val item = listItemsSelected[position]

        holder.binding.titleText.text=item.title
        holder.binding.feeEachTime.text="Rs ${item.price}0"
        holder.binding.totalEachItem.text="Rs ${Math.round(item.numberInCart*item.price)}.00"
        holder.binding.noOfItemsText.text=item.numberInCart.toString()

        Glide.with(holder.itemView.context)
            .load(item.picUrl[0])
            .into(holder.binding.pic)

        holder.binding.plusBtn.setOnClickListener{
            managementCart.plusItem(listItemsSelected,position,object :ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }

            })
        }

        holder.binding.minusBtn.setOnClickListener{
            managementCart.minusItem(listItemsSelected,position,object :ChangeNumberItemsListener{
                override fun onChanged() {
                    notifyDataSetChanged()
                    changeNumberItemsListener.onChanged()
                }

            })
        }

    }

    override fun getItemCount(): Int = listItemsSelected.size

}
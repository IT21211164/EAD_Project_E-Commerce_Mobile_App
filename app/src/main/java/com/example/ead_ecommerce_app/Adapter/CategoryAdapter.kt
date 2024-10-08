package com.example.ead_ecommerce_app.Adapter

import android.content.Intent
import android.content.res.ColorStateList
import android.os.Handler
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.widget.ImageViewCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Activity.ListItemsActivity
import com.example.ead_ecommerce_app.Model.CategoryModel
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.databinding.ViewholderCategoryBinding


class CategoryAdapter(val items:MutableList<CategoryModel>):
    RecyclerView.Adapter<CategoryAdapter.Viewholder>() {
    private var selectedPosition = -1
    private var lastSelectedPosition = -1

    inner class Viewholder(val binding: ViewholderCategoryBinding):
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryAdapter.Viewholder {
        val binding = ViewholderCategoryBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: CategoryAdapter.Viewholder, position: Int) {
        val item=items[position]
        holder.binding.textTitle.text=item.title

        Glide.with(holder.itemView.context)
            .load(item.image)
            .into(holder.binding.pic)

        if(selectedPosition==position){
            holder.binding.pic.setBackgroundResource(0)
            holder.binding.mainlayout.setBackgroundResource(R.drawable.orange_bg)
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context,R.color.white)
                )
            )
            holder.binding.textTitle.visibility=View.VISIBLE
            holder.binding.textTitle.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.white
                )
            )
        }else{
            holder.binding.pic.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.mainlayout.setBackgroundResource(0)
            ImageViewCompat.setImageTintList(
                holder.binding.pic,
                ColorStateList.valueOf(
                    ContextCompat.getColor(holder.itemView.context,R.color.black)
                )
            )
            holder.binding.textTitle.visibility=View.GONE
            holder.binding.textTitle.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.black
                )
            )

        }

        holder.binding.root.setOnClickListener{
            val position = position
            if(position!=RecyclerView.NO_POSITION){
                lastSelectedPosition=selectedPosition
                selectedPosition=position
                notifyItemChanged(lastSelectedPosition)
                notifyItemChanged(selectedPosition)
            }
            Handler(Looper.getMainLooper()).postDelayed({
                val intent = Intent(holder.itemView.context,ListItemsActivity::class.java).apply {
                    putExtra("category",item.category_Name)
                    putExtra("title",item.title)
                }
                ContextCompat.startActivity(holder.itemView.context,intent,null)
            },1000)
        }
    }

    override fun getItemCount(): Int=items.size

}



























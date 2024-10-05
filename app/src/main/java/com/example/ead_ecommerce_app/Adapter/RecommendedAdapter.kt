package com.example.ead_ecommerce_app.Adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.databinding.ViewholderCategoryBinding
import com.example.ead_ecommerce_app.databinding.ViewholderRecommendationBinding

class RecommendedAdapter(val items:MutableList<ItemsModel>):RecyclerView.Adapter<RecommendedAdapter.Viewholder>() {

    class Viewholder(val binding: ViewholderRecommendationBinding) :RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecommendedAdapter.Viewholder {
        val binding=ViewholderRecommendationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return Viewholder(binding)
    }

    override fun onBindViewHolder(holder: RecommendedAdapter.Viewholder, position: Int) {
        val item = items[position]

        with(holder.binding){
            titleText.text=item.title
            price.text="Rs${item.price}"
            ratingText.text = item.rating.toString()

            Glide.with(holder.itemView.context)
                .load(item.picUrl[0])
                .into(pic)

            root.setOnClickListener{

            }
        }
    }

    override fun getItemCount(): Int = items.size

}
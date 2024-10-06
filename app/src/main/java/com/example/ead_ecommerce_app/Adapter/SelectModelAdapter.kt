package com.example.ead_ecommerce_app.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.databinding.ViewholderModelBinding
import com.example.ead_ecommerce_app.databinding.ViewholderPicBinding
import com.example.ead_ecommerce_app.databinding.ViewholderRecommendationBinding

class SelectModelAdapter(val items:MutableList<String>):
    RecyclerView.Adapter<SelectModelAdapter.Viewholder>() {

    private var selectedPosition = -1
    private var lastSelectedPosition = -1
    private lateinit var context: Context

    inner class Viewholder(val binding: ViewholderModelBinding):RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): SelectModelAdapter.Viewholder {
        context=parent.context
        val binding = ViewholderModelBinding.inflate(LayoutInflater.from(context),parent,false)
        return  Viewholder(binding)
    }

    override fun onBindViewHolder(holder: SelectModelAdapter.Viewholder, position: Int) {
        holder.binding.modelText.text=items[position]

        holder.binding.root.setOnClickListener{
            lastSelectedPosition=selectedPosition
            selectedPosition=position
            notifyItemChanged(lastSelectedPosition)
            notifyItemChanged(selectedPosition)
        }

        if(selectedPosition==position){
            holder.binding.modelLayout.setBackgroundResource(R.drawable.orange_bg_selected)
            holder.binding.modelText.setTextColor(context.resources.getColor(R.color.orange))
        }else{
            holder.binding.modelLayout.setBackgroundResource(R.drawable.grey_bg)
            holder.binding.modelText.setTextColor(context.resources.getColor(R.color.black))
        }
    }

    override fun getItemCount(): Int = items.size

}
package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.example.ead_ecommerce_app.Adapter.PicAdapter
import com.example.ead_ecommerce_app.Adapter.SelectModelAdapter
import com.example.ead_ecommerce_app.Helper.CartManagement
import com.example.ead_ecommerce_app.Model.CartModel
import com.example.ead_ecommerce_app.Model.ItemsModel
import com.example.ead_ecommerce_app.databinding.ActivityDetailBinding


class DetailActivity : BaseActivity() {
    private lateinit var binding: ActivityDetailBinding
    private lateinit var item: ItemsModel
    private var numberOrder=1
    private lateinit var cartManagement: CartManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartManagement=CartManagement(this)

        getBundle()
    }


    private fun getBundle() {
        item = intent.getParcelableExtra("object")!!

        val newItem = CartModel(
            id = "",
            product_Name = item.product_Name,
            product_Id = item.id,
            vendor_Id = item.vendor_Id,
            price = item.price,
            number_Of_Items = 1,
            image = item.image,
        )


        binding.titleText.text = item.product_Name
        binding.descriptionText.text = item.description
        binding.priceText.text = "Rs "+item.price+"0"
        binding.vendorText.text = item.vendor_Name

        Glide.with(this)
            .load(item.image)
            .into(binding.img)
//        binding.ratingText.text = "${item.rating} Rating"
        binding.addCartBtn.setOnClickListener{
            cartManagement.insertItem(newItem)
        }
        binding.backBtn.setOnClickListener{ finish() }
        binding.cartBtn.setOnClickListener{
           startActivity(Intent(this@DetailActivity, CartActivity::class.java))
        }



    }
}


























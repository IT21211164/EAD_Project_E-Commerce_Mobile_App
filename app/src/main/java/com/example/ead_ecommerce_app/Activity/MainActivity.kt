package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.MarginPageTransformer
import com.example.ead_ecommerce_app.Adapter.CategoryAdapter
import com.example.ead_ecommerce_app.Adapter.RecommendedAdapter
import com.example.ead_ecommerce_app.Adapter.SliderAdapter
import com.example.ead_ecommerce_app.Model.SliderModel
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.ViewModel.MainViewModel
import com.example.ead_ecommerce_app.databinding.ActivityMainBinding

class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel = MainViewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBanner()
        initCategory()
        initRecommended()
//        initBottomMenu()
    }

//    private fun initBottomMenu() {
//        binding.cartBtn.setOnClickListener{
//            startActivity(Intent(this@MainActivity, CartActivity::class.java))
//        }
//    }

    private fun initRecommended() {
        binding.progressBarRecommendation.visibility=View.VISIBLE
        viewModel.recommended.observe(this, Observer {
            binding.recyclerViewRecommendation.layoutManager=GridLayoutManager(this@MainActivity,2)
            binding.recyclerViewRecommendation.adapter=RecommendedAdapter(it)
            binding.progressBarRecommendation.visibility=View.GONE
        })
        viewModel.loadRecommended()
    }

    private fun initCategory(){
        binding.progressBarCategories.visibility=View.VISIBLE
        viewModel.categories.observe(this, Observer {
            binding.recyclerViewCategories.layoutManager=LinearLayoutManager(this@MainActivity,LinearLayoutManager.HORIZONTAL,false)
            binding.recyclerViewCategories.adapter=CategoryAdapter(it)
            binding.progressBarCategories.visibility=View.GONE
        })
        viewModel.loadCategory()
    }

    private fun banners(image:List<SliderModel>){
        binding.viewPager2.adapter = SliderAdapter(image,binding.viewPager2)
        binding.viewPager2.clipToPadding = false
        binding.viewPager2.clipChildren = false
        binding.viewPager2.offscreenPageLimit = 3
        binding.viewPager2.getChildAt(0).overScrollMode = RecyclerView.OVER_SCROLL_NEVER

        val compositePageTransformer = CompositePageTransformer().apply{
            addTransformer(MarginPageTransformer(40))
        }
        binding.viewPager2.setPageTransformer(compositePageTransformer)

        if(image.size > 1){
            binding.dotIndicator.visibility = View.VISIBLE
            binding.dotIndicator.attachTo(binding.viewPager2)
        }
    }

    private fun initBanner(){
        binding.progressBarSlider.visibility = View.VISIBLE
        viewModel.banners.observe(this, Observer {
            banners(it)
            binding.progressBarSlider.visibility = View.GONE
        })
        viewModel.loadBanners()
    }
}
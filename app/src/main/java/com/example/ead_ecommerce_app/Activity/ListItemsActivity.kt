package com.example.ead_ecommerce_app.Activity

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.example.ead_ecommerce_app.Adapter.ListItemsAdapter
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.ViewModel.MainViewModel
import com.example.ead_ecommerce_app.databinding.ActivityDetailBinding
import com.example.ead_ecommerce_app.databinding.ActivityListItemsBinding

class ListItemsActivity : BaseActivity() {
    private lateinit var binding: ActivityListItemsBinding
    private val viewModel=MainViewModel()
    private var category:String=""
    private var title: String=""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListItemsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        getBundle()
        initList()
    }

    private fun initList() {
        binding.apply {
            binding.backBtn.setOnClickListener{finish()}
            progressBarList.visibility=View.VISIBLE
            viewModel.recommended.observe(this@ListItemsActivity, Observer {
                viewList.layoutManager=GridLayoutManager(this@ListItemsActivity,2)
                viewList.adapter=ListItemsAdapter(it)
                progressBarList.visibility=View.GONE
            })
            viewModel.loadFiltered(category)
        }
    }

    private fun getBundle() {
        category = intent.getStringExtra("category")!!
        title = intent.getStringExtra("title")!!

        binding.categoryText.text=title
    }
}


















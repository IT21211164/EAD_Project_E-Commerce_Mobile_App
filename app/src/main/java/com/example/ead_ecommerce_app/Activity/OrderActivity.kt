package com.example.ead_ecommerce_app.Activity

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ead_ecommerce_app.Adapter.OrderAdapter
import com.example.ead_ecommerce_app.ViewModel.MainViewModel
import com.example.ead_ecommerce_app.databinding.ActivityOrderBinding

class OrderActivity : BaseActivity() {
    private lateinit var binding:ActivityOrderBinding
    private val viewModel = MainViewModel()
    private var customer_Id:String="670c2d64b513b7b6939980ad"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityOrderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setVariable()
        initOrderList()
    }

    private fun initOrderList() {
        //binding.progressBarRecommendation.visibility=View.VISIBLE
        viewModel.order.observe(this, Observer {
            binding.viewOrders.layoutManager=LinearLayoutManager(this@OrderActivity,LinearLayoutManager.VERTICAL,false)
            binding.viewOrders.adapter= OrderAdapter(it)
            with(binding){
                emptyText.visibility=if(viewModel.order.value?.isEmpty() == true) View.VISIBLE else View.GONE
                scrollViewCart.visibility=if(viewModel.order.value?.isEmpty() == true) View.GONE else View.VISIBLE

            }

        })
        viewModel.loadOrderList(customer_Id)
    }


    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener{
                finish()
            }

        }
    }


}
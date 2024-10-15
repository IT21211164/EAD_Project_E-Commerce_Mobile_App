package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ead_ecommerce_app.Adapter.CartAdapter
import com.example.ead_ecommerce_app.Helper.CartManagement
import com.example.ead_ecommerce_app.ViewModel.MainViewModel
import com.example.ead_ecommerce_app.databinding.ActivityCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener

class CartActivity : BaseActivity() {
    private lateinit var binding:ActivityCartBinding
    private val viewModel = MainViewModel()
    private lateinit var cartManagement: CartManagement

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartManagement=CartManagement(this)

        setVariable()
        initCartList()
        calculatorCart()
    }

    private fun initCartList() {
        //binding.progressBarRecommendation.visibility=View.VISIBLE
        viewModel.cart.observe(this, Observer {
            binding.viewCart.layoutManager=LinearLayoutManager(this@CartActivity,LinearLayoutManager.VERTICAL,false)
            binding.viewCart.adapter= CartAdapter(it,this,object:ChangeNumberItemsListener{
                override fun onChanged() {
                    calculatorCart()
                    initCartList()
                }
            })
            with(binding){
                emptyText.visibility=if(viewModel.cart.value?.isEmpty() == true) View.VISIBLE else View.GONE
                scrollViewCart.visibility=if(viewModel.cart.value?.isEmpty() == true) View.GONE else View.VISIBLE

            }

            //binding.progressBarRecommendation.visibility=View.GONE
        })
        viewModel.loadCart()
    }


    private  fun calculatorCart(){

        val delivery=0.00

        cartManagement.cart.observe(this, Observer { cartItems ->
            if (cartItems != null) {
                // Calculate the total fee when the cart updates
                val totalFee = Math.round(cartManagement.getTotalFee()*100) / 100
                val total = Math.round((cartManagement.getTotalFee() + delivery) * 100)/100
                binding.totalFeeText.text="LKR ${totalFee}.00"
                binding.deliveryFeeText.text="LKR ${delivery}0"
                binding.totalText.text="LKR ${total}.00"
            }
        })
        cartManagement.loadCart()

    }

    private fun setVariable() {
        binding.apply {
            backBtn.setOnClickListener{
                finish()
            }
            purchaseBtn.setOnClickListener{
                startActivity(Intent(this@CartActivity, CheckoutActivity::class.java))
            }
            exploreBtn.setOnClickListener{
                startActivity(Intent(this@CartActivity, MainActivity::class.java))
            }
            ordersBtn.setOnClickListener{
                startActivity(Intent(this@CartActivity, OrderActivity::class.java))
            }

        }
    }


}
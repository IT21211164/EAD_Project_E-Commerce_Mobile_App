package com.example.ead_ecommerce_app.Activity

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ead_ecommerce_app.Adapter.CartAdapter
import com.example.ead_ecommerce_app.Adapter.RecommendedAdapter
import com.example.ead_ecommerce_app.Helper.CartManagement
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.ViewModel.MainViewModel
import com.example.ead_ecommerce_app.databinding.ActivityCartBinding
import com.example.project1762.Helper.ChangeNumberItemsListener
//import com.example.project1762.Helper.ManagmentCart

class CartActivity : BaseActivity() {
    private lateinit var binding:ActivityCartBinding
    private val viewModel = MainViewModel()
    private lateinit var cartManagement: CartManagement
//    private lateinit var managmentCart: ManagmentCart
//    private var tax:Double=0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityCartBinding.inflate(layoutInflater)
        setContentView(binding.root)

        cartManagement=CartManagement(this)
        //managmentCart=ManagmentCart(this)


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
            //binding.progressBarRecommendation.visibility=View.GONE
        })
        viewModel.loadCart()
    }
//    private fun initCartList() {
//        binding.viewCart.layoutManager=LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
//        binding.viewCart.adapter=CartAdapter(managmentCart.getListCart(),this,object:ChangeNumberItemsListener{
//            override fun onChanged() {
//                calculatorCart()
//            }
//        })
//        with(binding){
//            emptyText.visibility=if(managmentCart.getListCart().isEmpty()) View.VISIBLE else View.GONE
//            scrollViewCart.visibility=if(managmentCart.getListCart().isEmpty()) View.GONE else View.VISIBLE
//
//        }
//    }

    private  fun calculatorCart(){

        val delivery=500.00

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
//
//            method1.setOnClickListener{
//                method1.setBackgroundResource(R.drawable.orange_bg_selected)
//                methodIcon1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.orange))
//                method1Text.setTextColor(getResources().getColor(R.color.orange))
//                methodSubText1.setTextColor(getResources().getColor(R.color.orange))
//
//                method2.setBackgroundResource(R.drawable.grey_bg_selected)
//                methodIcon2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.black))
//                method2Text.setTextColor(getResources().getColor(R.color.black))
//                methodSubText2.setTextColor(getResources().getColor(R.color.black))
//
//            }
//
//            method2.setOnClickListener{
//                method2.setBackgroundResource(R.drawable.orange_bg_selected)
//                methodIcon2.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.orange))
//                method2Text.setTextColor(getResources().getColor(R.color.orange))
//                methodSubText2.setTextColor(getResources().getColor(R.color.orange))
//
//                method1.setBackgroundResource(R.drawable.grey_bg_selected)
//                methodIcon1.imageTintList= ColorStateList.valueOf(ContextCompat.getColor(this@CartActivity,R.color.black))
//                method1Text.setTextColor(getResources().getColor(R.color.black))
//                methodSubText1.setTextColor(getResources().getColor(R.color.black))
//
//            }
        }
    }


}
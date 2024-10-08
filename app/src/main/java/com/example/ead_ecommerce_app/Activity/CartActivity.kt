//package com.example.ead_ecommerce_app.Activity
//
//import android.content.res.ColorStateList
//import android.os.Bundle
//import android.view.View
//import androidx.activity.enableEdgeToEdge
//import androidx.appcompat.app.AppCompatActivity
//import androidx.core.content.ContextCompat
//import androidx.core.view.ViewCompat
//import androidx.core.view.WindowInsetsCompat
//import androidx.recyclerview.widget.LinearLayoutManager
//import com.example.ead_ecommerce_app.Adapter.CartAdapter
//import com.example.ead_ecommerce_app.R
//import com.example.ead_ecommerce_app.databinding.ActivityCartBinding
//import com.example.project1762.Helper.ChangeNumberItemsListener
//import com.example.project1762.Helper.ManagmentCart
//
//class CartActivity : BaseActivity() {
//    private lateinit var binding:ActivityCartBinding
//    private lateinit var managmentCart: ManagmentCart
//    private var tax:Double=0.0
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding=ActivityCartBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        managmentCart=ManagmentCart(this)
//
//        setVariable()
//        initCartList()
//        calculatorCart()
//    }
//
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
//
//    private fun setVariable() {
//        binding.apply {
//            backBtn.setOnClickListener{
//                finish()
//            }
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
//        }
//    }
//
//    private  fun calculatorCart(){
//        val percentTax=0.02
//        val delivery=10.0
//        tax=Math.round((managmentCart.getTotalFee()*percentTax)*100) / 100.0
//        val total = Math.round((managmentCart.getTotalFee() + tax + delivery) * 100)/100
//        val itemTotal = Math.round(managmentCart.getTotalFee()*100) / 100
//
//        with(binding){
//            totalFeeText.text="$$itemTotal"
//            taxText.text="$$tax"
//            deliveryFeeText.text="$$delivery"
//            totalText.text="$$total"
//        }
//    }
//}
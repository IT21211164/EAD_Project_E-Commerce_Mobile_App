package com.example.ead_ecommerce_app.Activity

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ead_ecommerce_app.Model.CustomerModel
import com.example.ead_ecommerce_app.Model.LoginRequest
import com.example.ead_ecommerce_app.R
import com.example.ead_ecommerce_app.RetrofitClient
import com.example.ead_ecommerce_app.Session.SessionManager
import com.example.ead_ecommerce_app.databinding.ActivityCheckoutBinding
import com.example.ead_ecommerce_app.databinding.ActivityCustomerLoginBinding
import com.example.ead_ecommerce_app.databinding.ActivityDetailBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CustomerLoginActivity : BaseActivity() {
    lateinit var sessionManager: SessionManager
    private lateinit var binding: ActivityCustomerLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_login)

        sessionManager = SessionManager(this)

        val emailEditText: EditText = findViewById(R.id.emailET)
        val passwordEditText: EditText = findViewById(R.id.passET)
        val loginButton: AppCompatButton = findViewById(R.id.signInBtn)

        loginButton.setOnClickListener {
            val email = emailEditText.text.toString()
            val password = passwordEditText.text.toString()

            if (email.isNotEmpty() && password.isNotEmpty()) {
                login(email, password)
            } else {
                Toast.makeText(this, "Please enter all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun login(email: String, password: String) {
        val loginRequest = LoginRequest(email, password)

        RetrofitClient.apiService.login(loginRequest).enqueue(object : Callback<CustomerModel> {
            override fun onResponse(call: Call<CustomerModel>, response: Response<CustomerModel>) {
                if (response.isSuccessful) {
                    val loginResponse = response.body()
                    Toast.makeText(applicationContext, "Login successful", Toast.LENGTH_SHORT).show()
                    startActivity(Intent(this@CustomerLoginActivity, MainActivity::class.java))
                    if (loginResponse != null) {
                        sessionManager.saveSession(loginResponse.id)
                    }

                } else {
                    Toast.makeText(applicationContext, "Login failed", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<CustomerModel>, t: Throwable) {
                Toast.makeText(this@CustomerLoginActivity, "Network Error: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}
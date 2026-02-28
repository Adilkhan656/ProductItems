package com.example.dummyjsonapp.View

import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.adapter.CartRecyclerViewAdapter
import com.example.dummyjsonapp.databinding.ActivityCartBinding
import com.example.dummyjsonapp.viewmodel.CartViewModel

class CartActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCartBinding
    private lateinit var cartAdapter: CartRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cart)
        ViewCompat.setOnApplyWindowInsetsListener(binding.cartToolbar) { view, insets ->
            view.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
            insets
        }

        val cartViewModel = ViewModelProvider(this)[CartViewModel::class.java]
        cartAdapter = CartRecyclerViewAdapter()
        binding.rvCartItems.adapter = cartAdapter

        cartViewModel.cartItems.observe(this) { items ->
            val cartList = items ?: emptyList()
            cartAdapter.submitList(cartList)
            binding.tvCartEmpty.visibility = if (cartList.isEmpty()) View.VISIBLE else View.GONE
        }

        binding.cartToolbar.setNavigationOnClickListener { finish() }
    }
}

package com.example.dummyjsonapp.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.dummyjsonapp.Factory.ProductFactory
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.viewmodel.ProductViewModel
import com.example.dummyjsonapp.adapter.ProductRecyclerViewAdapter
import com.example.dummyjsonapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.example.dummyjsonapp.repo.ProductRepo

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        val repository = ProductRepo()
        val factory = ProductFactory(repository)
        val viewModel = ViewModelProvider(this, factory)[ProductViewModel::class.java]

        productAdapter = ProductRecyclerViewAdapter { product ->
            startActivity(
                Intent(this, ProductDetailActivity::class.java).apply {
                    putExtra(ProductDetailActivity.EXTRA_PRODUCT_JSON, Gson().toJson(product))
                }
            )
        }
        binding.rvProducts.adapter = productAdapter

        viewModel.isLoading.observe(this) { isLoading ->
            binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
            binding.tvError.visibility = View.GONE
        }

        viewModel.getproduct().observe(this) { products ->
            val list = products ?: emptyList()
            productAdapter.submitList(list)
            if (list.isEmpty()) binding.tvError.visibility = View.VISIBLE
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }
}

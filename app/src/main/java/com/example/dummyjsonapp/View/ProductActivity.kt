package com.example.dummyjsonapp.View

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.dummyjsonapp.Factory.ProductFactory
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.viewmodel.ProductViewModel
import com.example.dummyjsonapp.adapter.ProductRecyclerViewAdapter
import com.example.dummyjsonapp.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.example.dummyjsonapp.repo.ProductRepo
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import androidx.paging.LoadState

class ProductActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var productAdapter: ProductRecyclerViewAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.lifecycleOwner = this

        ViewCompat.setOnApplyWindowInsetsListener(binding.topAppBar) { view, insets ->
            view.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
            insets
        }

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
        binding.topAppBar.inflateMenu(R.menu.menu_product)
        binding.topAppBar.setOnMenuItemClickListener { menuItem ->
            if (menuItem.itemId == R.id.action_cart) {
                startActivity(Intent(this, CartActivity::class.java))
                true
            } else {
                false
            }
        }
        binding.rvProducts.adapter = productAdapter

        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                launch {
                    viewModel.products.collectLatest { pagingData ->
                        productAdapter.submitData(pagingData)
                    }
                }

                launch {
                    productAdapter.loadStateFlow.collectLatest { loadState ->
                        val isLoading = loadState.refresh is LoadState.Loading
                        val isError = loadState.refresh is LoadState.Error
                        val isEmpty = loadState.refresh is LoadState.NotLoading && productAdapter.itemCount == 0

                        binding.pbLoading.visibility = if (isLoading) View.VISIBLE else View.GONE
                        binding.tvError.visibility = if (isError || isEmpty) View.VISIBLE else View.GONE
                    }
                }
            }
        }

    }
}

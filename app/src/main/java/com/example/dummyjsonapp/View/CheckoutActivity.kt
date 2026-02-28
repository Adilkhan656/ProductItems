package com.example.dummyjsonapp.View

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.updatePadding
import androidx.databinding.DataBindingUtil
import coil.load
import com.example.dummyjsonapp.R
import com.example.dummyjsonapp.databinding.ActivityCheckoutBinding
import com.example.dummyjsonapp.datas.productItems
import com.google.gson.Gson
import java.util.Locale

class CheckoutActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCheckoutBinding
    private var quantity: Int = 1
    private var stock: Int = Int.MAX_VALUE
    private var unitPrice: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = DataBindingUtil.setContentView(this, R.layout.activity_checkout)
        ViewCompat.setOnApplyWindowInsetsListener(binding.checkoutToolbar) { view, insets ->
            view.updatePadding(top = insets.getInsets(WindowInsetsCompat.Type.statusBars()).top)
            insets
        }

        val productJson = intent.getStringExtra(ProductDetailActivity.EXTRA_PRODUCT_JSON)
        val product = productJson?.let { Gson().fromJson(it, productItems::class.java) }
        quantity = intent.getIntExtra(ProductDetailActivity.EXTRA_QUANTITY, 1).coerceAtLeast(1)

        if (product != null) {
            bindProduct(product)
            setupQtyActions()
            updateQuantityUi()
        }

        binding.checkoutToolbar.setNavigationOnClickListener { finish() }
        binding.btnPay.setOnClickListener {
            Toast.makeText(this, "Payment flow will be added next", Toast.LENGTH_SHORT).show()
        }
    }

    private fun bindProduct(product: productItems) {
        stock = product.stock
        unitPrice = product.price

        binding.ivCheckoutImage.load(product.thumbnail.ifBlank { product.images.firstOrNull().orEmpty() }) {
            crossfade(true)
            placeholder(android.R.color.darker_gray)
            error(android.R.color.darker_gray)
        }

        binding.tvCheckoutTitle.text = product.title
        binding.tvCheckoutBrand.text = "Brand: ${product.brand} | SKU: ${product.sku}"
        binding.tvCheckoutPrice.text = String.format(Locale.US, "$%.2f", unitPrice)
        binding.tvCheckoutShipping.text = "Shipping: Free"
    }

    private fun setupQtyActions() {
        binding.btnCheckoutMinus.setOnClickListener {
            if (quantity > 1) {
                quantity--
                updateQuantityUi()
            }
        }

        binding.btnCheckoutPlus.setOnClickListener {
            if (quantity < stock) {
                quantity++
                updateQuantityUi()
            } else {
                Toast.makeText(this, "Reached max stock", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun updateQuantityUi() {
        val subtotal = quantity * unitPrice
        binding.tvCheckoutQty.text = quantity.toString()
        binding.btnCheckoutMinus.isEnabled = quantity > 1
        binding.tvCheckoutSubtotal.text = String.format(Locale.US, "Subtotal: $%.2f", subtotal)
        binding.tvCheckoutTotal.text = String.format(Locale.US, "Total: $%.2f", subtotal)
        binding.btnPay.text = String.format(Locale.US, "Pay $%.2f", subtotal)
    }
}

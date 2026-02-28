package com.example.dummyjsonapp.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dummyjsonapp.databinding.ItemCartDetailBinding
import com.example.dummyjsonapp.db.CartItemEntity
import java.util.Locale

class CartRecyclerViewAdapter : RecyclerView.Adapter<CartRecyclerViewAdapter.CartViewHolder>() {

    private val items: MutableList<CartItemEntity> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val binding = ItemCartDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CartViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    fun submitList(newItems: List<CartItemEntity>) {
        items.clear()
        items.addAll(newItems)
        notifyDataSetChanged()
    }

    class CartViewHolder(private val binding: ItemCartDetailBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: CartItemEntity) {
            binding.ivCartImage.load(item.thumbnail) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
                error(android.R.color.darker_gray)
            }

            binding.tvCartTitle.text = item.title
            binding.tvCartDescription.text = item.description
            binding.tvCartPrice.text = String.format(Locale.US, "$%.2f", item.price)
            binding.tvCartQty.text = "Qty: ${item.quantity}"
            binding.tvCartStock.text = "Stock: ${item.stock}"
            binding.tvCartBrandCategory.text = "Brand: ${item.brand}  |  Category: ${item.category}"
            binding.tvCartSku.text = "SKU: ${item.sku}"
            binding.tvCartShipping.text = "Shipping: ${item.shippingInformation}"
            binding.tvCartReturn.text = "Return: ${item.returnPolicy}"

            val stockColor = if (item.stock > 0) "#2A7A2A" else "#B3261E"
            binding.tvCartStock.setTextColor(Color.parseColor(stockColor))
        }
    }
}

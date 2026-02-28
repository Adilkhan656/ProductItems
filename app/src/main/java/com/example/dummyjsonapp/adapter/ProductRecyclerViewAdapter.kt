package com.example.dummyjsonapp.adapter

import android.graphics.Color
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.dummyjsonapp.databinding.ItemProductCardBinding
import com.example.dummyjsonapp.datas.productItems
import java.util.Locale

class ProductRecyclerViewAdapter(
    private val onProductClick: (productItems) -> Unit
) :
    PagingDataAdapter<productItems, ProductRecyclerViewAdapter.MyViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyViewHolder {
        val binding = ItemProductCardBinding.inflate(
            android.view.LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: MyViewHolder,
        position: Int
    ) {
        getItem(position)?.let { holder.bind(it, onProductClick) }
    }

    companion object {
        private val DiffCallback = object : DiffUtil.ItemCallback<productItems>() {
            override fun areItemsTheSame(oldItem: productItems, newItem: productItems): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: productItems, newItem: productItems): Boolean {
                return oldItem == newItem
            }
        }
    }


    class MyViewHolder(private val binding: ItemProductCardBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(product: productItems, onProductClick: (productItems) -> Unit) {
            binding.tvTitle.text = product.title
            binding.tvDescription.text = product.description
            binding.tvCategory.text = product.category
            binding.tvBrand.text = "by ${product.brand}"
            binding.tvPrice.text = String.format(Locale.US, "$%.2f", product.price)
            binding.tvRating.text = String.format(Locale.US, "★ %.1f", product.rating)
            binding.tvDiscount.text =
                String.format(Locale.US, "-%.1f%%", product.discountPercentage)

            binding.tvStock.text = if (product.stock > 0) {
                "In stock (${product.stock})"
            } else {
                "Out of stock"
            }
            binding.tvStock.setTextColor(
                if (product.stock > 0) Color.parseColor("#2A7A2A") else Color.parseColor("#B3261E")
            )

            val imageUrl = product.thumbnail.ifBlank { product.images.firstOrNull().orEmpty() }
            binding.ivThumbnail.load(imageUrl) {
                crossfade(true)
                placeholder(android.R.color.darker_gray)
                error(android.R.color.darker_gray)
            }

            binding.cardRoot.setOnClickListener { onProductClick(product) }
        }
    }
}

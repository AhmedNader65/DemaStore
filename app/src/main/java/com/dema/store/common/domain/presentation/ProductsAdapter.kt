package com.dema.store.common.domain.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dema.store.common.domain.model.product.Product
import com.dema.store.databinding.ItemProductBinding

class ProductsAdapter : ListAdapter<Product, ProductsAdapter.ProductsViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item: Product = getItem(position)

        holder.bind(item)
    }

    inner class ProductsViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product) {
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<Product>() {
    override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean {
        // TODO: compare identity
        return true
    }

    override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean {
        // TODO: compare contents
        return true
    }
}

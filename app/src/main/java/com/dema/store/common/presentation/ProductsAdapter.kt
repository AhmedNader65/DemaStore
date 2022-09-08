package com.dema.store.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.databinding.ItemProductBinding

class ProductsAdapter : ListAdapter<UIProduct, ProductsAdapter.ProductsViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductsViewHolder {
        val binding = ItemProductBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return ProductsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ProductsViewHolder, position: Int) {
        val item: UIProduct = getItem(position)

        holder.bind(item)
    }

    inner class ProductsViewHolder(
        private val binding: ItemProductBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIProduct) {
        }
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIProduct>() {
    override fun areItemsTheSame(oldItem: UIProduct, newItem: UIProduct): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIProduct, newItem: UIProduct): Boolean {
        return oldItem == newItem
    }
}

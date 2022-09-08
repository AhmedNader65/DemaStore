package com.dema.store.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dema.store.common.domain.model.product.Product
import com.dema.store.databinding.ItemSliderBinding

class SliderAdapter : ListAdapter<Product, SliderAdapter.SlidersViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlidersViewHolder {
        val binding = ItemSliderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SlidersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlidersViewHolder, position: Int) {
        val item: Product = getItem(position)

        holder.bind(item)
    }

    inner class SlidersViewHolder(
        private val binding: ItemSliderBinding
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

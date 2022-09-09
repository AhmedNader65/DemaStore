package com.dema.store.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.databinding.ItemSliderBinding

class SliderAdapter : ListAdapter<UIProduct, SliderAdapter.SlidersViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlidersViewHolder {
        val binding = ItemSliderBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SlidersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlidersViewHolder, position: Int) {
        val item: UIProduct = getItem(position)

        holder.bind(item)
    }

    inner class SlidersViewHolder(
        private val binding: ItemSliderBinding
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

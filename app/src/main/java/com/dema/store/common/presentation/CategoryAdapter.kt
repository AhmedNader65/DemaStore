package com.dema.store.common.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.databinding.ItemCategoryBinding
import com.dema.store.databinding.ItemSliderBinding
import com.dema.store.home.presentation.model.UIProductSlider

class CategoryAdapter(val onCategoryClickListener: CategoryClickListener) :
    ListAdapter<UIProductSlider, CategoryAdapter.SlidersViewHolder>(ITEM_COMPARATOR) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SlidersViewHolder {
        val binding = ItemCategoryBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return SlidersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SlidersViewHolder, position: Int) {
        val item: UIProductSlider = getItem(position)

        holder.bind(item)
    }

    inner class SlidersViewHolder(
        private val binding: ItemCategoryBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: UIProductSlider) {
            binding.item = item
            binding.onClickListener = onCategoryClickListener
            binding.executePendingBindings()
        }
    }

    class CategoryClickListener(val clickListener: (categoryId: Long) -> Unit) {
        fun onClick(categoryId: Long) = clickListener(categoryId)
    }
}

private val ITEM_COMPARATOR = object : DiffUtil.ItemCallback<UIProductSlider>() {
    override fun areItemsTheSame(oldItem: UIProductSlider, newItem: UIProductSlider): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: UIProductSlider, newItem: UIProductSlider): Boolean {
        return oldItem == newItem
    }
}

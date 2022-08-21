package com.dema.store.common.domain.model.category

data class Category(
    val id: Int,
    val name: String,
    val icon: String,
    val image: String,
    val productCount: Int
) {
}

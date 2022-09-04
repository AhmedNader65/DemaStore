package com.dema.store.common.domain.model.category

data class Category(
    val id: Long,
    val name: String,
    val slug: String,
    val icon: String,
    val image: String,
    val productCount: Int
) {
}

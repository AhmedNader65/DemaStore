package com.dema.store.common.data.cache

data class CachedCategory(
    val id: Long,
    val name: String,
    val slug: String,
    val icon: String,
    val image: String,
    val productCount: Int
) {
}

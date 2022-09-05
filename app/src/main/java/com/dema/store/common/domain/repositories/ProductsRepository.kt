package com.dema.store.common.domain.repositories

import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import kotlinx.coroutines.flow.Flow


interface ProductsRepository {

    fun getProducts(): Flow<List<Product>> // 1
    fun getCategories(): Flow<List<Category>> // 1
    suspend fun requestMoreProducts(pageToLoad: Int, numberOfItems: Int,category: Long = 0): PaginatedProducts
    suspend fun requestCategories(): List<Category>
    suspend fun storeCategory(category: List<Category>) // 3
    suspend fun storeProducts(category: Category, products: List<ProductWithDetails>) // 3

}
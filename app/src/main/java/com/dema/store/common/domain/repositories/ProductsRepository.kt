package com.dema.store.common.domain.repositories

import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.home.domain.model.HomeProduct
import kotlinx.coroutines.flow.Flow


interface ProductsRepository {

    fun getProducts(): Flow<List<Product>> // 1
    fun getHome(): Flow<List<HomeProduct>> // 1
    fun getCategories(): Flow<List<Category>> // 1
    suspend fun requestMoreProducts(
        pageToLoad: Int,
        numberOfItems: Int,
        category: Long = 0
    ): PaginatedProducts

    suspend fun requestHomeProducts(): List<HomeProduct>
    suspend fun requestCategories(): List<Category>
    suspend fun storeCategory(category: List<Category>)
    suspend fun storeProducts(products: List<ProductWithDetails>)
    suspend fun storeHome(home: List<HomeProduct>)

}
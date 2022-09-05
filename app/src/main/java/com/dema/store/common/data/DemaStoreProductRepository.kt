package com.dema.store.common.data

import com.dema.store.common.data.api.DemaApi
import com.dema.store.common.data.api.model.mapToDomain
import com.dema.store.common.data.api.model.mapToDomainProduct
import com.dema.store.common.data.api.model.mapToDomainProductWithDetails
import com.dema.store.common.data.cache.Cache
import com.dema.store.common.data.cache.model.CachedCategory
import com.dema.store.common.data.cache.model.CachedProductAggregate
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DemaStoreProductRepository @Inject constructor(
    private val api: DemaApi,
    private val cache: Cache
) : ProductsRepository {
    override fun getProducts(): Flow<List<Product>> {
        return cache.getProducts()
            .distinctUntilChanged() // ensures only events with new information get to the subscriber.
            .map { productsList ->
                productsList.map {
                    it.product.toProductDomain(
                        it.photos.first(),
                        it.category
                    )
                }
            }

    }

    override fun getCategories(): Flow<List<Category>> {
        return cache.getCategories()
            .distinctUntilChanged()
            .map { categoriesList ->
                categoriesList.map {
                    it.toDomain()
                }
            }
    }

    override suspend fun requestMoreProducts(
        pageToLoad: Int,
        numberOfItems: Int,
        category: Long,

        ): PaginatedProducts {
        val (apiProducts, apiPagination) = api.getProducts( // 1
            pageToLoad,
            numberOfItems,
            category
        )
        return PaginatedProducts( // 2
            apiProducts?.map {
                it.mapToDomainProductWithDetails()
            }.orEmpty(),
            apiPagination!!.mapToDomain()
        )

    }

    override suspend fun requestCategories(): List<Category> {
        val apiCategory = api.getCategories()
        return apiCategory.map {
            it.mapToDomain()
        }
    }

    override suspend fun storeCategory(category: List<Category>) {
        cache.storeCategories(category.map { CachedCategory.fromDomain(it) })
    }

    override suspend fun storeProducts(category: Category, products: List<ProductWithDetails>) {
        cache.storeProducts(products.map { CachedProductAggregate.fromDomain(it, category) })
    }

}
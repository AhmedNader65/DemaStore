package com.dema.store.common.data

import com.dema.store.common.data.api.DemaApi
import com.dema.store.common.data.api.model.mapToDomain
import com.dema.store.common.data.api.model.mapToDomainProductWithDetails
import com.dema.store.common.data.cache.Cache
import com.dema.store.common.data.cache.model.CachedCategory
import com.dema.store.common.data.cache.model.CachedHome
import com.dema.store.common.data.cache.model.CachedProductAggregate
import com.dema.store.common.data.cache.model.CachedUpdateCategory
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.category.UpdateCategory
import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.home.domain.model.HomeProduct
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

    override fun getHome(): Flow<List<HomeProduct>> {
        return cache.getHome()
            .distinctUntilChanged()
            .map {
                it.map {
                    it.toDomain()
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

    override suspend fun requestHomeProducts(): List<HomeProduct> {

        return api.getHome().mapToDomain()
    }

    override suspend fun requestCategories(): List<Category> {
        val apiCategory = api.getCategories()
        return apiCategory.data.map {
            it.mapToDomain()
        }
    }

    override suspend fun storeCategory(category: List<Category>) {
        cache.storeCategories(*category.map { CachedCategory.fromDomain(it) }.toTypedArray())
    }

    override suspend fun storeProducts(products: List<ProductWithDetails>) {

        cache.storeProducts(*products.map {
            val category = UpdateCategory(it.categoryId, it.categoryName)
            cache.storeOrUpdateCategories(CachedUpdateCategory.fromDomain(category))
            CachedProductAggregate.fromDomain(it, category)
        }.toTypedArray())
    }

    override suspend fun storeHome(home: List<HomeProduct>) {
        cache.storeHome(*home.map {
            CachedHome.fromDomain(it)
        }.toTypedArray())

    }

}
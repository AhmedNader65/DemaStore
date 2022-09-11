package com.dema.store.common.data

import com.dema.store.common.data.api.DemaApi
import com.dema.store.common.data.api.model.mapToDomain
import com.dema.store.common.data.api.model.mapToDomainProductWithDetails
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.pagination.PaginatedProducts
import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.home.domain.model.HomeProduct
import javax.inject.Inject

class DemaStoreProductRepository @Inject constructor(
    private val api: DemaApi
) : ProductsRepository {

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


}
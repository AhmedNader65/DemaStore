package com.dema.store.home.domain.usecases

import com.dema.store.common.domain.model.NoMoreProductsException
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.pagination.Pagination
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.common.utils.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestNextPageOfProducts @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke(
        pageToLoad: Int,
        pageSize: Int = Pagination.DEFAULT_PAGE_SIZE,
        category: Long
    ): Pair<List<ProductWithDetails>, Pagination> {
        return withContext(dispatchersProvider.io()) {
            val (products, pagination) =
                productsRepository.requestMoreProducts(pageToLoad, pageSize, category = category)
            if (products.isEmpty()) {
                throw NoMoreProductsException("No more products Available :(")
            }

            return@withContext Pair(products,pagination)
        }
    }
}
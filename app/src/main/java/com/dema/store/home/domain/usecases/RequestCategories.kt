package com.dema.store.home.domain.usecases

import com.dema.store.common.domain.model.NoMoreProductsException
import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.pagination.Pagination
import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.common.utils.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestCategories @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke() {
        return withContext(dispatchersProvider.io()) {
            val categories =
                productsRepository.requestCategories()
            if (categories.isEmpty()) {
                throw NoMoreProductsException("No more categories Available :(")
            }

            productsRepository.storeCategory(categories)
        }
    }
}
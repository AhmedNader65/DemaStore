package com.dema.store.home.domain.usecases

import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.common.utils.DispatchersProvider
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestHomeProducts @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke() {
        return withContext(dispatchersProvider.io()) {
            val (newProducts, popularProducts, saleProducts) =
                productsRepository.requestHomeProducts()
            productsRepository.storeProducts(newProducts)
            productsRepository.storeProducts(popularProducts)
            productsRepository.storeProducts(saleProducts)
        }
    }
}
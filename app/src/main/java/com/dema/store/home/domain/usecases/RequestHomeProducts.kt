package com.dema.store.home.domain.usecases

import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.common.utils.DispatchersProvider
import com.dema.store.home.domain.model.HomeProduct
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RequestHomeProducts @Inject constructor(
    private val productsRepository: ProductsRepository,
    private val dispatchersProvider: DispatchersProvider
) {
    suspend operator fun invoke(): List<HomeProduct> {
        return withContext(dispatchersProvider.io()) {
            return@withContext productsRepository.requestHomeProducts()
        }
    }
}
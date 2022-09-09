package com.dema.store.home.domain.usecases

import com.dema.store.common.domain.repositories.ProductsRepository
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

class GetCategories @Inject constructor(
    private val productsRepository: ProductsRepository
) {
    operator fun invoke() = productsRepository.getCategories()
}
package com.dema.store.common.domain.model.pagination

import com.dema.store.common.domain.model.product.Product

data class PaginatedProducts(
    val products: List<Product>,
    val pagination: Pagination
)
package com.dema.store.common.domain.model.pagination

import com.dema.store.common.domain.model.product.ProductWithDetails

data class PaginatedProducts(
    val products: List<ProductWithDetails>,
    val pagination: Pagination
)
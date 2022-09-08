package com.dema.store.home.domain.model

import com.dema.store.common.domain.model.product.ProductWithDetails

data class HomeProducts(
    val newProducts: List<ProductWithDetails>,
    val popularProducts: List<ProductWithDetails>,
    val saleProducts: List<ProductWithDetails>,
)
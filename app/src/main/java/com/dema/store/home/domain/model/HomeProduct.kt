package com.dema.store.home.domain.model

import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.home.presentation.model.UIHome

data class HomeProduct(
    val id: Long =0,
    val type: String,
    val product: ProductWithDetails,
){
}
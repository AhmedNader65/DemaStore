package com.dema.store.home.presentation.model

import com.dema.store.common.domain.model.category.Category
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.home.domain.model.HomeProduct

fun List<HomeProduct>.toUIModel(): UIHome {
    return UIHome(
        this.filter { it.type == "new" }.map {
            it.product.mapToView()
        },
        this.filter { it.type == "popular" }.map {
            it.product.mapToView()
        },
        this.filter { it.type == "sale" }.map {
            it.product.mapToView()
        }
    )
}

fun List<Product>.toUIProductModel(): List<UIProduct> {
    return map { it.mapToView() }
}

fun List<Category>.toUICategoryModel(): List<UIProductSlider> {
    return map { it.mapToView() }
}
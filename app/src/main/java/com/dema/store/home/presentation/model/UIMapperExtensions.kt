package com.dema.store.home.presentation.model

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
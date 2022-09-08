package com.dema.store.shopping.presentation

import com.dema.store.common.presentation.Event
import com.dema.store.common.presentation.model.UIProduct

data class StoreViewState(
    val loading: Boolean = true,
    val products: List<UIProduct> = emptyList(),
    val noMoreProducts: Boolean = false,
    val failure: Event<Throwable>? = null
)
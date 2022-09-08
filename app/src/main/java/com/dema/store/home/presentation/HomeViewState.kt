package com.dema.store.home.presentation

import com.dema.store.common.presentation.Event
import com.dema.store.home.presentation.model.UIHome

data class HomeViewState(
    val loading: Boolean = true,
    val uiHome: UIHome? = null,
    val failure: Event<Throwable>? = null
)
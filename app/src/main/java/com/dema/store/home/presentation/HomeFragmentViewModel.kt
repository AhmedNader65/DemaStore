package com.dema.store.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dema.store.common.domain.model.NetworkException
import com.dema.store.common.domain.model.NetworkUnavailableException
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.model.product.ProductWithDetails
import com.dema.store.common.presentation.Event
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.common.utils.createExceptionHandler
import com.dema.store.home.domain.usecases.GetHome
import com.dema.store.home.domain.usecases.RequestHomeProducts
import com.dema.store.home.domain.usecases.RequestNextPageOfProducts
import com.dema.store.home.presentation.model.UIHome
import com.dema.store.home.presentation.model.toUIModel
import com.dema.store.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(
    private val requestHomeProducts: RequestHomeProducts,
    private val getHome: GetHome
) :
    ViewModel() {

    init {
        subscribeToHomeUpdates()
    }

    private val _state = MutableStateFlow(HomeViewState())
    val state: StateFlow<HomeViewState> =
        _state.asStateFlow()

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.RequestInitialProductsList ->
                loadHome()
        }
    }

    private fun loadHome() {
        if (state.value.uiHome == null || (state.value.uiHome?.newProducts.isNullOrEmpty()) && (state.value.uiHome?.popularProducts.isNullOrEmpty()) && (state.value.uiHome?.saleProducts.isNullOrEmpty())) {
            loadHomeProducts()
        }
    }

    private fun subscribeToHomeUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {

                getHome()
                    .collect {
                        onNewProductsList(it.toUIModel())
                    }
            } catch (e: Exception) {
                onFailure(e)
            }

        }
    }

    private fun onNewProductsList(uiHome: UIHome) {
        Logger.d("Got more products!")


        _state.update { oldState ->
            oldState.copy(
                loading = false,
                uiHome = uiHome,
            )
        }
    }

    private fun loadHomeProducts() {
        val errorMessage = "Failed to fetch home products"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage)
            { onFailure(it) }
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            requestHomeProducts()
        }
    }

    private fun onFailure(failure: Throwable) {
        when (failure) {
            is NetworkException,
            is NetworkUnavailableException -> {
                _state.update { oldState ->
                    oldState.copy(
                        loading = false,
                        failure = Event(failure)
                    )
                }
            }
        }
    }
}

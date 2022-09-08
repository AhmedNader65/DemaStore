package com.dema.store.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dema.store.common.domain.model.NetworkException
import com.dema.store.common.domain.model.NetworkUnavailableException
import com.dema.store.common.presentation.Event
import com.dema.store.common.utils.createExceptionHandler
import com.dema.store.home.domain.usecases.RequestHomeProducts
import com.dema.store.home.domain.usecases.RequestNextPageOfProducts
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeFragmentViewModel @Inject constructor(private val requestHomeProducts: RequestHomeProducts) :
    ViewModel() {

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
        if (state.value.uiHome == null) {
            loadHomeProducts()
        }
    }

    private fun loadHomeProducts() {
        val errorMessage = "Failed to fetch home products"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage)
            { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
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

package com.dema.store.shopping.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dema.store.common.domain.model.NetworkException
import com.dema.store.common.domain.model.NetworkUnavailableException
import com.dema.store.common.domain.model.NoMoreProductsException
import com.dema.store.common.domain.model.pagination.Pagination
import com.dema.store.common.presentation.Event
import com.dema.store.common.presentation.model.UIProduct
import com.dema.store.common.utils.createExceptionHandler
import com.dema.store.home.domain.usecases.GetCategories
import com.dema.store.home.domain.usecases.GetProducts
import com.dema.store.home.domain.usecases.RequestCategories
import com.dema.store.home.domain.usecases.RequestNextPageOfProducts
import com.dema.store.home.presentation.model.*
import com.dema.store.utils.Logger
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class StoreFragmentViewModel @Inject constructor(
    private val requestNextPageOfProducts: RequestNextPageOfProducts,
    private val requestCategories: RequestCategories,
    private val getProducts: GetProducts,
    private val getCategories: GetCategories
) :
    ViewModel() {

    init {
        subscribeToProductsUpdates()
        subscribeToCategoryUpdates()
    }

    private val _state = MutableStateFlow(StoreViewState())
    val state: StateFlow<StoreViewState> =
        _state.asStateFlow()
    private val _categoriesState = MutableStateFlow(StoreViewState())
    val categories: StateFlow<StoreViewState> =
        _categoriesState.asStateFlow()
    private var currentPage = 0
    private var categoryId = 0L

    fun onEvent(event: ShoppingEvent) {
        when (event) {
            is ShoppingEvent.RequestInitialProductsList ->
                loadProducts()
        }
    }

    private fun loadProducts() {
        if (state.value.products.isEmpty()) {
            loadNextProductPage()
        }
        if (state.value.categories.isEmpty()) {
            loadCategories()
        }
    }

    private fun subscribeToCategoryUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getCategories()
                    .collect {
                        onCategoriesFetched(it.toUICategoryModel())
                    }
            } catch (e: Exception) {
                onFailure(e)
            }

        }
    }

    private fun subscribeToProductsUpdates() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                getProducts(categoryId)
                    .collect {
                        onNewProductsList(it.toUIProductModel())
                    }
            } catch (e: Exception) {
                onFailure(e)
            }

        }
    }

    private fun onNewProductsList(productsList: List<UIProduct>) {
        Logger.d("Got more products!")


        _state.update { oldState ->
            oldState.copy(
                loading = false,
                products = productsList,
            )

        }
    }

    private fun onCategoriesFetched(categoriesList: List<UIProductSlider>) {
        Logger.d("Got more Categories!")

        _categoriesState.update { oldState ->
            oldState.copy(
                loading = false,
                categories = categoriesList,
            )

        }
    }

    fun loadCategoryProducts(categoryId: Long = 0) {
        this.categoryId = categoryId
        subscribeToProductsUpdates()
    }

    private fun loadNextProductPage() {
        val errorMessage = "Failed to fetch home products"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage)
            { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            Logger.d("Requesting more products.")
            val pagination = requestNextPageOfProducts(++currentPage, category = categoryId)
            onPaginationInfoObtained(pagination)

        }
    }

    private fun loadCategories() {
        val errorMessage = "Failed to fetch categories"
        val exceptionHandler =
            viewModelScope.createExceptionHandler(errorMessage)
            { onFailure(it) }
        viewModelScope.launch(exceptionHandler) {
            Logger.d("Requesting categories.")
            requestCategories()

        }
    }

    private fun onPaginationInfoObtained(pagination: Pagination) {
        currentPage = pagination.currentPage
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
            is NoMoreProductsException -> {
                _state.update { oldState ->
                    oldState.copy(
                        noMoreProducts = true,
                        failure = Event(failure)
                    )
                }
            }
        }
    }
}

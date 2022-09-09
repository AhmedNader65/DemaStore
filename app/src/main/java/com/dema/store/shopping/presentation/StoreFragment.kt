package com.dema.store.shopping.presentation

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dema.store.R
import com.dema.store.common.domain.model.product.Image
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.presentation.CategoryAdapter
import com.dema.store.common.presentation.Event
import com.dema.store.common.presentation.ProductsAdapter
import com.dema.store.common.presentation.SliderAdapter
import com.dema.store.databinding.FragmentHomeBinding
import com.dema.store.databinding.FragmentStoreBinding
import com.dema.store.home.presentation.HomeViewState
import com.dema.store.shopping.presentation.ShoppingEvent
import com.dema.store.shopping.presentation.StoreFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class StoreFragment : Fragment() {

    private val viewModel: StoreFragmentViewModel by viewModels()
    private val binding get() = _binding!!

    private var _binding: FragmentStoreBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentStoreBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialAnimalsList()

    }

    private fun requestInitialAnimalsList() {
        viewModel.onEvent(ShoppingEvent.RequestInitialProductsList)
    }

    private fun setupUI() {
        val categoriesAdapter = createCategoriesAdapter()
        val productsAdapter = createProductsAdapter()
        setupCategoriesRecycler(categoriesAdapter)
        setupProductsRecycler(productsAdapter)
        subscribeToViewStateUpdates(productsAdapter, categoriesAdapter)
    }

    private fun setupCategoriesRecycler(categoriesAdapter: CategoryAdapter) {
        binding.categoriesAdapter.adapter = categoriesAdapter
    }

    private fun setupProductsRecycler(productsAdapter: ProductsAdapter) {
        binding.productsRecycler.adapter = productsAdapter
    }

    private fun createCategoriesAdapter(): CategoryAdapter {
        return CategoryAdapter(CategoryAdapter.CategoryClickListener {
            viewModel.loadCategoryProducts(it)
            Toast.makeText(requireContext(), "clicked category #$it", Toast.LENGTH_SHORT).show()
        })
    }

    private fun createProductsAdapter(): ProductsAdapter {
        return ProductsAdapter()
    }

    private fun subscribeToViewStateUpdates(
        productsAdapter: ProductsAdapter,
        categoriesAdapter: CategoryAdapter
    ) {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.state.collect {
                    updateProductsState(it, productsAdapter)
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.categories.collect {
                    updateCategoriesState(it, categoriesAdapter)
                }
            }
        }
    }

    private fun updateCategoriesState(
        state: StoreViewState,
        categoriesAdapter: CategoryAdapter
    ) {
        categoriesAdapter.submitList(state.categories)
        handleFailures(state.failure)
    }

    private fun updateProductsState(
        state: StoreViewState,
        productsAdapter: ProductsAdapter
    ) {
        binding.loading.isVisible = state.loading
        if (state.products.isNotEmpty())
            productsAdapter.submitList(state.products)
        handleFailures(state.failure)
    }

    private fun handleFailures(failure: Event<Throwable>?) {
        val unhandledFailure = failure?.getContentIfNotHandled() ?: return
        val fallbackMessage = getString(R.string.an_error_occurred)
        val snackbarMessage = if (unhandledFailure.message.isNullOrEmpty()) {
            fallbackMessage
        } else {
            unhandledFailure.message!! // 4
        }
        if (snackbarMessage.isNotEmpty()) {
            Snackbar.make(
                requireView(), snackbarMessage,
                Snackbar.LENGTH_SHORT
            ).show()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

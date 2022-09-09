package com.dema.store.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.dema.store.R
import com.dema.store.common.presentation.Event
import com.dema.store.common.presentation.ProductsAdapter
import com.dema.store.common.presentation.SliderAdapter
import com.dema.store.databinding.FragmentHomeBinding
import com.dema.store.shopping.presentation.ShoppingEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private val viewModel: HomeFragmentViewModel by viewModels()
    private val binding get() = _binding!!

    private var _binding: FragmentHomeBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        requestInitialHomeData()
    }

    private fun requestInitialHomeData() {
//        viewModel.onEvent(HomeEvent.RequestInitialProductsList)
    }


    private fun setupUI() {
        val popularAdapter = createProductsAdapter()
        val salesAdapter = createProductsAdapter()
        val slidersAdapter = createSliderAdapter()
        setupSlidersRecyclerView(slidersAdapter)
        setupPopularRecyclerView(popularAdapter)
        setupSalesRecyclerView(salesAdapter)
        subscribeToViewStateUpdates(slidersAdapter,popularAdapter,salesAdapter)
    }

    private fun setupSalesRecyclerView(salesAdapter: ProductsAdapter) {
        binding.saleRecycler.adapter = salesAdapter
    }

    private fun setupPopularRecyclerView(popularAdapter: ProductsAdapter) {

        binding.popularProductRecycler.adapter = popularAdapter
    }

    private fun setupSlidersRecyclerView(slidersAdapter: SliderAdapter) {

        binding.newCollectionRecycler.adapter = slidersAdapter
    }

    private fun subscribeToViewStateUpdates(
        newProductsAdapter: SliderAdapter,
        popularAdapter: ProductsAdapter,
        salesAdapter: ProductsAdapter
    ) {
        viewLifecycleOwner.lifecycleScope.launch {

            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED)
            {
                viewModel.state.collect {
                    updateScreenState(it, newProductsAdapter, popularAdapter, salesAdapter)
                }
            }
        }
    }

    private fun updateScreenState(
        state: HomeViewState,
        newProductsAdapter: SliderAdapter,
        popularAdapter: ProductsAdapter,
        salesAdapter: ProductsAdapter
    ) {
        binding.loading.isVisible = state.loading
        newProductsAdapter.submitList(state.uiHome?.newProducts ?: listOf())
        popularAdapter.submitList(state.uiHome?.popularProducts ?: listOf())
        salesAdapter.submitList(state.uiHome?.saleProducts ?: listOf())
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

    private fun createProductsAdapter(): ProductsAdapter {
        return ProductsAdapter()
    }

    private fun createSliderAdapter(): SliderAdapter {
        return SliderAdapter()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

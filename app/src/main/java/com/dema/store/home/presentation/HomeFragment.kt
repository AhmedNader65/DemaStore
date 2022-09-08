package com.dema.store.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.dema.store.common.presentation.ProductsAdapter
import com.dema.store.common.presentation.SliderAdapter
import com.dema.store.databinding.FragmentHomeBinding
import com.dema.store.shopping.presentation.ShoppingEvent
import dagger.hilt.android.AndroidEntryPoint

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
        requestInitialAnimalsList()

    }

    private fun requestInitialAnimalsList() {
        viewModel.onEvent(HomeEvent.RequestInitialProductsList)
    }


    private fun setupUI() {
        val popularAdapter = createProductsAdapter()
        val salesAdapter = createProductsAdapter()
        val slidersAdapter = createSliderAdapter()
        setupSlidersRecyclerView(slidersAdapter)
        setupPopularRecyclerView(popularAdapter)
        setupSalesRecyclerView(salesAdapter)
    }

    private fun setupSalesRecyclerView(salesAdapter: ProductsAdapter) {
        binding.saleRecycler.adapter = salesAdapter
        binding.newCollectionRecycler.setHasFixedSize(true)
    }

    private fun setupPopularRecyclerView(popularAdapter: ProductsAdapter) {

        binding.popularProductRecycler.adapter = popularAdapter
        binding.popularProductRecycler.setHasFixedSize(true)
    }

    private fun setupSlidersRecyclerView(slidersAdapter: SliderAdapter) {

        binding.newCollectionRecycler.adapter = slidersAdapter
        binding.newCollectionRecycler.setHasFixedSize(true)
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

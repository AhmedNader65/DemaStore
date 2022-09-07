package com.dema.store.common.home.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.dema.store.common.domain.model.product.Image
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.domain.presentation.ProductsAdapter
import com.dema.store.common.domain.presentation.SliderAdapter
import com.dema.store.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

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
        binding.saleRecycler.adapter = ProductsAdapter().apply {
            this.submitList(listOf(Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),))
        }
        binding.popularProductRecycler.adapter = ProductsAdapter().apply {
            this.submitList(listOf(Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),))
        }
        binding.newCollectionRecycler.adapter = SliderAdapter().apply {
            this.submitList(listOf(Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),Product(1,
                "sku",
                "TestingProduct",
                Image(1,"","","","","",""),
                50L,
                "Category",
                "50",
                "50",
                true,
                true,
                true),))
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

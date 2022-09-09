package com.dema.store.profile

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
import com.dema.store.common.domain.model.product.Image
import com.dema.store.common.domain.model.product.Product
import com.dema.store.common.presentation.CategoryAdapter
import com.dema.store.common.presentation.Event
import com.dema.store.common.presentation.ProductsAdapter
import com.dema.store.common.presentation.SliderAdapter
import com.dema.store.databinding.FragmentHomeBinding
import com.dema.store.databinding.FragmentProfileBinding
import com.dema.store.databinding.FragmentStoreBinding
import com.dema.store.home.presentation.HomeViewState
import com.dema.store.shopping.presentation.ShoppingEvent
import com.dema.store.shopping.presentation.StoreFragmentViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class ProfileFragment : Fragment() {

    private val viewModel: StoreFragmentViewModel by viewModels()
    private val binding get() = _binding!!

    private var _binding: FragmentProfileBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

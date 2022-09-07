package com.dema.store.common

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.dema.store.R
import com.dema.store.common.data.DemaStoreProductRepository
import com.dema.store.common.domain.repositories.ProductsRepository
import com.dema.store.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.shape.CornerFamily
import com.google.android.material.shape.MaterialShapeDrawable

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val navController by lazy { findNavController(R.id.nav_host_fragment) }
    private val appBarConfiguration by lazy {
        AppBarConfiguration(
            topLevelDestinationIds = setOf(
                R.id.home,
                R.id.store,
                R.id.basket,
                R.id.profile
            )
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupActionBar()
        setupBottomNav()
    }

    private fun setupBottomNav() {
        binding.bottomNavigation.setupWithNavController(navController)
    }

    private fun setupActionBar() {

        val radius = resources.getDimension(com.intuit.sdp.R.dimen._16sdp); //32dp

        val materialShapeDrawable = binding.appBarLayout.background as MaterialShapeDrawable
        materialShapeDrawable.shapeAppearanceModel = materialShapeDrawable.shapeAppearanceModel
            .toBuilder()
            .setBottomLeftCorner(CornerFamily.ROUNDED, radius)
            .setBottomRightCorner(CornerFamily.ROUNDED, radius)
            .build()

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            binding.title.text = destination.label
        }

    }

}
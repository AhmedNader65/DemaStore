package com.dema.store.common.data

import com.dema.store.common.data.api.DemaApi
import com.dema.store.common.data.cache.Cache
import com.dema.store.common.data.di.PreferencesModule
import com.dema.store.common.domain.repositories.ProductsRepository
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import org.junit.Assert.*
import org.junit.Rule

import org.junit.Test
import retrofit2.Retrofit
import javax.inject.Inject

@HiltAndroidTest
@UninstallModules(PreferencesModule::class) //not load the original PreferencesModule, replace it with a test module
class DemaStoreProductRepositoryTest {

    private val fakeServer = FakeServer()
    private lateinit var repository: ProductsRepository
    private lateinit var api: DemaApi

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Inject
    lateinit var cache: Cache

    @Inject
    lateinit var retrofitBuilder: Retrofit.Builder

    @Test
    fun getProducts() {
    }

    @Test
    fun getCategories() {
    }

    @Test
    fun requestMoreProducts() {
    }

    @Test
    fun requestCategories() {
    }

    @Test
    fun storeCategory() {
    }

    @Test
    fun storeProducts() {
    }
}
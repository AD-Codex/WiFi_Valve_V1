package com.example.wifi_valve_v01.ui.home

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.wifi_valve_v01.data.ApiClient
import com.example.wifi_valve_v01.data.product.ProductRepository
import com.example.wifi_valve_v01.data.productItem.ProductItemDB
import com.example.wifi_valve_v01.data.productItem.ProductItemRepository
import com.example.wifi_valve_v01.models.Product
import com.example.wifi_valve_v01.utils.TokenManager
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


class ProductViewModel(application: Application) : AndroidViewModel(application) {

    private val productRepository = ProductRepository(ApiClient.productApiService)

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products

    // Function to fetch products from the API
    fun fetchProducts() {
        viewModelScope.launch {
            val result = productRepository.fetchProducts()
            if (result.isSuccess) {
                _products.value = result.getOrNull() ?: emptyList()
            }
        }
    }

    private val tokenManager = TokenManager(application)
    private val productItemRepository = ProductItemRepository(ApiClient.productItemApiService)

    private val _productItems = MutableStateFlow<List<ProductItemDB>>(emptyList())
    val productItems: StateFlow<List<ProductItemDB>> = _productItems

    fun fetchProductItems() {
        val user = tokenManager.getUser()

        viewModelScope.launch {
            val result = productItemRepository.getProductItem(user.id)

            if (result.isSuccess) {
                _productItems.value = result.getOrDefault(emptyList())
            }
        }
    }

}
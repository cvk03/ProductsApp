package com.example.products.viewmodel

import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.products.MainActivity
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

sealed class ProductListResult {
    data class Success(val productList: List<Product>) : ProductListResult()
    data class Error(val errorMessage: String) : ProductListResult()
}

class ProductListViewModel(private val productService: ProductService) : ViewModel() {

    private val _products = MutableLiveData<ProductListResult>()
    val products: LiveData<ProductListResult> get() = _products

    init {
        viewModelScope.launch {
            fetchProducts()
        }
    }

    suspend fun fetchProducts() {
        try {
            val productListResponse = productService.getProducts()
            _products.value = ProductListResult.Success(productListResponse.products)
        } catch (e: Exception) {
            _products.value = ProductListResult.Error(e.localizedMessage ?: "Unknown error")
        }
    }
}

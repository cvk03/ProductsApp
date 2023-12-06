package com.example.products.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class ProductDetailsViewModelFactory(private val product: Product) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProductDetailsViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProductDetailsViewModel(product) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

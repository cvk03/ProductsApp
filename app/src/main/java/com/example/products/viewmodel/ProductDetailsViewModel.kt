package com.example.products.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class ProductDetailsViewModel(private val product: Product) : ViewModel() {
    private val _selectedProduct = MutableLiveData<Product>()
    val selectedProduct: LiveData<Product> get() = _selectedProduct

    private val _productImages = MutableLiveData<List<String>>()
    val productImages: LiveData<List<String>> get() = _productImages

    fun setProduct(product: Product) {
        _selectedProduct.value = product

        _productImages.value = product.images
    }
}
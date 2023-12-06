package com.example.products.viewmodel

import retrofit2.http.GET

interface ProductService {
    @GET("products")
    suspend fun getProducts(): ProductList
}
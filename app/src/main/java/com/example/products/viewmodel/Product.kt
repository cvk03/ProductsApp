package com.example.products.viewmodel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class ProductList(
    val products: List<Product>
)
@Parcelize
data class Product(val id: Int,
                   val title: String,
                   val description: String,
                   val price: Double,
                   val discountPercentage: Double,
                   val rating: Double,
                   val stock: Int,
                   val brand: String,
                   val category: String,
                   val thumbnail: String,
                   val images: List<String>
) : Parcelable


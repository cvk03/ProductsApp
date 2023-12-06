package com.example.products.RecyclerViewAdapter

import android.content.ContentProvider
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

import com.example.products.R
import com.example.products.viewmodel.Product


class ProductListAdapter(private val context: Context, private val productClickListener: OnProductClickListener) : RecyclerView.Adapter<ProductListAdapter.ViewHolder>() {

    private var products: List<Product> = emptyList()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tv_title: TextView = itemView.findViewById(R.id.tv_title)
        val tv_cost: TextView = itemView.findViewById(R.id.tv_cost)
        val tv_discount : TextView = itemView.findViewById(R.id.tv_discount)
        val iv_thumbnail : ImageView = itemView.findViewById(R.id.iv_thumbnail)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        Log.d("ProductListAdapter", "Creating ViewHolder with context: $context")
        val view = LayoutInflater.from(parent.context).inflate(R.layout.each_product, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val product = products[position]
        holder.tv_title.text = product.title
        holder.tv_cost.text =  "Price : Rs. " + product.price.toString()
        holder.tv_discount.text = "Discount : " + product.discountPercentage.toString() + "%"
        Glide.with(context)
            .load(product.thumbnail)
            .centerCrop()
            .placeholder(R.drawable.ic_launcher_foreground)
            .into(holder.iv_thumbnail);

        holder.itemView.setOnClickListener {
            productClickListener.onProductClick(product)
        }
    }

    override fun getItemCount():Int{
        Log.d("ProductListAdapter", "getItemCount: ${products.size}")
        return products.size}

    fun setProducts(products: List<Product>) {
        Log.d("ProductListAdapter", "setProducts: Setting new products")
        this.products = products
        notifyDataSetChanged()
    }

    interface OnProductClickListener {
        fun onProductClick(product: Product)
    }
}



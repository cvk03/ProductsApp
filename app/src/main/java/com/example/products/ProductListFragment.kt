package com.example.products

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.products.RecyclerViewAdapter.ProductListAdapter
import com.example.products.viewmodel.ProductListViewModel
import kotlinx.coroutines.InternalCoroutinesApi
import com.example.products.viewmodel.Product
import com.example.products.viewmodel.ProductListResult
import com.example.products.viewmodel.ProductListViewModelFactory
import com.example.products.viewmodel.ProductService
import com.example.products.viewmodel.RetrofitClient
import kotlinx.coroutines.launch


@InternalCoroutinesApi
class ProductListFragment : Fragment() {

    lateinit var productListViewModel: ProductListViewModel
    lateinit var productService: ProductService
    private lateinit var productListAdapter: ProductListAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("ProductListFragment", "onCreateView: Initializing productService and productListViewModel")
        productService = RetrofitClient.getInstance().create(ProductService::class.java)

        productListViewModel = ViewModelProvider(this, ProductListViewModelFactory(productService))
                .get(ProductListViewModel::class.java)

        productListAdapter = ProductListAdapter(requireContext(),object : ProductListAdapter.OnProductClickListener {

            override fun onProductClick(product: Product) {
                val fragmentManager = requireActivity().supportFragmentManager
                val productDetailsFragment = ProductDetailsFragment.newInstance(product)
                val transaction = fragmentManager.beginTransaction()
                transaction.replace(R.id.frame1, productDetailsFragment)
                transaction.addToBackStack(null)
                transaction.commit()
            }
        })

        val root = inflater.inflate(R.layout.fragment_product_list, container, false)
        val recyclerView: RecyclerView = root.findViewById(R.id.rv)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = productListAdapter

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Add the observer here
        productListViewModel.products.observe(viewLifecycleOwner, { result ->
            when (result) {
                is ProductListResult.Success -> {
                    // Handle success
                    productListAdapter.setProducts(result.productList)
                }
                is ProductListResult.Error -> {
                    // Handle error
                    Toast.makeText(requireContext(), result.errorMessage, Toast.LENGTH_LONG).show()
                }
            }
        })

        // Fetch products
        lifecycleScope.launch {
            Log.d("ProductListFragment", "Fetching products...")
            productListViewModel.fetchProducts()
        }
    }
}

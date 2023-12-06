package com.example.products

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.products.RecyclerViewAdapter.ImageAdapter
import com.example.products.databinding.FragmentProductDetailsBinding
import com.example.products.viewmodel.Product
import com.example.products.viewmodel.ProductDetailsViewModel
import com.example.products.viewmodel.ProductDetailsViewModelFactory


class ProductDetailsFragment : Fragment() {


    lateinit var productDetailsViewModel: ProductDetailsViewModel


    private lateinit var binding: FragmentProductDetailsBinding

    companion object {
        private const val ARG_PRODUCT = "product"

        fun newInstance(product: Product): ProductDetailsFragment {
            val fragment = ProductDetailsFragment()
            val args = Bundle()
            args.putParcelable(ARG_PRODUCT, product)
            fragment.arguments = args
            return fragment
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProductDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val product = arguments?.getParcelable<Product>(ARG_PRODUCT)

        val productDetailsViewModel = ViewModelProvider(this, ProductDetailsViewModelFactory(product!!)).get(ProductDetailsViewModel::class.java)



        product?.let { productDetailsViewModel.setProduct(it) }

        val recyclerView: RecyclerView = view.findViewById(R.id.rv_images)

        recyclerView.layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, true)

        val imageAdapter = ImageAdapter(requireContext()) // Pass your list of images here
        recyclerView.adapter = imageAdapter


        productDetailsViewModel.selectedProduct.observe(viewLifecycleOwner, { product ->
            // update UI with product details
            binding.tvTitle.text = product.title
            binding.tvCost.text = "Price : Rs. " + product.price.toString()
            binding.tvBrand.text = "Brand : " + product.brand
            binding.tvCategory.text = "Category : " + product.category
            binding.tvDiscount.text = "Discount : " + product.discountPercentage.toString() + "%"
            binding.tvRating.text = "Rating : " + product.rating.toString()
            binding.tvStock.text ="Stock : " +  product.stock.toString()
            binding.tvDescription.text =  "Description : "+ product.description
            Glide.with(requireContext())
                .load(product.thumbnail)
                .centerCrop()
                .placeholder(R.drawable.ic_launcher_foreground)
                .into(binding.ivThumbnail);

            // ...
        })
        productDetailsViewModel.productImages.observe(viewLifecycleOwner, { images ->
            // Update the RecyclerView with the list of images
            imageAdapter.submitList(images)
        })



    }
}
package com.example.products

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.appcompat.widget.Toolbar
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.products.RecyclerViewAdapter.ProductListAdapter
import com.example.products.viewmodel.ProductDetailsViewModel
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : AppCompatActivity() {


    @OptIn(InternalCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        Log.d("MainActivity", "onCreate: calling the replaceFragment function")

        val toolbar = findViewById<Toolbar>(R.id.toolbar)

// Set the Toolbar as the action bar for the activity
        setSupportActionBar(toolbar)


        val fragmentManager : FragmentManager = supportFragmentManager
        val fragmentTransaction : FragmentTransaction = fragmentManager.beginTransaction()
        val fragment1 = ProductListFragment()

        fragmentTransaction.add(R.id.frame1,fragment1)
            .addToBackStack("MyFragmentTag")
        fragmentTransaction.commit()


    }


}
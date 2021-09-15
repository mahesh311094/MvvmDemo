package com.qstest.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.qstest.R
import com.qstest.databinding.ActivityMainBinding
import com.qstest.view.adapter.ProductAdapter
import com.qstest.viewmodel.ProductEvent
import com.qstest.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val viewModel: ProductsViewModel by viewModel()
    private lateinit var productAdapter: ProductAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        viewModel.getIDs()
        viewModel.event.observe(this, { handleEvent(it) })
    }

    private fun handleEvent(event: ProductEvent) {
        when (event) {
            is ProductEvent.ProductIDsAPISuccess -> getProduct(event.productIDs)
        }
    }

    private fun getProduct(productIDs:ArrayList<String>){
        productAdapter = ProductAdapter(this,viewModel,this)
        binding.productRecyclerView.adapter = productAdapter
        viewModel.getProduct(productIDs)
    }
}
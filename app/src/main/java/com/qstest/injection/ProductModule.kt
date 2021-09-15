package com.qstest.injection

import com.qstest.api.ProductService
import com.qstest.api.RetrofitProvider
import com.qstest.repository.ProductsRepository
import com.qstest.utils.Constants
import com.qstest.viewmodel.ProductsViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val productModule = module {

    factory {
        val productService = RetrofitProvider
            .provideRetrofit(Constants.BASE_URL, get())
            .create(ProductService::class.java)

        ProductsRepository(productService)
    }

    viewModel {
        ProductsViewModel(get(), get())
    }
}
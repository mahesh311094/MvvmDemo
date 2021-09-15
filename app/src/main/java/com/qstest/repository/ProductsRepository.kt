package com.qstest.repository

import com.qstest.api.ProductService
import com.qstest.utils.safeApiCall

class ProductsRepository constructor(private val productService: ProductService) {
    suspend fun getProductIDs() = safeApiCall { productService.getProductIDs() }
}
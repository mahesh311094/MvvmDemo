package com.qstest.api

import com.qstest.model.AllProductsIDs
import retrofit2.Response
import retrofit2.http.GET

interface ProductService {

    @GET("product-ids")
    suspend fun getProductIDs(): Response<AllProductsIDs>
}
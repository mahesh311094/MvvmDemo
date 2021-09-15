package com.qstest.viewmodel

import com.qstest.model.AllProductsIDs
import retrofit2.Response

sealed class ProductEvent {
    data class APIError(val throwable: Throwable) : ProductEvent()
    data class ResponseError(val response: Response<*>) : ProductEvent()
    data class ProductIDsAPISuccess(val productIDs: AllProductsIDs) : ProductEvent()
}
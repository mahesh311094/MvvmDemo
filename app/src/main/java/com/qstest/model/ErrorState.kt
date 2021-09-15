package com.qstest.model

import retrofit2.Response

sealed class ErrorState {
    data class GenericError(val exception: Throwable) : ErrorState()
    data class ErrorResponse(val response: Response<*>) : ErrorState()
}

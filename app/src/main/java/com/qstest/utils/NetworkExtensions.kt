package com.qstest.utils

import com.qstest.model.ErrorState
import com.qstest.model.ResultType
import retrofit2.Response

suspend fun <T : Any> safeApiCall(apiCall: suspend () -> Response<T>): ResultType<T> {
    return try {
        apiCall().toResult()
    } catch (throwable: Throwable) {
        ResultType.Error(ErrorState.GenericError(throwable))
    }
}


fun <T : Any> Response<T>.toResult(): ResultType<T> {
    return when {
        isSuccessful -> body()?.let {
            ResultType.Success(it)
        }
            ?: ResultType.Error(ErrorState.GenericError(IllegalStateException("Empty body in response.")))
        else -> ResultType.Error(ErrorState.ErrorResponse(this))
    }
}
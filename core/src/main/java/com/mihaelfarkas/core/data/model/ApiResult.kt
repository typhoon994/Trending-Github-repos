package com.mihaelfarkas.core.data.model

sealed class ApiResult<T> {
    class Success<T>(val data: List<T>) : ApiResult<T>()
    class Loading<T> : ApiResult<T>()
    class Error<T>(val throwable: Throwable) : ApiResult<T>()
}

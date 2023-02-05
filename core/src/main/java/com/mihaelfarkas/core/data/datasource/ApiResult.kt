package com.mihaelfarkas.core.data.datasource

sealed class ApiResult<T>(val data: List<T>) {
    class Success<T>(data: List<T>) : ApiResult<T>(data)
    class Loading<T>(data: List<T>) : ApiResult<T>(data)
    class Error<T>(val throwable: Throwable, data: List<T>) : ApiResult<T>(data)
}

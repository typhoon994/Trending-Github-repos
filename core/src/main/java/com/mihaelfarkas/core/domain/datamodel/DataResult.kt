package com.mihaelfarkas.core.domain.datamodel

sealed class DataResult<T>(val data: List<T>) {
    class Success<T>(data: List<T>) : DataResult<T>(data)
    class Loading<T>(data: List<T>) : DataResult<T>(data)
    class Error<T>(val throwable: Throwable, data: List<T>) : DataResult<T>(data)
}

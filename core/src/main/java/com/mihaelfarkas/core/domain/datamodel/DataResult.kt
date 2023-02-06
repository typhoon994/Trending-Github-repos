package com.mihaelfarkas.core.domain.datamodel

sealed class DataResult<T> {
    class Success<T>(val data: List<T>) : DataResult<T>()
    class Loading<T> : DataResult<T>()
    class Error<T>(val throwable: Throwable) : DataResult<T>()
}

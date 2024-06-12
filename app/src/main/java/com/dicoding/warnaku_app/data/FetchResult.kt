package com.dicoding.warnaku_app.data

sealed class FetchResult<out T> {
    data class Success<out T>(val data: T) : FetchResult<T>()
    data class Error(val error: String?) : FetchResult<Nothing>()
    data object Loading : FetchResult<Nothing>()
}
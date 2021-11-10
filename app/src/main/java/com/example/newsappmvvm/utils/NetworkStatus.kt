package com.example.newsappmvvm.utils

import okhttp3.ResponseBody

sealed class NetworkStatus<out T> {
    data class Success<out T>(val data: T?) : NetworkStatus<T>()
    object Loading : NetworkStatus<Nothing>()
    data class Failure(
        val isNetworkError: Boolean,
        val errorCode: Int?,
        val errorBody: ResponseBody?
    ) : NetworkStatus<Nothing>()

    fun data() = if (this is Success) data else null
}

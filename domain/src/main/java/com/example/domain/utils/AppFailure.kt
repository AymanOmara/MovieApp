package com.example.domain.utils

sealed class AppFailure {
    data object NetworkUnavailable : AppFailure()
    data class Http(val code: Int?) : AppFailure()
    data object Decoding : AppFailure()
    data object CacheEmpty : AppFailure()
    data class Unknown(val cause: Throwable?) : AppFailure()
}

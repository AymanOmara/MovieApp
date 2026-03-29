package com.example.domain.utils

sealed class Result<out T> {
    data class Success<out T>(val data: T) : Result<T>()
    data class Error(val failure: AppFailure) : Result<Nothing>()
    data object Loading : Result<Nothing>()
}

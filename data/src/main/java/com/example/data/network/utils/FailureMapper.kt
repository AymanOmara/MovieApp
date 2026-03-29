package com.example.data.network.utils

import com.example.domain.utils.AppFailure
import com.google.gson.JsonSyntaxException
import retrofit2.HttpException
import java.io.IOException

fun Throwable.toAppFailure(): AppFailure = when (this) {
    is HttpException -> AppFailure.Http(code())
    is JsonSyntaxException -> AppFailure.Decoding
    is IOException -> AppFailure.NetworkUnavailable
    else -> AppFailure.Unknown(this)
}

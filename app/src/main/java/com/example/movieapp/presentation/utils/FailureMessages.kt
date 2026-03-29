package com.example.movieapp.presentation.utils

import android.content.Context
import androidx.annotation.StringRes
import com.example.domain.utils.AppFailure
import com.example.movieapp.R

fun AppFailure.userMessage(context: Context, @StringRes featureFallback: Int): String = when (this) {
    AppFailure.NetworkUnavailable -> context.getString(R.string.error_connection)
    is AppFailure.Http -> context.getString(R.string.error_generic)
    AppFailure.Decoding -> context.getString(R.string.error_generic)
    AppFailure.CacheEmpty -> context.getString(R.string.error_no_network_no_cache)
    is AppFailure.Unknown -> cause?.message?.takeIf { it.isNotBlank() }
        ?: context.getString(featureFallback)
}

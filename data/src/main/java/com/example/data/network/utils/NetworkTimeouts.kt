package com.example.data.network.utils

/**
 * OkHttp timeout values in **seconds**.
 */
data class NetworkTimeouts(
    val connectTimeout: Long,
    val readTimeout: Long,
    val writeTimeout: Long,
) {
    companion object {
        fun default(): NetworkTimeouts = NetworkTimeouts(
            connectTimeout = 30,
            readTimeout = 30,
            writeTimeout = 30,
        )
    }
}

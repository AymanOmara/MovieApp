package com.example.data.network.interceptors

import com.example.data.utils.Constants
import okhttp3.Interceptor
import okhttp3.Response

class HeaderInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {

        val request = chain.request().newBuilder()
            .addHeader("Authorization", "Bearer ${Constants.API_TOKEN}")
            .build()

        return chain.proceed(request)
    }
}
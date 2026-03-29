package com.example.data.utils

import com.example.data.Secrets

object Constants {
    private const val PACKAGE_NAME = "com.example.data"

    private val secrets by lazy { Secrets() }

    val IMAGE_BASE_URL: String by lazy { secrets.getimageBaseUrl(PACKAGE_NAME) }

    val BASE_URL: String by lazy { secrets.getbaseUrl(PACKAGE_NAME) }
    val API_TOKEN: String by lazy { secrets.getapiToken(PACKAGE_NAME) }
}

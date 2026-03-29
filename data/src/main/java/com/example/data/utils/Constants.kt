package com.example.data.utils

import com.example.data.Secrets

object Constants {
    private const val PACKAGE_NAME = "com.example.data"

    val IMAGE_BASE_URL: String by lazy { Secrets().getimageBaseUrl(PACKAGE_NAME) }

    val BASE_URL: String by lazy { Secrets().getbaseUrl(PACKAGE_NAME) }
    val API_TOKEN: String by lazy { Secrets().getapiToken(PACKAGE_NAME) }
}

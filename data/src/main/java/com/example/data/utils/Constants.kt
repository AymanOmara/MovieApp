package com.example.data.utils

object Constants {
    const val API_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIzZDFjYzMxN2NiNTE2NTFiNDdkNzVjY2QxNzY2MTNlMyIsIm5iZiI6MTc3MTg0NTM3OC45NzcsInN1YiI6IjY5OWMzNzAyZGVhM2UwMjkyOTgwZjcwOCIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.NzIA4rjVVts5IRfMpAsCswc-ZfeRE15TwHJrLFz_rY8"

    private const val ENCRYPTED_BASE_URL = "fW1JmiZlMsp8NaA2mGPb3GPiDNPvtjA5YEd0vmoPDog="
    private const val ENCRYPTED_IMAGE_BASE_URL = "A8hN7Yoobw77lEmL/obiwOGTlxZvMW9/bQLeon/A//tWTmzXejYYZIW1+tYX7RQy"

    val BASE_URL: String by lazy { UrlEncryption.decrypt(ENCRYPTED_BASE_URL) }
    val IMAGE_BASE_URL: String by lazy { UrlEncryption.decrypt(ENCRYPTED_IMAGE_BASE_URL) }
}
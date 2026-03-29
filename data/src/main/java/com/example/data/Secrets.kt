package com.example.data

class Secrets {

    companion object {
        init {
            System.loadLibrary("secrets")
        }
    }

    external fun getbaseUrl(packageName: String): String

    external fun getimageBaseUrl(packageName: String): String

    external fun getapiToken(packageName: String): String
}
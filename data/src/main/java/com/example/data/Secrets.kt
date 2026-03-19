package com.example.data

class Secrets {

    // Method calls will be added by gradle task hideSecret
    // Example : external fun getWellHiddenSecret(packageName: String): String

    companion object {
        init {
            System.loadLibrary("secrets")
        }
    }

    external fun getbaseUrl(packageName: String): String

    external fun getimageBaseUrl(packageName: String): String

    external fun getapiToken(packageName: String): String
}
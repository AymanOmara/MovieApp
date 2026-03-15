package com.example.data.utils

import java.util.Base64
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec
import javax.crypto.spec.SecretKeySpec

internal object UrlEncryption {
    private const val ALGORITHM = "AES/CBC/PKCS5Padding"
    private const val KEY = "MovieAppKey12345"
    private const val IV = "RandomInitVector"

    fun encrypt(value: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(KEY.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(IV.toByteArray())
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec)
        val encrypted = cipher.doFinal(value.toByteArray())
        return Base64.getEncoder().encodeToString(encrypted)
    }

    fun decrypt(encrypted: String): String {
        val cipher = Cipher.getInstance(ALGORITHM)
        val keySpec = SecretKeySpec(KEY.toByteArray(), "AES")
        val ivSpec = IvParameterSpec(IV.toByteArray())
        cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec)
        val decoded = Base64.getDecoder().decode(encrypted)
        return String(cipher.doFinal(decoded))
    }
}

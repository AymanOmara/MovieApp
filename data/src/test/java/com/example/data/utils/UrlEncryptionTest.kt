package com.example.data.utils

import org.junit.Assert.assertEquals
import org.junit.Test

class UrlEncryptionTest {

    @Test
    fun `encrypt and decrypt should return original value`() {
        val original = "https://api.themoviedb.org/3/"
        val encrypted = UrlEncryption.encrypt(original)
        val decrypted = UrlEncryption.decrypt(encrypted)

        assertEquals(original, decrypted)
    }

    @Test
    fun `encrypted value should not equal original`() {
        val original = "https://api.themoviedb.org/3/"
        val encrypted = UrlEncryption.encrypt(original)

        assert(encrypted != original)
    }
}

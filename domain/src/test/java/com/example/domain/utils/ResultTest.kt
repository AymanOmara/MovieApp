package com.example.domain.utils

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {

    @Test
    fun success_holdsData() {
        val data = "test"
        val result = Result.Success(data)
        assertEquals(data, result.data)
    }

    @Test
    fun error_holdsFailure() {
        val throwable = RuntimeException("error")
        val failure = AppFailure.Unknown(throwable)
        val result = Result.Error(failure)
        assertEquals(failure, result.failure)
    }

    @Test
    fun loading_isSingleton() {
        val a = Result.Loading
        val b = Result.Loading
        assertTrue(a === b)
    }
}

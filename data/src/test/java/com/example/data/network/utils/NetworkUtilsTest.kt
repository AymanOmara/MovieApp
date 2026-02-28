package com.example.data.network.utils

import com.example.domain.utils.Result
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class NetworkUtilsTest {

    private lateinit var networkUtils: NetworkUtils

    @Before
    fun setup() {
        networkUtils = NetworkUtils()
    }

    @Test
    fun safeApiCall_emitsLoadingThenSuccess() = runTest {
        val flow = networkUtils.safeApiCall { "result" }
        val results = mutableListOf<Result<String>>()
        flow.collect { results.add(it) }
        assertEquals(2, results.size)
        assertTrue(results[0] is Result.Loading)
        assertEquals("result", (results[1] as Result.Success).data)
    }

    @Test
    fun safeApiCall_emitsLoadingThenErrorOnException() = runTest {
        val exception = RuntimeException("error")
        val flow = networkUtils.safeApiCall<String> { throw exception }
        val results = mutableListOf<Result<String>>()
        flow.collect { results.add(it) }
        assertEquals(2, results.size)
        assertTrue(results[0] is Result.Loading)
        assertTrue(results[1] is Result.Error)
        assertEquals(exception, (results[1] as Result.Error).error)
    }

    @Test
    fun safeApiCall_firstEmitsLoading() = runTest {
        val flow = networkUtils.safeApiCall { 42 }
        val first = flow.first()
        assertTrue(first is Result.Loading)
    }
}

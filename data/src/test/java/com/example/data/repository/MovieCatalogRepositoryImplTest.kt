package com.example.data.repository

import app.cash.turbine.test
import com.example.data.local.DiscoverPageCacheDao
import com.example.data.local.PopularMoviesCacheDao
import com.example.data.network.MoviesWebServices
import com.example.data.network.dto.BaseResponse
import com.example.data.network.dto.MovieDto
import android.content.Context
import com.example.data.R
import com.example.data.network.utils.NetworkUtils
import com.example.domain.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MovieCatalogRepositoryImplTest {

    private lateinit var api: MoviesWebServices
    private lateinit var popularMoviesCacheDao: PopularMoviesCacheDao
    private lateinit var discoverPageCacheDao: DiscoverPageCacheDao
    private lateinit var context: Context
    private lateinit var networkUtils: NetworkUtils
    private lateinit var repository: MovieCatalogRepositoryImpl

    @Before
    fun setup() {
        context = mockk()
        every { context.getString(R.string.error_no_network_no_cache) } returns "No network connection and no cached data available"
        api = mockk()
        popularMoviesCacheDao = mockk()
        discoverPageCacheDao = mockk(relaxed = true)
        networkUtils = mockk()
        repository = MovieCatalogRepositoryImpl(
            context,
            api,
            networkUtils,
            popularMoviesCacheDao,
            discoverPageCacheDao
        )
    }

    @Test
    fun getPopularMovies_emptyCache_emitsLoadingThenSuccessFromApi() = runTest {
        // posterPath null so toMovie() does not read IMAGE_BASE_URL / native Secrets on JVM
        val movieDto = MovieDto(1, "Title", null, null, "Overview", "2024-01-01", 8.0, 100)
        val response = BaseResponse(1, listOf(movieDto), 1, 1)
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { popularMoviesCacheDao.getAllOnce() } returns emptyList()
        coEvery { api.getPopularMovies() } returns response
        coEvery { popularMoviesCacheDao.deleteAll() } just Runs
        coEvery { popularMoviesCacheDao.insertAll(any()) } just Runs

        repository.getPopularMovies().test {
            assertTrue(awaitItem() is Result.Loading)
            val success = awaitItem() as Result.Success
            assertEquals(1, success.data.size)
            assertEquals("Title", success.data[0].title)
            awaitComplete()
        }
    }

    @Test
    fun getPopularMovies_apiFailsAndEmptyCache_emitsError() = runTest {
        every { networkUtils.isNetworkAvailable() } returns true
        coEvery { popularMoviesCacheDao.getAllOnce() } returns emptyList()
        coEvery { api.getPopularMovies() } throws RuntimeException("Network error")

        repository.getPopularMovies().test {
            assertTrue(awaitItem() is Result.Loading)
            assertTrue(awaitItem() is Result.Error)
            awaitComplete()
        }
    }
}

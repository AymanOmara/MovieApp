package com.example.data.repository

import app.cash.turbine.test
import com.example.data.local.DiscoverPageCacheDao
import com.example.data.local.MovieDAO
import com.example.data.local.PopularMoviesCacheDao
import com.example.data.network.MoviesWebServices
import com.example.data.network.dto.BaseResponse
import com.example.data.network.dto.MovieDto
import com.example.domain.model.Movie
import com.example.domain.utils.Result
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import io.mockk.verify
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class MoviesRepositoryImplTest {

    private lateinit var api: MoviesWebServices
    private lateinit var movieDao: MovieDAO
    private lateinit var popularMoviesCacheDao: PopularMoviesCacheDao
    private lateinit var discoverPageCacheDao: DiscoverPageCacheDao
    private lateinit var repository: MoviesRepositoryImpl

    @Before
    fun setup() {
        api = mockk()
        movieDao = mockk(relaxed = true)
        popularMoviesCacheDao = mockk()
        discoverPageCacheDao = mockk(relaxed = true)
        val networkUtils = com.example.data.network.utils.NetworkUtils()
        repository = MoviesRepositoryImpl(api, networkUtils, movieDao, popularMoviesCacheDao, discoverPageCacheDao)
    }

    @Test
    fun getPopularMovies_emptyCache_emitsLoadingThenSuccessFromApi() = runTest {
        val movieDto = MovieDto(1, "Title", "/p.jpg", null, "Overview", "2024-01-01", 8.0, 100)
        val response = BaseResponse(1, listOf(movieDto), 1, 1)
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
        coEvery { popularMoviesCacheDao.getAllOnce() } returns emptyList()
        coEvery { api.getPopularMovies() } throws RuntimeException("Network error")

        repository.getPopularMovies().test {
            assertTrue(awaitItem() is Result.Loading)
            assertTrue(awaitItem() is Result.Error)
            awaitComplete()
        }
    }

    @Test
    fun getAllMovies_returnsMappedMoviesFromDao() = runTest {
        val local = com.example.data.local.MovieLocal(1, "T", "/p", "O", "2024-01-01", 8.0, 100)
        every { movieDao.getAll() } returns flowOf(listOf(local))

        repository.getAllMovies().test {
            val list = awaitItem()
            assertEquals(1, list.size)
            assertEquals("T", list[0].title)
            awaitComplete()
        }
    }

    @Test
    fun isMovieStored_returnsTrueWhenStored() = runTest {
        every { movieDao.isMovieStored(1) } returns flowOf(1)

        repository.isMovieStored(1).test {
            assertTrue(awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun isMovieStored_returnsFalseWhenNotStored() = runTest {
        every { movieDao.isMovieStored(1) } returns flowOf(0)

        repository.isMovieStored(1).test {
            assertEquals(false, awaitItem())
            awaitComplete()
        }
    }

    @Test
    fun saveMovie_insertsIntoDao() = runTest {
        val movie = Movie.preview()
        repository.saveMovie(movie)
        verify { movieDao.insert(any()) }
    }

    @Test
    fun deleteMovie_deletesFromDao() = runTest {
        val movie = Movie.preview()
        repository.deleteMovie(movie)
        verify { movieDao.delete(any()) }
    }
}

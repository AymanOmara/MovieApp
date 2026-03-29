package com.example.data.repository

import app.cash.turbine.test
import com.example.data.local.MovieDAO
import com.example.domain.entity.Movie
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class WatchlistRepositoryImplTest {

    private lateinit var movieDao: MovieDAO
    private lateinit var repository: WatchlistRepositoryImpl

    @Before
    fun setup() {
        movieDao = mockk(relaxed = true)
        repository = WatchlistRepositoryImpl(movieDao)
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
        coVerify { movieDao.insert(any()) }
    }

    @Test
    fun deleteMovie_deletesFromDao() = runTest {
        val movie = Movie.preview()
        repository.deleteMovie(movie)
        coVerify { movieDao.delete(any()) }
    }
}

package com.example.movieapp.presentation.watchlist.viewmodel

import com.example.domain.model.Movie
import com.example.domain.repository.MoviesRepository
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.movieapp.util.MainDispatcherRule

class WatchlistViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: MoviesRepository
    private lateinit var viewModel: WatchlistViewModel

    @Before
    fun setup() {
        repository = mockk()
    }

    @Test
    fun init_emitsLoadingThenSuccessWithMovies() = runBlocking {
        val movies = listOf(Movie.preview())
        every { repository.getAllMovies() } returns flowOf(movies)
        viewModel = WatchlistViewModel(repository)
        delay(100)

        val state = viewModel.uiState.value
        assertTrue(state is WatchlistUiState.Success)
        assertEquals(movies, (state as WatchlistUiState.Success).movies)
    }

    @Test
    fun init_whenEmpty_emitsSuccessWithEmptyList() = runBlocking {
        every { repository.getAllMovies() } returns flowOf(emptyList())
        viewModel = WatchlistViewModel(repository)
        delay(100)

        val state = viewModel.uiState.value
        assertTrue(state is WatchlistUiState.Success)
        assertTrue((state as WatchlistUiState.Success).movies.isEmpty())
    }

    @Test
    fun onEvent_refresh_reloadsWatchlist() = runBlocking {
        every { repository.getAllMovies() } returns flowOf(listOf(Movie.preview()))
        viewModel = WatchlistViewModel(repository)
        delay(100)

        every { repository.getAllMovies() } returns flowOf(emptyList())
        viewModel.onEvent(WatchlistEvent.Refresh)
        delay(100)

        val state = viewModel.uiState.value
        assertTrue(state is WatchlistUiState.Success)
        assertTrue((state as WatchlistUiState.Success).movies.isEmpty())
    }

    @Test
    fun onEvent_removeMovie_deletesAndReloads() = runBlocking {
        val movie = Movie.preview()
        every { repository.getAllMovies() } returns flowOf(listOf(movie)) andThen flowOf(emptyList())
        coEvery { repository.deleteMovie(movie) } just Runs
        viewModel = WatchlistViewModel(repository)
        delay(100)

        viewModel.onEvent(WatchlistEvent.RemoveMovie(movie))
        delay(100)

        val state = viewModel.uiState.value
        assertTrue(state is WatchlistUiState.Success)
        assertTrue((state as WatchlistUiState.Success).movies.isEmpty())
        coVerify { repository.deleteMovie(movie) }
    }
}

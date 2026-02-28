package com.example.movieapp.presentation.details.viewmodel

import com.example.domain.model.Cast
import com.example.domain.model.Movie
import com.example.domain.model.MovieDetails
import com.example.domain.repository.MoviesRepository
import com.example.domain.utils.Result
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import io.mockk.Runs
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.movieapp.util.MainDispatcherRule

class MovieDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: MoviesRepository
    private val movie = Movie.preview()
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        repository = mockk()
    }

    @Test
    fun init_loadsDetailsCastAndSimilar() = runTest {
        val details = MovieDetails.preview()
        val cast = Cast.previewList()
        val similar = Movie.previewList()
        every { repository.getMovieDetails(movie.id) } returns flowOf(Result.Success(details))
        every { repository.getMovieCredits(movie.id) } returns flowOf(Result.Success(cast))
        every { repository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(similar))
        every { repository.isMovieStored(movie.id) } returns flowOf(false)
        viewModel = MovieDetailsViewModel(repository, movie)
        yield()

        val state = viewModel.uiState.value
        assertEquals(details, state.movieDetails)
        assertEquals(cast, state.cast)
        assertEquals(similar, state.similarMovies)
        assertFalse(state.isDetailsLoading)
        assertFalse(state.isCastLoading)
        assertFalse(state.isSimilarLoading)
    }

    @Test
    fun uiState_whenDetailsLoaded_hasMovieDetails() = runTest {
        val details = MovieDetails.preview()
        every { repository.getMovieDetails(movie.id) } returns flowOf(Result.Success(details))
        every { repository.getMovieCredits(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { repository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { repository.isMovieStored(movie.id) } returns flowOf(false)
        viewModel = MovieDetailsViewModel(repository, movie)
        yield()

        assertEquals(details, viewModel.uiState.value.movieDetails)
        assertFalse(viewModel.uiState.value.isDetailsLoading)
    }

    @Test
    fun onEvent_retryDetails_reloadsDetails() = runTest {
        every { repository.getMovieDetails(movie.id) } returns flowOf(Result.Success(MovieDetails.preview()))
        every { repository.getMovieCredits(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { repository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { repository.isMovieStored(movie.id) } returns flowOf(false)
        viewModel = MovieDetailsViewModel(repository, movie)
        yield()

        viewModel.onEvent(MovieDetailsEvent.RetryDetails)
        yield()

        assertEquals(MovieDetails.preview(), viewModel.uiState.value.movieDetails)
    }

    @Test
    fun onEvent_toggleWatchlist_savesMovieWhenNotInWatchlist() = runTest {
        every { repository.getMovieDetails(movie.id) } returns flowOf(Result.Success(MovieDetails.preview()))
        every { repository.getMovieCredits(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { repository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { repository.isMovieStored(movie.id) } returns flowOf(false)
        coEvery { repository.saveMovie(movie) } just Runs
        viewModel = MovieDetailsViewModel(repository, movie)
        yield()

        viewModel.onEvent(MovieDetailsEvent.ToggleWatchlist)
        yield()
        coVerify { repository.saveMovie(movie) }
    }
}

package com.example.movieapp.presentation.details.viewmodel

import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import com.example.domain.repository.MovieDetailsRepository
import com.example.domain.repository.WatchlistRepository
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
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import android.content.Context
import com.example.movieapp.R
import com.example.movieapp.util.MainDispatcherRule

class MovieDetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var movieDetailsRepository: MovieDetailsRepository
    private lateinit var watchlistRepository: WatchlistRepository
    private lateinit var context: Context
    private val movie = Movie.preview()
    private lateinit var viewModel: MovieDetailsViewModel

    @Before
    fun setup() {
        movieDetailsRepository = mockk()
        watchlistRepository = mockk()
        context = mockk()
        every { context.getString(R.string.error_movie_details) } returns "Failed to load movie details"
        every { context.getString(R.string.error_cast) } returns "Failed to load cast"
        every { context.getString(R.string.error_similar_movies) } returns "Failed to load similar movies"
    }

    @Test
    fun init_loadsDetailsCastAndSimilar() = runTest {
        val details = MovieDetails.preview()
        val cast = Cast.previewList()
        val similar = Movie.previewList()
        every { movieDetailsRepository.getMovieDetails(movie.id) } returns flowOf(Result.Success(details))
        every { movieDetailsRepository.getMovieCredits(movie.id) } returns flowOf(Result.Success(cast))
        every { movieDetailsRepository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(similar))
        every { watchlistRepository.isMovieStored(movie.id) } returns flowOf(false)
        viewModel = MovieDetailsViewModel(movieDetailsRepository, watchlistRepository, context, movie)
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
        every { movieDetailsRepository.getMovieDetails(movie.id) } returns flowOf(Result.Success(details))
        every { movieDetailsRepository.getMovieCredits(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { movieDetailsRepository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { watchlistRepository.isMovieStored(movie.id) } returns flowOf(false)
        viewModel = MovieDetailsViewModel(movieDetailsRepository, watchlistRepository, context, movie)
        yield()

        assertEquals(details, viewModel.uiState.value.movieDetails)
        assertFalse(viewModel.uiState.value.isDetailsLoading)
    }

    @Test
    fun onEvent_retryDetails_reloadsDetails() = runTest {
        every { movieDetailsRepository.getMovieDetails(movie.id) } returns flowOf(Result.Success(MovieDetails.preview()))
        every { movieDetailsRepository.getMovieCredits(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { movieDetailsRepository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { watchlistRepository.isMovieStored(movie.id) } returns flowOf(false)
        viewModel = MovieDetailsViewModel(movieDetailsRepository, watchlistRepository, context, movie)
        yield()

        viewModel.onEvent(MovieDetailsEvent.RetryDetails)
        yield()

        assertEquals(MovieDetails.preview(), viewModel.uiState.value.movieDetails)
    }

    @Test
    fun onEvent_toggleWatchlist_savesMovieWhenNotInWatchlist() = runTest {
        every { movieDetailsRepository.getMovieDetails(movie.id) } returns flowOf(Result.Success(MovieDetails.preview()))
        every { movieDetailsRepository.getMovieCredits(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { movieDetailsRepository.getSimilarMovies(movie.id) } returns flowOf(Result.Success(emptyList()))
        every { watchlistRepository.isMovieStored(movie.id) } returns flowOf(false)
        coEvery { watchlistRepository.saveMovie(movie) } just Runs
        viewModel = MovieDetailsViewModel(movieDetailsRepository, watchlistRepository, context, movie)
        yield()

        viewModel.onEvent(MovieDetailsEvent.ToggleWatchlist)
        yield()
        coVerify { watchlistRepository.saveMovie(movie) }
    }
}

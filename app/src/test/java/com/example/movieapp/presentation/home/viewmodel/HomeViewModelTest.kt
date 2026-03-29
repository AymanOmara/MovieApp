package com.example.movieapp.presentation.home.viewmodel

import com.example.domain.entity.Movie
import com.example.domain.repository.MovieCatalogRepository
import com.example.domain.utils.AppFailure
import com.example.domain.utils.Result
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.yield
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import androidx.paging.PagingData
import android.content.Context
import com.example.movieapp.R
import com.example.movieapp.util.MainDispatcherRule

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var context: Context
    private lateinit var repository: MovieCatalogRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
        context = mockk()
        every { context.getString(R.string.error_generic) } returns "Something went wrong. Please try again."
        repository = mockk()
        every { repository.getMoviesByDateRangePager(any(), any()) } returns flowOf(PagingData.from(emptyList()))
    }

    @Test
    fun init_loadsPopularMovies_andEmitsSuccess() = runTest {
        val movies = listOf(Movie.preview())
        every { repository.getPopularMovies() } returns flowOf(
            Result.Loading,
            Result.Success(movies)
        )
        viewModel = HomeViewModel(context, repository)
        yield()

        assertEquals(movies, viewModel.popularMovies.value)
        assert(!viewModel.isPopularLoading.value)
        assertEquals(null, viewModel.popularMoviesError.value)
    }

    @Test
    fun init_onError_stopsLoadingAndKeepsEmptyList() = runTest {
        every { repository.getPopularMovies() } returns flowOf(
            Result.Loading,
            Result.Error(AppFailure.Unknown(RuntimeException("error")))
        )
        viewModel = HomeViewModel(context, repository)
        yield()

        assertEquals(emptyList<Movie>(), viewModel.popularMovies.value)
        assert(!viewModel.isPopularLoading.value)
        assertEquals("error", viewModel.popularMoviesError.value)
    }

    @Test
    fun onEvent_retry_loadsPopularMoviesAgain() = runTest {
        val movies = listOf(Movie.preview())
        every { repository.getPopularMovies() } returns flowOf(
            Result.Loading,
            Result.Success(movies)
        )
        viewModel = HomeViewModel(context, repository)
        yield()
        assertEquals(movies, viewModel.popularMovies.value)

        every { repository.getPopularMovies() } returns flowOf(Result.Loading, Result.Success(emptyList()))
        viewModel.onEvent(HomeEvent.Retry)
        yield()
        assertEquals(emptyList<Movie>(), viewModel.popularMovies.value)
        assertEquals(null, viewModel.popularMoviesError.value)
    }

    @Test
    fun monthSections_has12Items() = runTest {
        every { repository.getPopularMovies() } returns flowOf(Result.Success(emptyList()))
        viewModel = HomeViewModel(context, repository)
        assertEquals(12, viewModel.monthSections.size)
    }
}

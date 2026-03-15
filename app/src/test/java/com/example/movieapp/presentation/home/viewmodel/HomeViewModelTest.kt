package com.example.movieapp.presentation.home.viewmodel

import com.example.domain.entity.Movie
import com.example.domain.repository.MoviesRepository
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
import com.example.movieapp.util.MainDispatcherRule

class HomeViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private lateinit var repository: MoviesRepository
    private lateinit var viewModel: HomeViewModel

    @Before
    fun setup() {
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
        viewModel = HomeViewModel(repository)
        yield()

        assertEquals(movies, viewModel.popularMovies.value)
        assert(!viewModel.isPopularLoading.value)
    }

    @Test
    fun init_onError_setsVisibleSectionCountToOne() = runTest {
        every { repository.getPopularMovies() } returns flowOf(
            Result.Loading,
            Result.Error(RuntimeException("error"))
        )
        viewModel = HomeViewModel(repository)
        yield()

        assertEquals(1, viewModel.visibleSectionCount.value)
    }

    @Test
    fun onEvent_retry_loadsPopularMoviesAgain() = runTest {
        val movies = listOf(Movie.preview())
        every { repository.getPopularMovies() } returns flowOf(
            Result.Loading,
            Result.Success(movies)
        )
        viewModel = HomeViewModel(repository)
        yield()
        assertEquals(movies, viewModel.popularMovies.value)

        every { repository.getPopularMovies() } returns flowOf(Result.Loading, Result.Success(emptyList()))
        viewModel.onEvent(HomeEvent.Retry)
        yield()
        assertEquals(emptyList<Movie>(), viewModel.popularMovies.value)
    }

    @Test
    fun onMonthSectionFirstPageLoaded_incrementsVisibleSectionCount() = runTest {
        every { repository.getPopularMovies() } returns flowOf(Result.Loading, Result.Success(emptyList()))
        viewModel = HomeViewModel(repository)
        yield()

        assertEquals(1, viewModel.visibleSectionCount.value)
        viewModel.onMonthSectionFirstPageLoaded()
        assertEquals(2, viewModel.visibleSectionCount.value)
        viewModel.onMonthSectionFirstPageLoaded()
        assertEquals(3, viewModel.visibleSectionCount.value)
    }

    @Test
    fun monthSections_has12Items() = runTest {
        every { repository.getPopularMovies() } returns flowOf(Result.Success(emptyList()))
        viewModel = HomeViewModel(repository)
        assertEquals(12, viewModel.monthSections.size)
    }
}

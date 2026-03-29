package com.example.movieapp.presentation.home.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.entity.Movie
import com.example.domain.entity.MonthRangeGenerator
import com.example.domain.repository.MovieCatalogRepository
import com.example.domain.utils.Result
import com.example.movieapp.R
import com.example.movieapp.presentation.model.MonthPagingSection
import com.example.movieapp.presentation.utils.PresentationConstants.MOVIES_YEAR
import com.example.movieapp.presentation.utils.PresentationConstants.POPULAR_MOVIES_TOP_COUNT
import com.example.movieapp.presentation.utils.userMessage
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val movieCatalogRepository: MovieCatalogRepository
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _isPopularLoading = MutableStateFlow(true)
    val isPopularLoading = _isPopularLoading.asStateFlow()

    private val _popularMoviesError = MutableStateFlow<String?>(null)
    val popularMoviesError = _popularMoviesError.asStateFlow()

    private var popularMoviesCollectionJob: Job? = null

    val monthSections: List<MonthPagingSection> = createMonthSections()

    init {
        loadPopularMovies()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.Retry -> loadPopularMovies()
        }
    }

    private fun createMonthSections(): List<MonthPagingSection> {
        val monthRanges = MonthRangeGenerator.generateMonthRanges(MOVIES_YEAR)
        return monthRanges.map { monthRange ->
            MonthPagingSection(
                monthLabel = monthRange.monthLabel,
                pagingFlow = movieCatalogRepository
                    .getMoviesByDateRangePager(monthRange.startDate, monthRange.endDate)
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun loadPopularMovies() {
        val fallbackMessage = context.getString(R.string.error_generic)
        popularMoviesCollectionJob?.cancel()
        popularMoviesCollectionJob = movieCatalogRepository.getPopularMovies()
            .catch { e ->
                _popularMovies.value = emptyList()
                _isPopularLoading.value = false
                _popularMoviesError.value = e.message?.takeIf { it.isNotBlank() }
                    ?: fallbackMessage
            }
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _isPopularLoading.value = true
                        _popularMoviesError.value = null
                    }
                    is Result.Success -> {
                        _popularMovies.value = result.data.take(POPULAR_MOVIES_TOP_COUNT)
                        _isPopularLoading.value = false
                        _popularMoviesError.value = null
                    }
                    is Result.Error -> {
                        _popularMovies.value = emptyList()
                        _isPopularLoading.value = false
                        _popularMoviesError.value = result.failure.userMessage(
                            context,
                            R.string.error_generic
                        )
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}

package com.example.movieapp.presentation.home.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.example.domain.model.Movie
import com.example.domain.model.MonthRangeGenerator
import com.example.domain.repository.MoviesRepository
import com.example.domain.utils.Result
import com.example.movieapp.presentation.model.MonthPagingSection
import com.example.movieapp.presentation.utils.PresentationConstants.MOVIES_YEAR
import com.example.movieapp.presentation.utils.PresentationConstants.POPULAR_MOVIES_TOP_COUNT
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val moviesRepository: MoviesRepository
) : ViewModel() {

    private val _popularMovies = MutableStateFlow<List<Movie>>(emptyList())
    val popularMovies = _popularMovies.asStateFlow()

    private val _isPopularLoading = MutableStateFlow(true)
    val isPopularLoading = _isPopularLoading.asStateFlow()

    val monthSections: List<MonthPagingSection> = createMonthSections()

    private val _visibleSectionCount = MutableStateFlow(0)
    val visibleSectionCount = _visibleSectionCount.asStateFlow()

    init {
        loadPopularMovies()
    }

    fun onEvent(event: HomeEvent) {
        when (event) {
            is HomeEvent.LoadPopularMovies -> loadPopularMovies()
            is HomeEvent.Retry -> loadPopularMovies()
        }
    }

    fun onMonthSectionFirstPageLoaded() {
        _visibleSectionCount.value = (_visibleSectionCount.value + 1).coerceAtMost(monthSections.size)
    }

    private fun createMonthSections(): List<MonthPagingSection> {
        val monthRanges = MonthRangeGenerator.generateMonthRanges(MOVIES_YEAR)
        return monthRanges.map { monthRange ->
            MonthPagingSection(
                monthLabel = monthRange.monthLabel,
                pagingFlow = moviesRepository
                    .getMoviesByDateRangePager(monthRange.startDate, monthRange.endDate)
                    .cachedIn(viewModelScope)
            )
        }
    }

    private fun loadPopularMovies() {
        moviesRepository.getPopularMovies()
            .catch { _ ->
                _popularMovies.value = emptyList()
                _isPopularLoading.value = false
                if (_visibleSectionCount.value == 0) _visibleSectionCount.value = 1
            }
            .onEach { result ->
                when (result) {
                    is Result.Loading -> {
                        _isPopularLoading.value = true
                    }
                    is Result.Success -> {
                        _popularMovies.value = result.data.take(POPULAR_MOVIES_TOP_COUNT)
                        _isPopularLoading.value = false
                        if (_visibleSectionCount.value == 0) _visibleSectionCount.value = 1
                    }
                    is Result.Error -> {
                        _isPopularLoading.value = false
                        if (_visibleSectionCount.value == 0) _visibleSectionCount.value = 1
                    }
                }
            }
            .launchIn(viewModelScope)
    }
}

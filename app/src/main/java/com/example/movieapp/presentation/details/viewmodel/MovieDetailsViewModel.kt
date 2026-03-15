package com.example.movieapp.presentation.details.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Movie
import com.example.domain.repository.MoviesRepository
import com.example.domain.utils.Result
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = MovieDetailsViewModel.Factory::class)
class MovieDetailsViewModel @AssistedInject constructor(
    private val moviesRepository: MoviesRepository,
    @Assisted private val movie: Movie
) : ViewModel() {

    @AssistedFactory
    interface Factory {
        fun create(movie: Movie): MovieDetailsViewModel
    }

    private val _uiState = MutableStateFlow(MovieDetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMovieData()
        observeWatchlistState()
    }

    private fun observeWatchlistState() {
        moviesRepository.isMovieStored(movie.id)
            .onEach { inWatchlist ->
                _uiState.update { it.copy(isInWatchlist = inWatchlist) }
            }
            .catch { _ -> _uiState.update { it.copy(isInWatchlist = false) } }
            .launchIn(viewModelScope)
    }

    private fun loadMovieData() {
        loadMovieDetails()
        loadMovieCast()
        loadSimilarMovies()
    }

    private fun loadMovieDetails() {
        viewModelScope.launch {
            moviesRepository.getMovieDetails(movie.id).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { it.copy(isDetailsLoading = true, detailsError = null) }
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                movieDetails = result.data,
                                isDetailsLoading = false,
                                detailsError = null
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isDetailsLoading = false,
                                detailsError = result.error.message ?: "Failed to load movie details"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMovieCast() {
        viewModelScope.launch {
            moviesRepository.getMovieCredits(movie.id).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { it.copy(isCastLoading = true, castError = null) }
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                cast = result.data,
                                isCastLoading = false,
                                castError = null
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isCastLoading = false,
                                castError = result.error.message ?: "Failed to load cast"
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadSimilarMovies() {
        viewModelScope.launch {
            moviesRepository.getSimilarMovies(movie.id).collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _uiState.update { it.copy(isSimilarLoading = true, similarError = null) }
                    }
                    is Result.Success -> {
                        _uiState.update {
                            it.copy(
                                similarMovies = result.data,
                                isSimilarLoading = false,
                                similarError = null
                            )
                        }
                    }
                    is Result.Error -> {
                        _uiState.update {
                            it.copy(
                                isSimilarLoading = false,
                                similarError = result.error.message ?: "Failed to load similar movies"
                            )
                        }
                    }
                }
            }
        }
    }

    fun onEvent(event: MovieDetailsEvent) {
        when (event) {
            is MovieDetailsEvent.RetryDetails -> loadMovieDetails()
            is MovieDetailsEvent.RetryCast -> loadMovieCast()
            is MovieDetailsEvent.RetrySimilar -> loadSimilarMovies()
            is MovieDetailsEvent.ToggleWatchlist -> toggleWatchlist()
        }
    }

    fun retryDetails() {
        loadMovieDetails()
    }

    fun retryCast() {
        loadMovieCast()
    }

    fun retrySimilar() {
        loadSimilarMovies()
    }

    private fun toggleWatchlist() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value.isInWatchlist) {
                moviesRepository.deleteMovie(movie)
            } else {
                moviesRepository.saveMovie(movie)
            }
        }
    }
}

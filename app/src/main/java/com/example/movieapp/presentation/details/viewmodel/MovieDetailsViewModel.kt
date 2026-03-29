package com.example.movieapp.presentation.details.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Movie
import com.example.domain.repository.MovieDetailsRepository
import com.example.domain.repository.WatchlistRepository
import com.example.domain.utils.Result
import com.example.movieapp.R
import com.example.movieapp.presentation.utils.userMessage
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
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
    private val movieDetailsRepository: MovieDetailsRepository,
    private val watchlistRepository: WatchlistRepository,
    @param:ApplicationContext private val context: Context,
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
        watchlistRepository.isMovieStored(movie.id)
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
            movieDetailsRepository.getMovieDetails(movie.id).collect { result ->
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
                                detailsError = result.failure.userMessage(
                                    context,
                                    R.string.error_movie_details
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadMovieCast() {
        viewModelScope.launch {
            movieDetailsRepository.getMovieCredits(movie.id).collect { result ->
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
                                castError = result.failure.userMessage(
                                    context,
                                    R.string.error_cast
                                )
                            )
                        }
                    }
                }
            }
        }
    }

    private fun loadSimilarMovies() {
        viewModelScope.launch {
            movieDetailsRepository.getSimilarMovies(movie.id).collect { result ->
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
                                similarError = result.failure.userMessage(
                                    context,
                                    R.string.error_similar_movies
                                )
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

    private fun toggleWatchlist() {
        viewModelScope.launch(Dispatchers.IO) {
            if (_uiState.value.isInWatchlist) {
                watchlistRepository.deleteMovie(movie)
            } else {
                watchlistRepository.saveMovie(movie)
            }
        }
    }
}

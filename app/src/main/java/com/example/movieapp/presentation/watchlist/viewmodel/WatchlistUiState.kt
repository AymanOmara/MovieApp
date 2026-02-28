package com.example.movieapp.presentation.watchlist.viewmodel

import com.example.domain.model.Movie

sealed interface WatchlistUiState {
    data object Loading : WatchlistUiState
    data class Success(val movies: List<Movie>) : WatchlistUiState
    data class Error(val message: String) : WatchlistUiState
}

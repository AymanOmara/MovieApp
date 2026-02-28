package com.example.movieapp.presentation.home.viewmodel

import com.example.domain.model.MovieSection

sealed class HomeUiState {
    data object Idle : HomeUiState()
    data object Loading : HomeUiState()
    data class Success(val sections: List<MovieSection>) : HomeUiState()
    data class Error(val message: String) : HomeUiState()
}

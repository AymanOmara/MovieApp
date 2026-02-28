package com.example.movieapp.presentation.details.viewmodel

sealed interface MovieDetailsEvent {
    data object RetryDetails : MovieDetailsEvent
    data object RetryCast : MovieDetailsEvent
    data object RetrySimilar : MovieDetailsEvent
    data object ToggleWatchlist : MovieDetailsEvent
}

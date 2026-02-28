package com.example.movieapp.presentation.home.viewmodel

sealed class HomeEvent {
    data object LoadPopularMovies : HomeEvent()
    data object Retry : HomeEvent()
}

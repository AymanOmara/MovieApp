package com.example.movieapp.presentation.home.viewmodel

sealed class HomeEvent {
    data object Retry : HomeEvent()
}

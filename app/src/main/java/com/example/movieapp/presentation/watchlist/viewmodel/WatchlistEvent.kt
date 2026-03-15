package com.example.movieapp.presentation.watchlist.viewmodel

import com.example.domain.entity.Movie

sealed interface WatchlistEvent {
    data object Refresh : WatchlistEvent
    data class RemoveMovie(val movie: Movie) : WatchlistEvent
}

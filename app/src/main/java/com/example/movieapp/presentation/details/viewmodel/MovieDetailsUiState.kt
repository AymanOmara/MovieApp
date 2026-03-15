package com.example.movieapp.presentation.details.viewmodel

import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails

data class MovieDetailsUiState(
    val movieDetails: MovieDetails? = null,
    val isDetailsLoading: Boolean = true,
    val detailsError: String? = null,

    val isInWatchlist: Boolean = false,

    val cast: List<Cast> = emptyList(),
    val isCastLoading: Boolean = true,
    val castError: String? = null,

    val similarMovies: List<Movie> = emptyList(),
    val isSimilarLoading: Boolean = true,
    val similarError: String? = null
) {
    val isFullyLoaded: Boolean
        get() = !isDetailsLoading && !isCastLoading && !isSimilarLoading

    val hasAnyData: Boolean
        get() = movieDetails != null || cast.isNotEmpty() || similarMovies.isNotEmpty()
}

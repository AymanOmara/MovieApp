package com.example.movieapp.presentation.details.viewmodel

import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class MovieDetailsUiStateTest {

    @Test
    fun isFullyLoaded_trueWhenNoLoading() {
        val state = MovieDetailsUiState(
            movieDetails = MovieDetails.preview(),
            isDetailsLoading = false,
            isCastLoading = false,
            isSimilarLoading = false
        )
        assertTrue(state.isFullyLoaded)
    }

    @Test
    fun isFullyLoaded_falseWhenAnyLoading() {
        assertFalse(
            MovieDetailsUiState(isDetailsLoading = true).isFullyLoaded
        )
        assertFalse(
            MovieDetailsUiState(isCastLoading = true).isFullyLoaded
        )
        assertFalse(
            MovieDetailsUiState(isSimilarLoading = true).isFullyLoaded
        )
    }

    @Test
    fun hasAnyData_trueWhenDetailsPresent() {
        val state = MovieDetailsUiState(movieDetails = MovieDetails.preview())
        assertTrue(state.hasAnyData)
    }

    @Test
    fun hasAnyData_trueWhenCastNotEmpty() {
        val state = MovieDetailsUiState(cast = Cast.previewList())
        assertTrue(state.hasAnyData)
    }

    @Test
    fun hasAnyData_trueWhenSimilarNotEmpty() {
        val state = MovieDetailsUiState(similarMovies = Movie.previewList())
        assertTrue(state.hasAnyData)
    }

    @Test
    fun hasAnyData_falseWhenAllEmpty() {
        val state = MovieDetailsUiState()
        assertFalse(state.hasAnyData)
    }
}

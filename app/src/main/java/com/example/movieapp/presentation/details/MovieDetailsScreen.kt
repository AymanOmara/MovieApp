package com.example.movieapp.presentation.details

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import com.example.movieapp.presentation.details.viewmodel.MovieDetailsEvent
import com.example.movieapp.presentation.details.viewmodel.MovieDetailsUiState
import com.example.movieapp.presentation.details.viewmodel.MovieDetailsViewModel
import com.example.movieapp.ui.theme.MovieAppTheme
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel

@Composable
fun MovieDetailsScreen(
    movie: Movie,
    onBackClick: () -> Unit,
    viewModel: MovieDetailsViewModel = hiltViewModel<MovieDetailsViewModel, MovieDetailsViewModel.Factory>(
        key = "movie_details_${movie.id}",
        creationCallback = { factory -> factory.create(movie) }
    )
) {
    val uiState by viewModel.uiState.collectAsState()

    MovieDetailsContent(
        uiState = uiState,
        onBackClick = onBackClick,
        onRetryDetails = { viewModel.onEvent(MovieDetailsEvent.RetryDetails) },
        onRetryCast = { viewModel.onEvent(MovieDetailsEvent.RetryCast) },
        onRetrySimilar = { viewModel.onEvent(MovieDetailsEvent.RetrySimilar) },
        onWatchlistToggle = { viewModel.onEvent(MovieDetailsEvent.ToggleWatchlist) }
    )
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsScreenPreview() {
    MovieAppTheme {
        MovieDetailsContent(
            uiState = MovieDetailsUiState(
                movieDetails = MovieDetails.preview(),
                isDetailsLoading = false,
                cast = Cast.previewList(),
                isCastLoading = false,
                similarMovies = Movie.previewList(),
                isSimilarLoading = false
            ),
            onBackClick = {},
            onRetryDetails = {},
            onRetryCast = {},
            onRetrySimilar = {},
            onWatchlistToggle = {}
        )
    }
}

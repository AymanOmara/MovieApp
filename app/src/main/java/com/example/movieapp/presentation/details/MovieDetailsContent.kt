package com.example.movieapp.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import com.example.movieapp.R
import com.example.movieapp.presentation.components.MovieTopAppBar
import com.example.movieapp.presentation.details.components.CastSection
import com.example.movieapp.presentation.details.components.MovieHeaderSection
import com.example.movieapp.presentation.details.components.SimilarMoviesSection
import com.example.movieapp.presentation.details.viewmodel.MovieDetailsUiState
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieDetailsContent(
    uiState: MovieDetailsUiState,
    onBackClick: () -> Unit,
    onRetryDetails: () -> Unit,
    onRetryCast: () -> Unit,
    onRetrySimilar: () -> Unit,
    onWatchlistToggle: () -> Unit,
) {
    Scaffold(
        containerColor = Primary,
        topBar = {
            MovieTopAppBar(
                title = {
                    Text(
                        text = uiState.movieDetails?.title ?: stringResource(R.string.movie_details_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.White
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = Color.White
                        )
                    }
                },
                actions = {
                    IconButton(onClick = onWatchlistToggle) {
                        Icon(
                            imageVector = if (uiState.isInWatchlist) Icons.Filled.Favorite else Icons.Outlined.FavoriteBorder,
                            contentDescription = stringResource(if (uiState.isInWatchlist) R.string.remove_from_watchlist else R.string.add_to_watchlist),
                            tint = if (uiState.isInWatchlist) Color.Red else Color.White
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            item {
                MovieHeaderSection(
                    movieDetails = uiState.movieDetails,
                    isLoading = uiState.isDetailsLoading,
                    error = uiState.detailsError,
                    onRetry = onRetryDetails
                )
            }

            item {
                CastSection(
                    cast = uiState.cast,
                    isLoading = uiState.isCastLoading,
                    error = uiState.castError,
                    onRetry = onRetryCast
                )
            }

            item {
                SimilarMoviesSection(
                    movies = uiState.similarMovies,
                    isLoading = uiState.isSimilarLoading,
                    error = uiState.similarError,
                    onRetry = onRetrySimilar,
                )
            }

            item {
                Spacer(modifier = Modifier.height(16.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsContentLoadingPreview() {
    MovieAppTheme {
        MovieDetailsContent(
            uiState = MovieDetailsUiState(
                isDetailsLoading = true,
                isCastLoading = true,
                isSimilarLoading = true
            ),
            onBackClick = {},
            onRetryDetails = {},
            onRetryCast = {},
            onRetrySimilar = {},
            onWatchlistToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsContentContentPreview() {
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

@Preview(showBackground = true)
@Composable
private fun MovieDetailsContentErrorPreview() {
    MovieAppTheme {
        MovieDetailsContent(
            uiState = MovieDetailsUiState(
                isDetailsLoading = false,
                detailsError = stringResource(R.string.error_movie_details),
                isCastLoading = false,
                castError = stringResource(R.string.error_cast),
                isSimilarLoading = false,
                similarError = stringResource(R.string.error_similar_movies)
            ),
            onBackClick = {},
            onRetryDetails = {},
            onRetryCast = {},
            onRetrySimilar = {},
            onWatchlistToggle = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieDetailsContentPartialErrorPreview() {
    MovieAppTheme {
        MovieDetailsContent(
            uiState = MovieDetailsUiState(
                movieDetails = MovieDetails.preview(),
                isDetailsLoading = false,
                isCastLoading = false,
                castError = stringResource(R.string.error_cast),
                isSimilarLoading = false,
                similarMovies = Movie.previewList().take(1)
            ),
            onBackClick = {},
            onRetryDetails = {},
            onRetryCast = {},
            onRetrySimilar = {},
            onWatchlistToggle = {}
        )
    }
}

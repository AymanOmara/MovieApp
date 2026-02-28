package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.model.MovieDetails
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun MovieHeaderSection(
    movieDetails: MovieDetails?,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    when {
        isLoading -> {
            ShimmerMovieHeader(modifier = modifier)
        }
        error != null && movieDetails == null -> {
            ErrorSection(
                message = error,
                onRetry = onRetry,
                modifier = modifier.height(200.dp)
            )
        }
        movieDetails != null -> {
            MovieHeaderContent(
                movieDetails = movieDetails,
                modifier = modifier
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieHeaderSectionLoadingPreview() {
    MovieAppTheme {
        MovieHeaderSection(
            movieDetails = null,
            isLoading = true,
            error = null,
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieHeaderSectionErrorPreview() {
    MovieAppTheme {
        MovieHeaderSection(
            movieDetails = null,
            isLoading = false,
            error = stringResource(R.string.error_movie_details),
            onRetry = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MovieHeaderSectionContentPreview() {
    MovieAppTheme {
        MovieHeaderSection(
            movieDetails = MovieDetails.preview(),
            isLoading = false,
            error = null,
            onRetry = {}
        )
    }
}

package com.example.movieapp.presentation.details.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.domain.entity.Movie
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun SimilarMoviesSection(
    movies: List<Movie>,
    isLoading: Boolean,
    error: String?,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = stringResource(R.string.similar_movies),
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        when {
            isLoading -> {
                ShimmerSimilarMoviesRow()
            }
            error != null && movies.isEmpty() -> {
                ErrorSection(
                    message = error,
                    onRetry = onRetry,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                )
            }
            movies.isEmpty() -> {
                Text(
                    text = stringResource(R.string.no_similar_movies_found),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.White.copy(alpha = 0.8f),
                    modifier = Modifier.padding(horizontal = 16.dp)
                )
            }
            else -> {
                LazyRow(
                    contentPadding = PaddingValues(horizontal = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(movies) { movie ->
                        SimilarMovieCard(
                            movie = movie,
                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarMoviesSectionLoadingPreview() {
    MovieAppTheme {
        SimilarMoviesSection(
            movies = emptyList(),
            isLoading = true,
            error = null,
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarMoviesSectionErrorPreview() {
    MovieAppTheme {
        SimilarMoviesSection(
            movies = emptyList(),
            isLoading = false,
            error = stringResource(R.string.error_similar_movies),
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarMoviesSectionEmptyPreview() {
    MovieAppTheme {
        SimilarMoviesSection(
            movies = emptyList(),
            isLoading = false,
            error = null,
            onRetry = {},
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun SimilarMoviesSectionContentPreview() {
    MovieAppTheme {
        SimilarMoviesSection(
            movies = Movie.previewList(),
            isLoading = false,
            error = null,
            onRetry = {},
        )
    }
}

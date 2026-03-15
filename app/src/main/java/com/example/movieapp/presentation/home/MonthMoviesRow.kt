package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import com.example.domain.entity.Movie
import androidx.compose.ui.tooling.preview.Preview
import com.example.movieapp.R
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.flow.flowOf
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems

@Composable
fun MonthMoviesRow(
    monthLabel: String,
    movies: LazyPagingItems<Movie>,
    onMovieClick: (Movie) -> Unit,
    onFirstPageLoaded: (() -> Unit)? = null,
    modifier: Modifier = Modifier
) {
    val hasReportedLoaded = remember { mutableStateOf(false) }
    LaunchedEffect(movies.loadState.refresh, movies.itemCount) {
        if (
            !hasReportedLoaded.value &&
            movies.loadState.refresh is LoadState.NotLoading &&
            movies.itemCount > 0
        ) {
            hasReportedLoaded.value = true
            onFirstPageLoaded?.invoke()
        }
    }

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = monthLabel,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (val refreshState = movies.loadState.refresh) {
            is LoadState.Loading -> {
                ShimmerMovieRow()
            }
            is LoadState.Error -> {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = refreshState.error.message ?: stringResource(R.string.error_loading),
                        color = Color.White,
                        style = MaterialTheme.typography.bodySmall
                    )
                    Button(onClick = { movies.retry() }) {
                        Text(stringResource(R.string.retry), color = Color.White)
                    }
                }
            }
            is LoadState.NotLoading -> {
                if (movies.itemCount == 0) {
                    Text(
                        text = stringResource(R.string.no_movies_found),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                } else {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(12.dp),
                        contentPadding = PaddingValues(vertical = 8.dp)
                    ) {
                        items(
                            count = movies.itemCount,
                            key = { index -> movies[index]?.id ?: index }
                        ) { index ->
                            movies[index]?.let { movie ->
                                MovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie) }
                                )
                            }
                        }

                        when (movies.loadState.append) {
                            is LoadState.Loading -> {
                                item {
                                    ShimmerMovieCard()
                                }
                            }
                            is LoadState.Error -> {
                                item {
                                    Box(
                                        modifier = Modifier
                                            .size(140.dp, 260.dp)
                                            .padding(8.dp),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Button(onClick = { movies.retry() }) {
                                            Text(stringResource(R.string.retry), color = Color.White)
                                        }
                                    }
                                }
                            }
                            else -> {}
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun MonthMoviesRowPreview() {
    MovieAppTheme {
        val movies = flowOf(PagingData.from(Movie.previewList())).collectAsLazyPagingItems()
        MonthMoviesRow(
            monthLabel = "January 2024",
            movies = movies,
            onMovieClick = {}
        )
    }
}

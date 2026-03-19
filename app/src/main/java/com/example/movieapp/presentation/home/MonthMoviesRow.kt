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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.example.domain.entity.Movie
import com.example.movieapp.R
import com.example.movieapp.presentation.model.MonthPagingSection
import com.example.movieapp.ui.theme.MovieAppTheme
import kotlinx.coroutines.flow.flowOf
import androidx.paging.PagingData

@Composable
fun MonthMoviesRow(
    section: MonthPagingSection,
    onMovieClick: (Movie) -> Unit,
    modifier: Modifier = Modifier
) {
    val pagingItems = section.pagingFlow.collectAsLazyPagingItems()

    Column(modifier = modifier.fillMaxWidth()) {
        Text(
            text = section.monthLabel,
            style = MaterialTheme.typography.titleMedium,
            color = Color.White,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        when (val refreshState = pagingItems.loadState.refresh) {
            is LoadState.Loading -> ShimmerMovieRow()

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
                    Button(onClick = { pagingItems.retry() }) {
                        Text(stringResource(R.string.retry), color = Color.White)
                    }
                }
            }

            is LoadState.NotLoading -> {
                if (pagingItems.itemCount == 0) {
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
                            count = pagingItems.itemCount,
                            key = pagingItems.itemKey { movie -> movie.id }
                        ) { index ->
                            val movie = pagingItems[index]
                            if (movie != null) {
                                MovieCard(
                                    movie = movie,
                                    onClick = { onMovieClick(movie) }
                                )
                            }
                        }

                        when (pagingItems.loadState.append) {
                            is LoadState.Loading -> item { ShimmerMovieCard() }
                            is LoadState.Error -> item {
                                Box(
                                    modifier = Modifier
                                        .size(140.dp, 260.dp)
                                        .padding(8.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Button(onClick = { pagingItems.retry() }) {
                                        Text(stringResource(R.string.retry), color = Color.White)
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
        val section = MonthPagingSection(
            monthLabel = "January 2024",
            pagingFlow = flowOf(PagingData.from(Movie.previewList()))
        )
        MonthMoviesRow(
            section = section,
            onMovieClick = {}
        )
    }
}

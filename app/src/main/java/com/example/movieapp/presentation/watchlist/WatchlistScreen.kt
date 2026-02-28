package com.example.movieapp.presentation.watchlist

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.model.Movie
import com.example.movieapp.R
import com.example.movieapp.presentation.components.MovieTopAppBar
import com.example.movieapp.presentation.watchlist.viewmodel.WatchlistEvent
import com.example.movieapp.presentation.watchlist.viewmodel.WatchlistUiState
import com.example.movieapp.presentation.watchlist.viewmodel.WatchlistViewModel
import androidx.compose.ui.graphics.Color
import com.example.movieapp.ui.theme.MovieAppTheme
import com.example.movieapp.ui.theme.Primary

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WatchlistScreen(
    onMovieClick: (Movie) -> Unit = {},
    viewModel: WatchlistViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(WatchlistEvent.Refresh)
    }

    Scaffold(
        containerColor = Primary,
        topBar = {
            MovieTopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.watchlist_title),
                        style = MaterialTheme.typography.titleLarge,
                        color = Color.White
                    )
                },
            )
        }
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
            contentAlignment = Alignment.Center
        ) {
            when (val state = uiState) {
                is WatchlistUiState.Loading -> {
                    CircularProgressIndicator()
                }

                is WatchlistUiState.Success -> {
                    if (state.movies.isEmpty()) {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(32.dp),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.watchlist_empty_title),
                                style = MaterialTheme.typography.titleMedium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                            Text(
                                text = stringResource(R.string.watchlist_empty_subtitle),
                                style = MaterialTheme.typography.bodyMedium,
                                color = Color.White,
                                textAlign = TextAlign.Center
                            )
                        }
                    } else {
                        LazyColumn(
                            modifier = Modifier.fillMaxSize(),
                            contentPadding = PaddingValues(16.dp),
                            verticalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            items(state.movies, key = { it.id }) { movie ->
                                WatchlistRowItem(
                                    movie = movie,
                                    onClick = { onMovieClick(movie) },
                                    onRemoveFromWatchlist = {
                                        viewModel.onEvent(WatchlistEvent.RemoveMovie(movie))
                                    }
                                )
                            }
                        }
                    }
                }

                is WatchlistUiState.Error -> {
                    Text(
                        text = stringResource(R.string.error_format, state.message),
                        style = MaterialTheme.typography.bodyMedium,
                        color = Color.White,
                        modifier = Modifier.padding(16.dp)
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WatchlistScreenPreview() {
    MovieAppTheme {
        WatchlistScreen(onMovieClick = {})
    }
}

package com.example.movieapp.presentation.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import com.example.domain.entity.Movie
import com.example.movieapp.R
import com.example.movieapp.presentation.home.viewmodel.HomeViewModel
import com.example.movieapp.ui.theme.MovieAppTheme

@Composable
fun HomeScreen(
    onMovieClick: (Movie) -> Unit,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val popularMovies by viewModel.popularMovies.collectAsState()
    val isPopularLoading by viewModel.isPopularLoading.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        contentPadding = PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item {
            PopularMoviesSection(
                movies = popularMovies,
                isLoading = isPopularLoading,
                onMovieClick = onMovieClick
            )
        }

        item {
            Text(
                text = stringResource(R.string.movies_2024),
                style = MaterialTheme.typography.titleLarge,
                color = Color.White,
                modifier = Modifier.padding(top = 8.dp, bottom = 8.dp)
            )
        }

        items(
            items = viewModel.monthSections,
            key = { section -> section.monthLabel }
        ) { section ->
            MonthMoviesRow(
                section = section,
                onMovieClick = onMovieClick
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenPreview() {
    MovieAppTheme {
        HomeScreen(onMovieClick = {})
    }
}

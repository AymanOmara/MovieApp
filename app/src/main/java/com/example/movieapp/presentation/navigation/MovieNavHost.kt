package com.example.movieapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.entryProvider
import androidx.navigation3.ui.NavDisplay
import com.example.movieapp.presentation.details.MovieDetailsScreen
import com.example.movieapp.presentation.home.HomeScreen
import com.example.movieapp.presentation.watchlist.WatchlistScreen

@Composable
fun MovieNavHost(
    backStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier
) {
    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        modifier = modifier,
        entryProvider = entryProvider {
            entry<Screen.Home> {
                HomeScreen(
                    onMovieClick = { movie ->
                        backStack.add(Screen.MovieDetails(movie))
                    }
                )
            }
            entry<Screen.Watchlist> {
                WatchlistScreen(
                    onMovieClick = { movie ->
                        backStack.add(Screen.MovieDetails(movie))
                    }
                )
            }
            entry<Screen.MovieDetails> { screen ->
                MovieDetailsScreen(
                    movie = screen.movie,
                    onBackClick = { backStack.removeLastOrNull() },
                )
            }
        }
    )
}

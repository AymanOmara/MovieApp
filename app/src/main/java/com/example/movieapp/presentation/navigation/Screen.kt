package com.example.movieapp.presentation.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.navigation3.runtime.NavKey
import com.example.domain.entity.Movie
import com.example.movieapp.R
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
sealed interface Screen : NavKey {
    @Transient
    val titleResId: Int
    @Transient
    val icon: ImageVector

    @Serializable
    data object Home : Screen {
        @Transient
        override val titleResId = R.string.nav_home
        @Transient
        override val icon = Icons.Filled.Home
    }

    @Serializable
    data object Watchlist : Screen {
        @Transient
        override val titleResId = R.string.nav_watchlist
        @Transient
        override val icon = Icons.AutoMirrored.Filled.List
    }

    @Serializable
    data class MovieDetails(val movie: Movie) : Screen {
        @Transient
        override val titleResId = R.string.nav_movie_details
        @Transient
        override val icon = Icons.Filled.Info
    }

    companion object {
        val bottomNavItems = listOf(Home, Watchlist)
    }
}

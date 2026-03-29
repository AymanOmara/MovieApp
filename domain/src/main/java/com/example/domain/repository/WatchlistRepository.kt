package com.example.domain.repository

import com.example.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

interface WatchlistRepository {

    suspend fun saveMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    fun getAllMovies(): Flow<List<Movie>>

    fun isMovieStored(movieId: Int): Flow<Boolean>
}

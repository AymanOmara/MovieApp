package com.example.data.repository

import com.example.data.local.MovieDAO
import com.example.data.local.movieEntityToMovieLocal
import com.example.domain.entity.Movie
import com.example.domain.repository.WatchlistRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(
    private val movieDao: MovieDAO
) : WatchlistRepository {

    override suspend fun saveMovie(movie: Movie) {
        movieDao.insert(movie.movieEntityToMovieLocal())
    }

    override suspend fun deleteMovie(movie: Movie) {
        movieDao.delete(movie.movieEntityToMovieLocal())
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAll().map { list -> list.map { it.toMovieEntity() } }
    }

    override fun isMovieStored(movieId: Int): Flow<Boolean> {
        return movieDao.isMovieStored(movieId).map { it == 1 }
    }
}

package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface MoviesRepository {

    fun getPopularMovies(): Flow<Result<List<Movie>>>

    fun getMoviesByDateRangePager(startDate: String, endDate: String): Flow<PagingData<Movie>>

    fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>>

    fun getMovieCredits(movieId: Int): Flow<Result<List<Cast>>>

    fun getSimilarMovies(movieId: Int): Flow<Result<List<Movie>>>

    suspend fun saveMovie(movie: Movie)

    suspend fun deleteMovie(movie: Movie)

    fun getAllMovies(): Flow<List<Movie>>

    fun isMovieStored(movieId: Int): Flow<Boolean>
}

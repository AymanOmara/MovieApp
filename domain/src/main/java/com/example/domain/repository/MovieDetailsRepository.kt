package com.example.domain.repository

import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieDetailsRepository {

    fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>>

    fun getMovieCredits(movieId: Int): Flow<Result<List<Cast>>>

    fun getSimilarMovies(movieId: Int): Flow<Result<List<Movie>>>
}

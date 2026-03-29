package com.example.data.repository

import com.example.data.network.MoviesWebServices
import com.example.data.network.utils.NetworkUtils
import com.example.domain.entity.Cast
import com.example.domain.entity.Movie
import com.example.domain.entity.MovieDetails
import com.example.domain.repository.MovieDetailsRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class MovieDetailsRepositoryImpl @Inject constructor(
    private val api: MoviesWebServices,
    private val networkUtils: NetworkUtils
) : MovieDetailsRepository {

    override fun getMovieDetails(movieId: Int): Flow<Result<MovieDetails>> {
        return networkUtils.safeApiCall {
            api.getMovieDetails(movieId).toMovieDetails()
        }
    }

    override fun getMovieCredits(movieId: Int): Flow<Result<List<Cast>>> {
        return networkUtils.safeApiCall {
            val response = api.getMovieCredits(movieId)
            response.cast?.map { it.toCast() } ?: emptyList()
        }
    }

    override fun getSimilarMovies(movieId: Int): Flow<Result<List<Movie>>> {
        return networkUtils.safeApiCall {
            api.getSimilarMovies(movieId).results.map { it.toMovie() }
        }
    }
}

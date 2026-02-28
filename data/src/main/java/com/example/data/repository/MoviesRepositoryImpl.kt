package com.example.data.repository

import androidx.paging.PagingData
import com.example.data.local.MovieDAO
import com.example.data.local.PopularMoviesCacheDao
import com.example.data.local.DiscoverPageCacheDao
import com.example.data.local.movieEntityToMovieLocal
import com.example.data.local.toDiscoverPageCacheItem
import com.example.data.local.toPopularMovieCache
import com.example.data.network.MoviesWebServices
import com.example.data.network.utils.NetworkUtils
import com.example.data.paging.BasePagingSource
import com.example.domain.model.Cast
import com.example.domain.model.Movie
import com.example.domain.model.MovieDetails
import com.example.domain.repository.MoviesRepository
import com.example.domain.utils.Result
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.withContext
import javax.inject.Inject


class MoviesRepositoryImpl @Inject constructor(
    private val api: MoviesWebServices,
    private val networkUtils: NetworkUtils,
    private val movieDao: MovieDAO,
    private val popularMoviesCacheDao: PopularMoviesCacheDao,
    private val discoverPageCacheDao: DiscoverPageCacheDao
) : MoviesRepository {

    override fun getPopularMovies(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        val cached = withContext(Dispatchers.IO) {
            popularMoviesCacheDao.getAllOnce().map { it.toMovie() }
        }
        if (cached.isNotEmpty()) {
            emit(Result.Success(cached))
        }
        try {
            val fresh = api.getPopularMovies().results.map { it.toMovie() }
            withContext(Dispatchers.IO) {
                popularMoviesCacheDao.deleteAll()
                popularMoviesCacheDao.insertAll(fresh.map { it.toPopularMovieCache() })
            }
            emit(Result.Success(fresh))
        } catch (e: Exception) {
            if (cached.isEmpty()) {
                emit(Result.Error(e))
            }
        }
    }

    override fun getMoviesByDateRangePager(
        startDate: String,
        endDate: String
    ): Flow<PagingData<Movie>> {
        return BasePagingSource.createPager(
            pageSize = 20,
            prefetchDistance = 2,
            provider = { page ->
                api.discoverMoviesByDateRange(
                    startDate = startDate,
                    endDate = endDate,
                    page = page
                )
            },
            mapper = { it.toMovie() },
            cacheReader = { page ->
                discoverPageCacheDao.getPage(startDate, endDate, page).map { it.toMovie() }
            },
            cacheWriter = { movies, page ->
                discoverPageCacheDao.insertPage(
                    movies.map { it.toDiscoverPageCacheItem(startDate, endDate, page) }
                )
            }
        ).flow
    }

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

    override suspend fun saveMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieDao.insert(movie.movieEntityToMovieLocal())
        }
    }

    override suspend fun deleteMovie(movie: Movie) {
        withContext(Dispatchers.IO) {
            movieDao.delete(movie.movieEntityToMovieLocal())
        }
    }

    override fun getAllMovies(): Flow<List<Movie>> {
        return movieDao.getAll().map { list -> list.map { it.toMovieEntity() } }
    }

    override fun isMovieStored(movieId: Int): Flow<Boolean> {
        return movieDao.isMovieStored(movieId).map { it == 1 }
    }
}

package com.example.data.repository

import android.content.Context
import androidx.paging.PagingData
import com.example.data.R
import com.example.data.local.DiscoverPageCacheDao
import com.example.data.local.PopularMoviesCacheDao
import com.example.data.local.toDiscoverPageCacheItem
import com.example.data.local.toPopularMovieCache
import com.example.data.network.MoviesWebServices
import com.example.data.network.utils.NetworkUtils
import com.example.data.paging.BasePagingSource
import com.example.data.utils.PagingConstants
import com.example.domain.entity.Movie
import com.example.domain.repository.MovieCatalogRepository
import com.example.domain.utils.Result
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MovieCatalogRepositoryImpl @Inject constructor(
    @param:ApplicationContext private val context: Context,
    private val api: MoviesWebServices,
    private val networkUtils: NetworkUtils,
    private val popularMoviesCacheDao: PopularMoviesCacheDao,
    private val discoverPageCacheDao: DiscoverPageCacheDao
) : MovieCatalogRepository {

    override fun getPopularMovies(): Flow<Result<List<Movie>>> = flow {
        emit(Result.Loading)
        val cached = withContext(Dispatchers.IO) {
            popularMoviesCacheDao.getAllOnce().map { it.toMovie() }
        }

        if (networkUtils.isNetworkAvailable()) {
            try {
                val fresh = api.getPopularMovies().results.map { it.toMovie() }
                withContext(Dispatchers.IO) {
                    popularMoviesCacheDao.deleteAll()
                    popularMoviesCacheDao.insertAll(fresh.map { it.toPopularMovieCache() })
                }
                emit(Result.Success(fresh))
            } catch (e: Exception) {
                if (cached.isNotEmpty()) {
                    emit(Result.Success(cached))
                } else {
                    emit(Result.Error(e))
                }
            }
        } else {
            if (cached.isNotEmpty()) {
                emit(Result.Success(cached))
            } else {
                emit(
                    Result.Error(
                        Exception(context.getString(R.string.error_no_network_no_cache))
                    )
                )
            }
        }
    }

    override fun getMoviesByDateRangePager(
        startDate: String,
        endDate: String
    ): Flow<PagingData<Movie>> {
        val offlineMessage = context.getString(R.string.error_no_network_no_cache)
        return BasePagingSource.createPager(
            pageSize = PagingConstants.PAGE_SIZE,
            prefetchDistance = PagingConstants.PREFETCH_DISTANCE,
            networkUtils = networkUtils,
            offlineNoCacheErrorMessage = offlineMessage,
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
}

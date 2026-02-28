package com.example.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.local.DiscoverPageCacheDao
import com.example.data.local.toDiscoverPageCacheItem
import com.example.data.network.dto.BaseResponse
import com.example.data.network.dto.MovieDto
import com.example.domain.model.Movie
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class DateRangePagingSource(
    private val startDate: String,
    private val endDate: String,
    private val api: suspend (Int) -> BaseResponse<List<MovieDto>>,
    private val mapper: (MovieDto) -> Movie,
    private val discoverPageCacheDao: DiscoverPageCacheDao
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val page = params.key ?: 1
        val cached = withContext(Dispatchers.IO) {
            discoverPageCacheDao.getPage(startDate, endDate, page).map { it.toMovie() }
        }
        return try {
            val response = api(page)
            val dtos = response.results
            val totalPages = response.totalPages
            val hasMore = dtos.isNotEmpty() && page < totalPages && page < 500
            val nextKey = if (hasMore) page + 1 else null
            val movies = dtos.map { mapper(it) }
            withContext(Dispatchers.IO) {
                discoverPageCacheDao.insertPage(
                    movies.map { it.toDiscoverPageCacheItem(startDate, endDate, page) }
                )
            }
            LoadResult.Page(
                data = movies,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                else -> if (cached.isNotEmpty()) {
                    LoadResult.Page(
                        data = cached,
                        prevKey = if (page == 1) null else page - 1,
                        nextKey = page + 1
                    )
                } else {
                    LoadResult.Error(e)
                }
            }
        }
    }
}

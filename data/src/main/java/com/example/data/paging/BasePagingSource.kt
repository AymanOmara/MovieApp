package com.example.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.network.dto.BaseResponse
import com.example.data.network.utils.NetworkUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class BasePagingSource<T : Any, DTO : Any>(
    private val provider: suspend (Int) -> BaseResponse<List<DTO>>,
    private val mapper: (DTO) -> T,
    private val networkUtils: NetworkUtils,
    private val cacheReader: (suspend (Int) -> List<T>)? = null,
    private val cacheWriter: (suspend (List<T>, Int) -> Unit)? = null
) : PagingSource<Int, T>() {

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        val page = params.key ?: 1

        return if (networkUtils.isNetworkAvailable()) {
            loadFromNetwork(page)
        } else {
            loadFromCache(page)
        }
    }

    private suspend fun loadFromNetwork(page: Int): LoadResult<Int, T> {
        return try {
            val response = provider(page)
            val dtos = response.results
            val hasMore = dtos.isNotEmpty() && page < response.totalPages
            val nextKey = if (hasMore) page + 1 else null
            val items = dtos.map { mapper(it) }

            cacheWriter?.let {
                withContext(Dispatchers.IO) { it(items, page) }
            }

            LoadResult.Page(
                data = items,
                prevKey = if (page == 1) null else page - 1,
                nextKey = nextKey
            )
        } catch (e: Exception) {
            loadFromCache(page, fallbackError = e)
        }
    }

    private suspend fun loadFromCache(
        page: Int,
        fallbackError: Exception? = null
    ): LoadResult<Int, T> {
        val cached = cacheReader?.let {
            withContext(Dispatchers.IO) { it(page) }
        } ?: emptyList()

        return if (cached.isNotEmpty()) {
            LoadResult.Page(
                data = cached,
                prevKey = if (page == 1) null else page - 1,
                nextKey = page + 1
            )
        } else {
            LoadResult.Error(
                fallbackError ?: Exception("No network connection and no cached data available")
            )
        }
    }

    companion object {
        fun <T : Any, DTO : Any> createPager(
            pageSize: Int = 20,
            prefetchDistance: Int = 1,
            networkUtils: NetworkUtils,
            provider: suspend (Int) -> BaseResponse<List<DTO>>,
            mapper: (DTO) -> T,
            cacheReader: (suspend (Int) -> List<T>)? = null,
            cacheWriter: (suspend (List<T>, Int) -> Unit)? = null
        ): Pager<Int, T> {
            return Pager(
                config = PagingConfig(
                    pageSize = pageSize,
                    prefetchDistance = prefetchDistance,
                    enablePlaceholders = false,
                    initialLoadSize = pageSize
                ),
                pagingSourceFactory = {
                    BasePagingSource(
                        provider = provider,
                        mapper = mapper,
                        networkUtils = networkUtils,
                        cacheReader = cacheReader,
                        cacheWriter = cacheWriter
                    )
                }
            )
        }
    }
}

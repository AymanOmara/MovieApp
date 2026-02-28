package com.example.data.paging

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.data.network.dto.BaseResponse

class BasePagingSource<T : Any, DTO : Any> : PagingSource<Int, T>() {

    private lateinit var provider: suspend (Int) -> BaseResponse<List<DTO>>
    private lateinit var mapper: (DTO) -> T

    fun setProvider(
        provider: suspend (Int) -> BaseResponse<List<DTO>>,
        mapper: (DTO) -> T
    ): BasePagingSource<T, DTO> {
        this.provider = provider
        this.mapper = mapper
        return this
    }

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            val page = params.key ?: 1
            val response = provider(page)
            val list = response.results
            val totalPages = response.totalPages
            val hasMore = list.isEmpty().not() && page < totalPages && page < 500
            val nextKey = if (hasMore) page + 1 else null

            if (list.isNotEmpty()) {
                LoadResult.Page(
                    data = list.map { mapper(it) },
                    prevKey = if (page == 1) null else page - 1,
                    nextKey = nextKey
                )
            } else {
                LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
            }
        } catch (e: Exception) {
            when (e) {
                is IndexOutOfBoundsException -> LoadResult.Page(
                    data = emptyList(),
                    prevKey = null,
                    nextKey = null
                )
                else -> LoadResult.Error(e)
            }
        }
    }

    fun toPager(pageSize: Int = 20): Pager<Int, T> {
        return Pager(
            config = PagingConfig(
                pageSize = pageSize,
                prefetchDistance = 1,
                enablePlaceholders = true,
                maxSize = PagingConfig.MAX_SIZE_UNBOUNDED,
                initialLoadSize = pageSize
            ),
            pagingSourceFactory = {
                BasePagingSource<T, DTO>().setProvider(provider, mapper)
            }
        )
    }
}

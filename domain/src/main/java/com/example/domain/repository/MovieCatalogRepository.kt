package com.example.domain.repository

import androidx.paging.PagingData
import com.example.domain.entity.Movie
import com.example.domain.utils.Result
import kotlinx.coroutines.flow.Flow

interface MovieCatalogRepository {

    fun getPopularMovies(): Flow<Result<List<Movie>>>

    fun getMoviesByDateRangePager(startDate: String, endDate: String): Flow<PagingData<Movie>>
}

package com.example.movieapp.presentation.model

import androidx.paging.PagingData
import com.example.domain.entity.Movie
import kotlinx.coroutines.flow.Flow

data class MonthPagingSection(
    val monthLabel: String,
    val pagingFlow: Flow<PagingData<Movie>>
)

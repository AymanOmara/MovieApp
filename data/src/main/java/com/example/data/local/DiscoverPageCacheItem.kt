package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Movie

@Entity(tableName = "discover_page_cache", primaryKeys = ["startDate", "endDate", "page", "id"])
data class DiscoverPageCacheItem(
    val startDate: String,
    val endDate: String,
    val page: Int,
    val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun toMovie() = Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun Movie.toDiscoverPageCacheItem(startDate: String, endDate: String, page: Int): DiscoverPageCacheItem =
    DiscoverPageCacheItem(
        startDate = startDate,
        endDate = endDate,
        page = page,
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )

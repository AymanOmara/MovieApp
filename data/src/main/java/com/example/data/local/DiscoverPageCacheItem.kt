package com.example.data.local

import androidx.room.Entity
import com.example.domain.entity.Movie

@Entity(tableName = "discover_page_cache", primaryKeys = ["startDate", "endDate", "page", "id"])
data class DiscoverPageCacheItem(
    val startDate: String,
    val endDate: String,
    val page: Int,
    val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun toMovie() = Movie(
        id = id,
        title = title,
        posterUrl = posterUrl,
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
        posterUrl = posterUrl,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )

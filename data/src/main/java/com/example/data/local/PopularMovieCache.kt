package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Movie

@Entity(tableName = "popular_movies_cache")
data class PopularMovieCache(
    @PrimaryKey val id: Int,
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

fun Movie.toPopularMovieCache(): PopularMovieCache = PopularMovieCache(
    id = id,
    title = title,
    posterPath = posterPath,
    overview = overview,
    releaseDate = releaseDate,
    voteAverage = voteAverage,
    voteCount = voteCount
)

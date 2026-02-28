package com.example.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.domain.model.Movie

@Entity(tableName = "movies")
data class MovieLocal(
    @PrimaryKey val id: Int,
    val title: String,
    val posterPath: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    fun toMovieEntity() = Movie(
        id = id,
        title = title,
        posterPath = posterPath,
        overview = overview,
        releaseDate = releaseDate,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}
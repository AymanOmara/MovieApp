package com.example.data.network.dto

import com.example.domain.model.Movie
import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?
) {
    fun toMovie(): Movie = Movie(
        id = id,
        title = title ?: "",
        posterPath = posterPath ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )
}

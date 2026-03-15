package com.example.data.network.dto

import com.example.data.utils.Constants
import com.example.domain.entity.Movie
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
        posterUrl = posterPath?.let { Constants.IMAGE_BASE_URL + it.trimStart('/') } ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0
    )
}

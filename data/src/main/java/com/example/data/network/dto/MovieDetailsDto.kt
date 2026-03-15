package com.example.data.network.dto

import com.example.data.utils.Constants
import com.example.domain.entity.MovieDetails
import com.google.gson.annotations.SerializedName

data class MovieDetailsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("title") val title: String?,
    @SerializedName("poster_path") val posterPath: String?,
    @SerializedName("backdrop_path") val backdropPath: String?,
    @SerializedName("overview") val overview: String?,
    @SerializedName("release_date") val releaseDate: String?,
    @SerializedName("vote_average") val voteAverage: Double?,
    @SerializedName("vote_count") val voteCount: Int?,
    @SerializedName("runtime") val runtime: Int?,
    @SerializedName("genres") val genres: List<GenreDto>?,
    @SerializedName("tagline") val tagline: String?,
    @SerializedName("status") val status: String?,
    @SerializedName("budget") val budget: Long?,
    @SerializedName("revenue") val revenue: Long?
) {
    fun toMovieDetails(): MovieDetails = MovieDetails(
        id = id,
        title = title ?: "",
        posterUrl = posterPath?.let { Constants.IMAGE_BASE_URL + it.trimStart('/') } ?: "",
        backdropUrl = backdropPath?.let { Constants.IMAGE_BASE_URL + it.trimStart('/') } ?: "",
        overview = overview ?: "",
        releaseDate = releaseDate ?: "",
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        runtime = runtime ?: 0,
        genres = genres?.map { it.toGenre() } ?: emptyList(),
        tagline = tagline ?: "",
        status = status ?: "",
        budget = budget ?: 0L,
        revenue = revenue ?: 0L
    )
}

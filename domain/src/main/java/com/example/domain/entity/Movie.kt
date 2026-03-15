package com.example.domain.entity

import kotlinx.serialization.Serializable

@Serializable
data class Movie(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int
) {
    companion object {
        fun preview() = Movie(
            id = 1,
            title = "Inception",
            posterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg",
            overview = "A thief who steals corporate secrets through the use of dream-sharing technology.",
            releaseDate = "2010-07-16",
            voteAverage = 8.4,
            voteCount = 35000
        )

        fun previewList() = listOf(
            Movie(1, "The Dark Knight", "https://image.tmdb.org/t/p/w500/poster1.jpg", "Batman raises the stakes.", "2008-07-18", 9.0, 25000),
            Movie(2, "Interstellar", "https://image.tmdb.org/t/p/w500/poster2.jpg", "Exploring the universe.", "2014-11-07", 8.6, 30000),
            Movie(3, "The Prestige", "https://image.tmdb.org/t/p/w500/poster3.jpg", "Two magicians compete.", "2006-10-20", 8.5, 15000)
        )
    }
}

package com.example.domain.entity

data class MovieDetails(
    val id: Int,
    val title: String,
    val posterUrl: String,
    val backdropUrl: String,
    val overview: String,
    val releaseDate: String,
    val voteAverage: Double,
    val voteCount: Int,
    val runtime: Int,
    val genres: List<Genre>,
    val tagline: String,
    val status: String,
    val budget: Long,
    val revenue: Long
) {
    companion object {
        fun preview() = MovieDetails(
            id = 1,
            title = "Inception",
            posterUrl = "https://image.tmdb.org/t/p/w500/poster.jpg",
            backdropUrl = "https://image.tmdb.org/t/p/w500/backdrop.jpg",
            overview = "A thief who steals corporate secrets through the use of dream-sharing technology is given the inverse task of planting an idea into the mind of a C.E.O.",
            releaseDate = "2010-07-16",
            voteAverage = 8.4,
            voteCount = 35000,
            runtime = 148,
            genres = Genre.previewList(),
            tagline = "Your mind is the scene of the crime.",
            status = "Released",
            budget = 160000000,
            revenue = 836836967
        )
    }
}

data class Genre(
    val id: Int,
    val name: String
) {
    companion object {
        fun preview() = Genre(id = 1, name = "Action")

        fun previewList() = listOf(
            Genre(1, "Action"),
            Genre(2, "Sci-Fi"),
            Genre(3, "Thriller")
        )
    }
}

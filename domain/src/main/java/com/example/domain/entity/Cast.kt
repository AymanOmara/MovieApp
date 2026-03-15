package com.example.domain.entity

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profileUrl: String,
    val order: Int
) {
    companion object {
        fun preview() = Cast(
            id = 1,
            name = "Leonardo DiCaprio",
            character = "Dom Cobb",
            profileUrl = "https://image.tmdb.org/t/p/w500/profile.jpg",
            order = 0
        )

        fun previewList() = listOf(
            Cast(1, "Leonardo DiCaprio", "Dom Cobb", "https://image.tmdb.org/t/p/w500/profile1.jpg", 0),
            Cast(2, "Joseph Gordon-Levitt", "Arthur", "https://image.tmdb.org/t/p/w500/profile2.jpg", 1),
            Cast(3, "Elliot Page", "Ariadne", "https://image.tmdb.org/t/p/w500/profile3.jpg", 2),
            Cast(4, "Tom Hardy", "Eames", "https://image.tmdb.org/t/p/w500/profile4.jpg", 3)
        )
    }
}

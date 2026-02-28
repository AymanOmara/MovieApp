package com.example.domain.model

data class Cast(
    val id: Int,
    val name: String,
    val character: String,
    val profilePath: String,
    val order: Int
) {
    companion object {
        fun preview() = Cast(
            id = 1,
            name = "Leonardo DiCaprio",
            character = "Dom Cobb",
            profilePath = "/profile.jpg",
            order = 0
        )

        fun previewList() = listOf(
            Cast(1, "Leonardo DiCaprio", "Dom Cobb", "/profile1.jpg", 0),
            Cast(2, "Joseph Gordon-Levitt", "Arthur", "/profile2.jpg", 1),
            Cast(3, "Elliot Page", "Ariadne", "/profile3.jpg", 2),
            Cast(4, "Tom Hardy", "Eames", "/profile4.jpg", 3)
        )
    }
}

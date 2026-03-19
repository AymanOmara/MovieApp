package com.example.domain.entity

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

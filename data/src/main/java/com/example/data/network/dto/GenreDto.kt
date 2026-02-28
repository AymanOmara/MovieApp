package com.example.data.network.dto

import com.example.domain.model.Genre
import com.google.gson.annotations.SerializedName

data class GenreDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?
) {
    fun toGenre(): Genre = Genre(
        id = id,
        name = name ?: ""
    )
}

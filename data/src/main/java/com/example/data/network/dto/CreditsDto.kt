package com.example.data.network.dto

import com.example.domain.model.Cast
import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<CastDto>?
)

data class CastDto(
    @SerializedName("id") val id: Int,
    @SerializedName("name") val name: String?,
    @SerializedName("character") val character: String?,
    @SerializedName("profile_path") val profilePath: String?,
    @SerializedName("order") val order: Int?
) {
    fun toCast(): Cast = Cast(
        id = id,
        name = name ?: "",
        character = character ?: "",
        profilePath = profilePath ?: "",
        order = order ?: 0
    )
}

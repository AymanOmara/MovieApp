package com.example.data.network.dto

import com.example.data.utils.Constants
import com.example.domain.entity.Cast
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
        profileUrl = profilePath?.let { Constants.IMAGE_BASE_URL + it.trimStart('/') } ?: "",
        order = order ?: 0
    )
}

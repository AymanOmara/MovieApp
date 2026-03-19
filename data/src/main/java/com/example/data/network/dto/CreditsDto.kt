package com.example.data.network.dto

import com.google.gson.annotations.SerializedName

data class CreditsDto(
    @SerializedName("id") val id: Int,
    @SerializedName("cast") val cast: List<CastDto>?
)

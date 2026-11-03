package com.example.kubrick.data.model

import com.google.gson.annotations.SerializedName

data class MovieDetails(
    val id: Int,
    val title: String? = null,
    val name: String? = null,
    val overview: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("release_date")
    val releaseDate: String? = null,

    val runtime: Int? = null,

    val genres: List<Genre> = emptyList(),

    @SerializedName("vote_average")
    val rating: Double = 0.0
) {
    val displayTitle: String
        get() = title ?: name ?: "Unknown"
}

data class CreditsResponse(
    val cast: List<CastMember> = emptyList()
)

data class CastMember(
    val id: Int,
    val name: String,
    val character: String? = null,

    @SerializedName("profile_path")
    val profilePath: String? = null
)
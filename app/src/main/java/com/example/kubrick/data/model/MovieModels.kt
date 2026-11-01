
package com.example.kubrick.data.model

import com.google.gson.annotations.SerializedName

data class MovieResponse(
    val results: List<Movie> = emptyList()
)

data class GenreResponse(
    val genres: List<Genre> = emptyList()
)

data class Genre(
    val id: Int,
    val name: String
)

data class Movie(
    val id: Int,

    val title: String? = null,
    val name: String? = null,

    val overview: String? = null,

    @SerializedName("poster_path")
    val posterPath: String? = null,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("vote_average")
    val rating: Double = 0.0,

    @SerializedName("genre_ids")
    val genreIds: List<Int> = emptyList(),

    @SerializedName("media_type")
    val mediaType: String? = null
) {
    val displayTitle: String
        get() = title ?: name ?: "Unknown"
}

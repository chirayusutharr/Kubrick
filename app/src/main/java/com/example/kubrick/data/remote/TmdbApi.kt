package com.example.kubrick.data.remote

import com.example.kubrick.data.model.MovieResponse
import com.example.kubrick.data.model.GenreResponse
import retrofit2.http.GET
import retrofit2.http.Query
import com.example.kubrick.data.model.MovieDetails
import com.example.kubrick.data.model.CreditsResponse
import retrofit2.http.Path

interface TmdbApi {

    @GET("trending/all/week")
    suspend fun getTrending(): MovieResponse

    @GET("movie/popular")
    suspend fun getPopular(): MovieResponse

    @GET("movie/top_rated")
    suspend fun getTopRated(): MovieResponse

    @GET("discover/movie")
    suspend fun getRomComMovies(
        @Query("with_genres") genres: String = "35|10749",
        @Query("sort_by") sortBy: String = "popularity.desc"
    ): MovieResponse

    @GET("genre/movie/list")
    suspend fun getGenres(): GenreResponse

    @GET("discover/movie")
    suspend fun getMoviesByGenre(
        @Query("with_genres") genreId: Int
    ): MovieResponse

    @GET("search/multi")
    suspend fun searchMovies(
        @Query("query") query: String,
        @Query("include_adult") includeAdult: Boolean = false
    ): MovieResponse

    @GET("movie/{movie_id}")
    suspend fun getMovieDetails(
        @Path("movie_id") movieId: Int
    ): MovieDetails

    @GET("movie/{movie_id}/credits")
    suspend fun getMovieCredits(
        @Path("movie_id") movieId: Int
    ): CreditsResponse
}
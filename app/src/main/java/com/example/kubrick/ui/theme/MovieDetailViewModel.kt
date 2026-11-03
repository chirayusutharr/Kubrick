package com.example.kubrick.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kubrick.data.model.MovieDetails
import com.example.kubrick.data.model.CastMember
import com.example.kubrick.data.remote.TmdbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class MovieDetailState(
    val movie: MovieDetails? = null,
    val cast: List<CastMember> = emptyList(),
    val loading: Boolean = true,
    val error: String? = null
)

class MovieDetailViewModel : ViewModel() {

    private val api = TmdbClient.api

    private val _state = MutableStateFlow(MovieDetailState())
    val state = _state.asStateFlow()

    fun loadMovie(movieId: Int) {
        viewModelScope.launch {
            _state.value = MovieDetailState(loading = true)

            try {
                val movie = api.getMovieDetails(movieId)

                val cast = runCatching {
                    api.getMovieCredits(movieId).cast
                }.getOrDefault(emptyList())

                _state.value = MovieDetailState(
                    movie = movie,
                    cast = cast,
                    loading = false
                )

            } catch (e: Exception) {
                _state.value = MovieDetailState(
                    loading = false,
                    error = e.message ?: "Unable to load movie"
                )
            }
        }
    }
}
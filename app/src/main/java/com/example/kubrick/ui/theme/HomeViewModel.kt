package com.example.kubrick.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kubrick.data.model.Movie
import com.example.kubrick.data.model.Genre
import com.example.kubrick.data.remote.TmdbClient
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

data class HomeState(
    val movies: List<Movie> = emptyList(),
    val popularMovies: List<Movie> = emptyList(),
    val romComMovies: List<Movie> = emptyList(),
    val topRatedMovies: List<Movie> = emptyList(),
    val genres: List<Genre> = emptyList(),
    val selectedGenre: Int? = null,
    val searchText: String = "",
    val loading: Boolean = true,
    val error: String? = null
)

class HomeViewModel : ViewModel() {

    private val api = TmdbClient.api

    private val _state = MutableStateFlow(HomeState())
    val state: StateFlow<HomeState> = _state.asStateFlow()

    init {
        loadHome()
    }

    private fun loadHome() {
        viewModelScope.launch {
            _state.value = _state.value.copy(loading = true, error = null)

            try {
                val trending = api.getTrending()
                val genres = api.getGenres()

                // Load each slider independently.
                val popular = runCatching {
                    api.getPopular().results
                }.getOrDefault(emptyList())

                val romCom = runCatching {
                    api.getRomComMovies().results
                }.getOrDefault(emptyList())

                val topRated = runCatching {
                    api.getTopRated().results
                }.getOrDefault(emptyList())

                _state.value = _state.value.copy(
                    movies = trending.results,
                    popularMovies = popular,
                    romComMovies = romCom,
                    topRatedMovies = topRated,
                    genres = genres.genres,
                    loading = false,
                    error = null
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = e.message ?: "Failed to load movies"
                )
            }
        }
    }

    fun selectGenre(id: Int?) {
        viewModelScope.launch {
            _state.value = _state.value.copy(
                selectedGenre = id,
                loading = true,
                error = null
            )

            try {
                val result = if (id == null) {
                    api.getTrending().results
                } else {
                    api.getMoviesByGenre(id).results
                }

                _state.value = _state.value.copy(
                    movies = result,
                    loading = false
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Could not load this genre"
                )
            }
        }
    }

    fun updateSearch(value: String) {
        _state.value = _state.value.copy(searchText = value)
    }

    fun search() {
        val query = _state.value.searchText.trim()

        if (query.isBlank()) {
            loadHome()
            return
        }

        viewModelScope.launch {
            _state.value = _state.value.copy(
                loading = true,
                error = null
            )

            try {
                val result = api.searchMovies(query)

                _state.value = _state.value.copy(
                    movies = result.results,
                    loading = false
                )

            } catch (e: Exception) {
                _state.value = _state.value.copy(
                    loading = false,
                    error = "Search failed"
                )
            }
        }
    }
}
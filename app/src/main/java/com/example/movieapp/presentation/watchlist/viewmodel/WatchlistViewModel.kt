package com.example.movieapp.presentation.watchlist.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.Movie
import com.example.domain.repository.WatchlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WatchlistViewModel @Inject constructor(
    private val repository: WatchlistRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow<WatchlistUiState>(WatchlistUiState.Loading)
    val uiState = _uiState.asStateFlow()

    init {
        loadWatchlist()
    }

    fun onEvent(event: WatchlistEvent) {
        when (event) {
            is WatchlistEvent.Refresh -> loadWatchlist()
            is WatchlistEvent.RemoveMovie -> removeMovie(event.movie)
        }
    }

    private fun loadWatchlist() {
        viewModelScope.launch(Dispatchers.IO) {
            _uiState.value = WatchlistUiState.Loading
            repository.getAllMovies().collect {
                _uiState.value = WatchlistUiState.Success(movies = it)
            }
        }
    }

    private fun removeMovie(movie: Movie) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteMovie(movie)
            loadWatchlist()
        }
    }
}

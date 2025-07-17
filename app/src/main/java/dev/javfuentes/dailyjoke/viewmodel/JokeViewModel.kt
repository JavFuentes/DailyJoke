package dev.javfuentes.dailyjoke.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.javfuentes.dailyjoke.data.model.ApiResult
import dev.javfuentes.dailyjoke.data.repository.JokeRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class JokeViewModel(
    private val repository: JokeRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(JokeUiState())
    val uiState: StateFlow<JokeUiState> = _uiState.asStateFlow()

    init {
        handleEvent(JokeUiEvent.LoadJoke)
        loadFavorites()
    }

    fun handleEvent(event: JokeUiEvent) {
        when (event) {
            is JokeUiEvent.LoadJoke -> loadRandomJoke()
            is JokeUiEvent.RefreshJoke -> refreshJoke()
            is JokeUiEvent.LoadJokeByCategory -> loadJokeByCategory(event.category)
            is JokeUiEvent.ClearError -> clearError()
            is JokeUiEvent.SaveFavoriteJoke -> saveFavoriteJoke()
            is JokeUiEvent.RemoveFromFavorites -> removeFromFavorites(event.joke)
        }
    }

    private fun loadRandomJoke() {
        viewModelScope.launch {
            repository.getRandomJoke()
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { result ->
                    handleApiResult(result)
                }
        }
    }

    private fun refreshJoke() {
        _uiState.value = _uiState.value.copy(isRefreshing = true)
        loadRandomJoke()
    }

    private fun loadJokeByCategory(category: String) {
        viewModelScope.launch {
            repository.getJokeByCategory(category)
                .catch { e ->
                    _uiState.value = _uiState.value.copy(
                        isLoading = false,
                        errorMessage = e.message ?: "An unexpected error occurred"
                    )
                }
                .collect { result ->
                    handleApiResult(result)
                }
        }
    }

    private fun handleApiResult(result: ApiResult<dev.javfuentes.dailyjoke.data.Joke>) {
        when (result) {
            is ApiResult.Loading -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = true,
                    errorMessage = null
                )
            }
            is ApiResult.Success -> {
                _uiState.value = _uiState.value.copy(
                    joke = result.data,
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = null
                )
            }
            is ApiResult.Error -> {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    isRefreshing = false,
                    errorMessage = result.exception.message ?: "An unexpected error occurred"
                )
            }
        }
    }

    private fun clearError() {
        _uiState.value = _uiState.value.copy(errorMessage = null)
    }

    private fun saveFavoriteJoke() {
        val currentState = _uiState.value
        val currentJoke = currentState.joke
        
        // Only save if there's a current joke
        if (currentJoke != null) {
            viewModelScope.launch {
                try {
                    repository.saveFavoriteJoke(currentJoke)
                    loadFavorites() // Reload favorites to update UI
                } catch (e: Exception) {
                    // Handle error if needed
                }
            }
        }
    }

    private fun removeFromFavorites(joke: dev.javfuentes.dailyjoke.data.Joke) {
        viewModelScope.launch {
            try {
                repository.removeFavoriteJoke(joke.id)
                loadFavorites() // Reload favorites to update UI
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }

    private fun loadFavorites() {
        viewModelScope.launch {
            try {
                val favorites = repository.getFavoriteJokes()
                _uiState.value = _uiState.value.copy(favoriteJokes = favorites)
            } catch (e: Exception) {
                // Handle error if needed
            }
        }
    }
}
package dev.javfuentes.dailyjoke.viewmodel

import dev.javfuentes.dailyjoke.data.Joke

data class JokeUiState(
    val joke: Joke? = null,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val isRefreshing: Boolean = false,
    val favoriteJokes: List<Joke> = emptyList()
)

sealed class JokeUiEvent {
    object LoadJoke : JokeUiEvent()
    object RefreshJoke : JokeUiEvent()
    data class LoadJokeByCategory(val category: String) : JokeUiEvent()
    object ClearError : JokeUiEvent()
    object SaveFavoriteJoke : JokeUiEvent()
    data class RemoveFromFavorites(val joke: Joke) : JokeUiEvent()
}